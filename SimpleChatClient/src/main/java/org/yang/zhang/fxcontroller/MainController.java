package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.cellimpl.ChatRoomViewCellImpl;
import org.yang.zhang.cellimpl.ContractTreeCellImpl;
import org.yang.zhang.cellimpl.RecentContractViewCellImpl;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.enums.IDType;
import org.yang.zhang.enums.MessageType;
import org.yang.zhang.module.ChatRoom;
import org.yang.zhang.module.ContractGroup;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.User;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.service.ContractService;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.*;
import org.yang.zhang.view.ChatRoomItemView;
import org.yang.zhang.view.ChatRoomView;
import org.yang.zhang.view.ChatView;
import org.yang.zhang.view.ContractItemView;
import org.yang.zhang.view.RecentContractView;
import org.yang.zhang.view.SearchContractView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;

@FXMLController
public class MainController  implements Initializable {

    @FXML
    public Pane root;
    @FXML
    public TreeView<ContractItemView> contractTree;
    @FXML
    public Tab messageTab;
    @FXML
    public ListView<RecentContractView> messageList;
    @FXML
    public Tab chatRoomTab;
    @FXML
    public ListView<ChatRoomItemView> chatRoomList;
    @FXML
    public TabPane tabPane;
    @FXML
    public ImageView userIcon;
    @FXML
    public Label nameLabel;
    @FXML
    public TextField personWord;
    @FXML
    public TextField searchField;
    @FXML
    public Button addFriendBtn;
    @FXML
    public Button systemBtn;

    @Autowired
    public ContractService contractService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private SearchContractView searchContractView;
    @Autowired
    private SearchContractController searchContractController;

    private ContractItemView focusItem;
    private double xOffset = 0;
    private double yOffset = 0;
    public static ContextMenu groupMenu = new ContextMenu();
    public static ContextMenu userMenu = new ContextMenu();
    private Integer userId;
    private User user;
    private Image image;
    /**
     * 主页面初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userId=UserUtils.getCurrentUserId();
        user=UserUtils.getCurrentUser();
        image=ImageUtiles.getUserIcon(user.getIconUrl());
    }

    public void init(){
        initMainPane();
        regesterChannel();
        initContractList();
        initTabPane();
        initEvent();
        userMenu();
        groupMenu();
    }

    /**
     * 初始化主面板元素
     */
    private void initMainPane() {
        userIcon.setImage(image);
        nameLabel.setText(user.getNickName());
        personWord.setText(user.getPersonWord());
        personWord.setFocusTraversable(false);
        UserUtils.setUserIcon(image);
    }

    /**
     * 向服务器注册当前channel
     */
    private void regesterChannel() {
        ChatUtils.sendMessage(null,MessageType.REGISTER,null);
    }

    /**
     * 初始化页签
     */
    private void initTabPane() {
        tabPane.setOnMouseClicked(click->{
           int index=tabPane.getSelectionModel().getSelectedIndex();
           //联系人
            if(index==0){
               initContractList();
            //最近消息
           }else if (index==1){
               initRecentMessage(user);
           //聊天室
           }else if(index==2){
               initChatRoom(user);
           }
        });
    }

    private void initChatRoom(User user) {
        Integer id=user.getId();
        ObservableList<ChatRoomItemView> items =FXCollections.observableArrayList();
        List<ChatRoom> chatRooms= chatService.getUserChatRoom(id);
        for (ChatRoom chatRoom:chatRooms){
            ChatRoomItemView chatRoomView=new ChatRoomItemView(chatRoom);
            items.add(chatRoomView);
        }
        chatRoomList.setItems(items);
        chatRoomList.setCellFactory(ChatRoomViewCellImpl.callback);
    }

    private void initRecentMessage(User user) {
        Integer id=user.getId();
        ObservableList<RecentContractView> items =FXCollections.observableArrayList();
        List<RecentContract> recentChatLogDtos= chatService.getrecentContract(id);
        for (RecentContract recentContract:recentChatLogDtos){
            RecentContractView contractView=new RecentContractView(recentContract);
            items.add(contractView);
        }
        messageList.setCellFactory(RecentContractViewCellImpl.callback);
        messageList.setItems(items);

    }

    /**
     *获取当前用户联系人列表
     */
    public void initContractList(){
        FindByUserDto findByUserDto=new FindByUserDto();
        findByUserDto.setUserId(userId);
        //拉取所有好友
        findByUserDto.setOnlyOnline(false);
        //初始化联系人列表
        doInitContractList(findByUserDto);
    }


    /**
     * 系统设置
     * @param event
     */
    @FXML
    public void openSystemConfig(ActionEvent event){

    }

    /**
     * 添加好友
     * @param event
     */
    @FXML
    public void openAddFriend(ActionEvent event){

    }

    @FXML
    public void minsize(){
        Stage primaryStage=StageManager.getStage(StageCodes.MAIN);
        primaryStage.hide();
    }

    @FXML
    public void closeApp(){
        System.exit(0);
    }

    private void initEvent() {
        root.setOnMousePressed(event ->  {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            StageManager.getStage(StageCodes.MAIN).setX(event.getScreenX() - xOffset);
            StageManager.getStage(StageCodes.MAIN).setY(event.getScreenY() - yOffset);
        });
        messageList.setOnMouseClicked(click->{
            if (click.getClickCount() == 2) {
                RecentContractView selectedItem = messageList.getSelectionModel().getSelectedItem();
                String userid=selectedItem.getId();
                if (userid != null) {
                    ChatUtils.openChatWindow(Integer.valueOf(userid),selectedItem.getNickName(),"",ImageUtiles.getUserIcon(selectedItem.getUserIcon()));
                }
            }
        });
        addFriendBtn.setOnMouseClicked(click->{
            if(StageManager.getStage(StageCodes.SEARCHCONTRACT)==null){
                Stage searchContract=new Stage();
                Scene scene=new Scene(searchContractView.getView());
                searchContract.setScene(scene);
                searchContract.setResizable(false);
                searchContract.show();
                searchContractController.init(String.valueOf(userId));
                StageManager.registerStage(StageCodes.SEARCHCONTRACT,searchContract);
            }else{
                StageManager.getStage(StageCodes.SEARCHCONTRACT).show();
                searchContractController.init(String.valueOf(userId));
            }
        });
        chatRoomList.setOnMouseClicked(click->{
            if (click.getClickCount() == 2) {
                ChatRoomItemView selectedItem = chatRoomList.getSelectionModel().getSelectedItem();
                String chatRoomId=selectedItem.getId();
                if (chatRoomId != null) {
                    ChatUtils.openChatRoom(chatRoomId,selectedItem.getIcon());
                }
            }
        });
        contractTree.setOnMouseClicked(click -> {

            //点击聚焦
            TreeItem<ContractItemView> selectedItem = contractTree.getSelectionModel().getSelectedItem();
            if(selectedItem!=null){
                ContractItemView view=selectedItem.getValue();
                String id = selectedItem.getValue().getId();
                if (id.contains("GROUP")) {
                    view.setGroupFocus();
                    if(focusItem!=null){
                        if(focusItem.getId().startsWith("GROUP")){
                            focusItem.setGroupNoFocus();
                        }else{
                            focusItem.setNoFocus();
                        }
                    }

                    focusItem=view;
                    if (click.getButton()==MouseButton.PRIMARY&&click.getClickCount() == 1) {
                        if(selectedItem.isExpanded()){
                            selectedItem.setExpanded(false);
                        }else{
                            selectedItem.setExpanded(true);
                        }
                    }
                }else{
                    if(focusItem!=null){
                        if(focusItem.getId().startsWith("GROUP")){
                            focusItem.setGroupNoFocus();
                        }else{
                            focusItem.setNoFocus();
                        }
                    }
                    view.setFocus();
                    focusItem=view;
                    //左键双击弹出聊天框
                    if (click.getButton()==MouseButton.PRIMARY&&click.getClickCount() == 2) {
                        ChatUtils.openChatWindow(Integer.valueOf(id),selectedItem.getValue().getNickName(),selectedItem.getValue().getPersonwordLabel().getText(),selectedItem.getValue().getUserImage());
                        //打开时停止闪动
                        if(selectedItem.getValue().getBlink()){
                            selectedItem.getValue().setBlink(false);
                            selectedItem.getValue().stopBlink();
                        }
                    }
                }
            }
        });
    }

    private ContextMenu emptyAreaMenu(){
        ContextMenu contextMenu=new ContextMenu();
        MenuItem add = new MenuItem("添加分组",new ImageView(new Image("images/icon/add.png")));
        MenuItem flush = new MenuItem("刷新好友列表",new ImageView(new Image("images/icon/flush.png")));
        MenuItem onlyOnline = new MenuItem("显示在线联系人",new ImageView(new Image("images/icon/onlyOnline.png")));
        contextMenu.getItems().addAll(add,flush,onlyOnline);
        add.setOnAction(this::addGroup);
        flush.setOnAction(this::flushGroup);
        onlyOnline.setOnAction(this::showOnlyOnline);
        return contextMenu;
    }

    private void userMenu(){
        MenuItem message = new MenuItem("发送即时消息",new ImageView(new Image("images/icon/message.png")));
        MenuItem email = new MenuItem("发送电子邮件",new ImageView(new Image("images/icon/email.png")));
        MenuItem info = new MenuItem("查看资料",new ImageView(new Image("images/icon/info.png")));
        MenuItem card = new MenuItem("分享TA的名片",new ImageView(new Image("images/icon/card.png")));
        MenuItem history = new MenuItem("消息记录",new ImageView(new Image("images/icon/history.png")));
        MenuItem auth = new MenuItem("设置权限",new ImageView(new Image("images/icon/auth.png")));
        MenuItem remark = new MenuItem("修改备注姓名",new ImageView(new Image("images/icon/remark.png")));
        MenuItem move = new MenuItem("移动联系人至",new ImageView(new Image("images/icon/move.png")));
        MenuItem delete = new MenuItem("删除好友",new ImageView(new Image("images/icon/delete.png")));
        MenuItem jubao = new MenuItem("举报此用户",new ImageView(new Image("images/icon/jubao.png")));
        userMenu.getItems().addAll(message,email,info,card,history,auth,remark,move,delete,jubao);
        delete.setOnAction(this::deleteContract);
    }

    private void  groupMenu(){
        MenuItem add = new MenuItem("添加分组",new ImageView(new Image("images/icon/add.png")));
        MenuItem edit = new MenuItem("重命名",new ImageView(new Image("images/icon/edit.png")));
        MenuItem delete = new MenuItem("删除该组",new ImageView(new Image("images/icon/delete.png")));
        MenuItem flush = new MenuItem("刷新好友列表",new ImageView(new Image("images/icon/flush.png")));
        MenuItem onlyOnline = new MenuItem("显示在线联系人",new ImageView(new Image("images/icon/onlyOnline.png")));
        MenuItem visableFor = new MenuItem("隐身对该分组可见",new ImageView(new Image("images/icon/invisableFor.png")));
        MenuItem invisableFor = new MenuItem("在线对该分组隐身",new ImageView(new Image("images/icon/invisableFor.png")));
        groupMenu.getItems().addAll(add,edit,delete,flush,onlyOnline,visableFor,invisableFor);
        add.setOnAction(this::addGroup);
        delete.setOnAction(this::deleteGroup);
        edit.setOnAction(this::editGroup);
        flush.setOnAction(this::flushGroup);
        onlyOnline.setOnAction(this::showOnlyOnline);
        visableFor.setOnAction(this::visableForGroup);
        invisableFor.setOnAction(this::invisableForGroup);
    }

    private void invisableForGroup(ActionEvent event) {

    }

    private void visableForGroup(ActionEvent event) {

    }

    private void showOnlyOnline(ActionEvent event) {
        //获取当前用户联系人列表
        FindByUserDto findByUserDto=new FindByUserDto();
        findByUserDto.setUserId(UserUtils.getCurrentUserId());
        findByUserDto.setOnlyOnline(true);
        doInitContractList(findByUserDto);
    }

    private void flushGroup(ActionEvent event) {
        initContractList();
    }

    private void editGroup(ActionEvent event) {
        TreeItem<ContractItemView> item=contractTree.getSelectionModel().getSelectedItem();
        if(item==null){
            return;
        }
        ContractItemView contractItemView=item.getValue();
        Label label=contractItemView.getGroupName();
        String groupName=label.getText();
        TextField textField;
        if(groupName.contains(" ")){
            textField=new TextField(groupName.substring(0,groupName.indexOf(" ")));
        }else{
            textField=new TextField(groupName);
        }
        textField.setPrefWidth(label.getPrefWidth());
        label.setGraphic(textField);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if(!newPropertyValue)
                {
                    ContractGroupDto dto=chatService.updateGroup(contractItemView.getId(),textField.getText());
                    label.setText(textField.getText()+" "+dto.getOnlineCount()+"/"+dto.getAllCount());
                    label.setGraphic(null);
                }
            }
        });
        textField.setOnKeyReleased(e->{
            if (e.getCode().equals(KeyCode.ENTER)){
                chatService.updateGroup(contractItemView.getId(),textField.getText());
                label.setText(textField.getText());
                label.setGraphic(null);
            }
        });
    }


    private void deleteGroup(ActionEvent event) {
        TreeItem<ContractItemView> item=contractTree.getSelectionModel().getSelectedItem();
        if(item==null){
            return;
        }
        ContractItemView contractItemView=item.getValue();
        //删除分组,原分组下的人移到默认的分组中
        String groupId = contractItemView.getId();
        chatService.deleteGroup(UserUtils.getCurrentUserId(), Integer.valueOf(groupId.substring(groupId.indexOf("P") + 1, groupId.length())));
        initContractList();

    }

    private void deleteContract(ActionEvent actionEvent) {
        TreeItem<ContractItemView> item=contractTree.getSelectionModel().getSelectedItem();
        if(item==null){
            return;
        }
        ContractItemView contractItemView=item.getValue();
        //删除用户
        String parentId=item.getParent().getValue().getId();
        Integer pid=Integer.valueOf(parentId.substring(parentId.indexOf("P")+1,parentId.length()));
        chatService.deleteFriend(pid,Integer.valueOf(contractItemView.getId()));
        initContractList();
    }

    private void addGroup(ActionEvent event) {

        TreeItem<ContractItemView> newGroup = new TreeItem<ContractItemView>();
        ContractItemView itemView=new ContractItemView("");
        itemView.setId("未命名");

        Label label=itemView.getGroupName();
        TextField textField=new TextField("未命名");
        textField.setPrefWidth(label.getPrefWidth());
        label.setGraphic(textField);
        textField.focusTraversableProperty().setValue(true);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if(!newPropertyValue)
                {
                    createGroup(textField.getText(),label,itemView);
                }
            }
        });
        textField.setOnKeyReleased(e->{
            if (e.getCode().equals(KeyCode.ENTER)){
                createGroup(textField.getText(),label,itemView);
            }
        });

        newGroup.setValue(itemView);
        //新分组都挂在根节点下
        contractTree.getRoot().getChildren().add(newGroup);
    }

    public void createGroup(String text,Label label,ContractItemView itemView){
        ContractGroup contractGroup= chatService.createNewGroup(text);
        label.setText(text+" 0/0");
        label.setGraphic(null);
        itemView.setId("GROUP"+contractGroup.getId());
    }

    private void doInitContractList(FindByUserDto findByUserDto){
        List<ContractGroupDto> contracts= contractService.getContractList(findByUserDto);
        //添加根节点
        TreeItem<ContractItemView> rootItem = new TreeItem<ContractItemView>();
        rootItem.setValue(null);
        contractTree.setRoot(rootItem);
        contractTree.setShowRoot(false);
        //清空旧联系人和组数据
        ClientCache.clearContract();
        ClientCache.clearGroup();
        //添加联系人到列表
        for (ContractGroupDto contract:contracts) {
            //分组label未：组名 在线人数/总人数
            ContractItemView pane=new ContractItemView(contract.getGroupName()+" "+contract.getOnlineCount()+"/"+contract.getAllCount());
            pane.setId(Constant.GROUP_PREFIX+contract.getGroupId());
            TreeItem<ContractItemView> groupItem = new TreeItem<ContractItemView>(pane);
            ClientCache.addGroup(groupItem);
            rootItem.getChildren().add(groupItem);
            List<User> users = contract.getUserList();
            for (User user : users) {
                TreeItem<ContractItemView> item = new TreeItem<ContractItemView>();
                ContractItemView contractItemView=new ContractItemView(user);
                contractItemView.setId(String.valueOf(user.getId()));
                item.setValue(contractItemView);
                ClientCache.addContract(String.valueOf(user.getId()),item);
                UserUtils.setUser(user.getId(),user);
                ImageUtiles.setUserIcon(user.getId(),contractItemView.getUserImage());
                groupItem.getChildren().add(item);
            }
        }
        contractTree.setCellFactory(ContractTreeCellImpl.callback);
        contractTree.setEditable(true);
        contractTree.setContextMenu(emptyAreaMenu());
    }
}
