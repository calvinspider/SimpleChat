package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
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

    private Integer userId;
    private ContractItemView focusItem;
    private ContractItemView focusGroup;
    private double xOffset = 0;
    private double yOffset = 0;
    /**
     * 主页面初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(User user){
        this.userId=user.getId();
        initMainPane(user);
        regesterChannel(user);
        initContract(user);
        initTabPane(user);
        initEvent();
    }

    private void initMainPane(User user) {
        Image image=ImageUtiles.getUserIcon(user.getIconUrl());
        userIcon.setImage(image);
        UserUtils.setUserIcon(image);
        nameLabel.setText(user.getNickName());
        personWord.setText(user.getPersonWord());
        personWord.setFocusTraversable(false);
    }


    private void regesterChannel(User user) {
        //向服务器注册当前channel
        ChatUtils.sendMessage(null,MessageType.REGISTER,null);
    }

    private void initTabPane(User user) {
        tabPane.setOnMouseClicked(click->{
           int index=tabPane.getSelectionModel().getSelectedIndex();
           //联系人
           if(index==0){
               initContract(user);
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

    public void initContract(User loginUser){
        Integer id=loginUser.getId();
        //获取当前用户联系人列表
        FindByUserDto findByUserDto=new FindByUserDto();
        findByUserDto.setUserId(id);
        List<ContractGroupDto> contracts= contractService.getContractList(findByUserDto);
        //添加根节点
        TreeItem<ContractItemView> rootItem = new TreeItem<ContractItemView>();
        rootItem.setValue(null);
        contractTree.setRoot(rootItem);
        contractTree.setShowRoot(false);
        ClientCache.clearContract();
        ClientCache.clearGroup();
        //添加联系人到列表
        for (ContractGroupDto contract:contracts) {
            ContractItemView pane=new ContractItemView(contract.getGroupName());
            pane.setId("GROUP"+contract.getGroupId());
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
        contractTree.setContextMenu(groupMenu());

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
                    ChatUtils.openChatWindow(Integer.valueOf(userid),selectedItem.getNickName(),ImageUtiles.getUserIcon(selectedItem.getUserIcon()));
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
            //左键双击弹出聊天框
            if (click.getButton()==MouseButton.PRIMARY&&click.getClickCount() == 2) {
                TreeItem<ContractItemView> selectedItem = contractTree.getSelectionModel().getSelectedItem();
                if(selectedItem!=null){
                    String userid = selectedItem.getValue().getId();
                    if (!userid.contains("GROUP")) {
                        ChatUtils.openChatWindow(Integer.valueOf(userid),selectedItem.getValue().getNickName(),selectedItem.getValue().getUserImage());
                        selectedItem.getValue().setBlink(false);
                        selectedItem.getValue().stopBlink();
                    }else{
                        return;
                    }
                }
            }
            if (click.getButton()==MouseButton.PRIMARY&&click.getClickCount() == 1) {
                TreeItem<ContractItemView> selectedItem = contractTree.getSelectionModel().getSelectedItem();
                if(selectedItem!=null){
                    String userid = selectedItem.getValue().getId();
                    if(userid==null){
                        return;
                    }
                    ContractItemView contractItemView=selectedItem.getValue();
                    if (!userid.contains("GROUP")) {
                        if(this.focusGroup!=null){
                            this.focusGroup.setGroupNoFocus();
                        }
                        if(focusItem==null){
                            contractItemView.setFocus();
                            this.focusItem=contractItemView;
                        }else if(focusItem!=contractItemView){
                            this.focusItem.setNoFocus();
                            contractItemView.setFocus();
                            this.focusItem=contractItemView;
                        }
                    }else{
                        //收缩或者展开分组
                        if(selectedItem.isExpanded()){
                            selectedItem.setExpanded(false);
                        }else{
                            selectedItem.setExpanded(true);
                        }
                    }
                }
            }
        });
    }

    public ContextMenu userMenu(){
        ContextMenu addMenu = new ContextMenu();
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
        addMenu.getItems().addAll(message,email,info,card,history,auth,remark,move,delete,jubao);
        return addMenu;
    }

    public ContextMenu groupMenu(){
        ContextMenu addMenu = new ContextMenu();
        MenuItem add = new MenuItem("添加分组",new ImageView(new Image("images/icon/add.png")));
        MenuItem edit = new MenuItem("重命名",new ImageView(new Image("images/icon/edit.png")));
        MenuItem delete = new MenuItem("删除该组",new ImageView(new Image("images/icon/delete.png")));
        MenuItem flush = new MenuItem("刷新好友列表",new ImageView(new Image("images/icon/flush.png")));
        MenuItem onlyOnline = new MenuItem("显示在线联系人",new ImageView(new Image("images/icon/onlyOnline.png")));
        MenuItem visableFor = new MenuItem("隐身对改分组可见",new ImageView(new Image("images/icon/invisableFor.png")));
        MenuItem invisableFor = new MenuItem("在线对该分组隐身",new ImageView(new Image("images/icon/invisableFor.png")));
        addMenu.getItems().addAll(add,edit,delete,flush,onlyOnline,visableFor,invisableFor);
        add.setOnAction(this::addGroup);
        delete.setOnAction(this::deleteGroup);
        edit.setOnAction(this::editGroup);
        flush.setOnAction(this::flushGroup);
        onlyOnline.setOnAction(this::showOnlyOnline);
        visableFor.setOnAction(this::visableForGroup);
        invisableFor.setOnAction(this::invisableForGroup);
        return addMenu;
    }

    private void invisableForGroup(ActionEvent event) {

    }

    private void visableForGroup(ActionEvent event) {

    }

    private void showOnlyOnline(ActionEvent event) {

    }

    private void flushGroup(ActionEvent event) {

    }

    private void editGroup(ActionEvent event) {
        TreeItem<ContractItemView> item=contractTree.getSelectionModel().getSelectedItem();
        if(item==null){
            return;
        }
        ContractItemView contractItemView=item.getValue();
        if(contractItemView.getId().contains("GROUP")){
            TextField textField=contractItemView.getGroupName();
            contractItemView.setGroupEditable();
            textField.focusedProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if(!newPropertyValue)
                    {
                        if(textField.isEditable()){
                            chatService.updateGroup(contractItemView.getId(),textField.getText());
                            textField.setEditable(false);
                            contractItemView.setGroupEditDisable();
                        }
                    }
                }
            });
        }else{

        }
    }


    private void deleteGroup(ActionEvent event) {
        TreeItem<ContractItemView> item=contractTree.getSelectionModel().getSelectedItem();
        if(item==null){
            return;
        }
        ContractItemView contractItemView=item.getValue();
        if(contractItemView.getId().contains("GROUP")){
            //删除分组,原分组下的人移到默认的分组中
            String groupId=contractItemView.getId();
            chatService.deleteGroup(UserUtils.getCurrentUserId(),Integer.valueOf(groupId.substring(groupId.indexOf("P")+1,groupId.length())));
            initContract(UserUtils.getCurrentUser());
        }else{
            //删除用户
            String parentId=item.getParent().getValue().getId();
            Integer pid=Integer.valueOf(parentId.substring(parentId.indexOf("P")+1,parentId.length()));
            chatService.deleteFriend(pid,Integer.valueOf(contractItemView.getId()));
            initContract(UserUtils.getCurrentUser());
        }
    }

    private void addGroup(ActionEvent event) {
            TreeItem<ContractItemView> newGroup = new TreeItem<ContractItemView>();
            ContractItemView itemView=new ContractItemView("");
            TextField textField=itemView.getGroupName();
            textField.setText("未命名");
            itemView.setId("未命名");
            itemView.setGroupEditable();
            textField.focusedProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                {
                    if(!newPropertyValue)
                    {
                        if(textField.isEditable()){
                            ContractGroup contractGroup= chatService.createNewGroup(textField.getText());
                            textField.setEditable(false);
                            itemView.setGroupEditDisable();
                            itemView.setId("GROUP"+contractGroup.getId());
                        }
                    }
                }
            });
            newGroup.setValue(itemView);
            //新分组都挂在根节点下
            contractTree.getRoot().getChildren().add(newGroup);
    }
}
