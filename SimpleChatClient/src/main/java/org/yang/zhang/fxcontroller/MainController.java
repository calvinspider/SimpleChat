package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.User;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.service.ContractService;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.ChatViewManager;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.utils.ImageUtiles;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.StageManager;
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
import javafx.util.Duration;

import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

import javax.jws.soap.SOAPBinding;

@FXMLController
public class MainController  implements Initializable {

    @FXML
    public Tab groupTab;
    @FXML
    public TreeView<ContractItemView> contractTree;
    @FXML
    public Tab messageTab;
    @FXML
    public ListView<Pane> messageList;
    @FXML
    public Tab spaceTab;
    @FXML
    public ListView<Label> spaceList;
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

    private List<TreeItem<ContractItemView>> groupList=new ArrayList<>();
    private Map<String,TreeItem<ContractItemView>> contractMap=new HashMap<>();

    /**
     * 主页面初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(User user){
        initMainPane(user);
        regesterChannel(user);
        initContract(user);
        initRecentMessage(user);
        initTabPane(user);
        initMenuBar(user);
    }

    private void initMainPane(User user) {
        Image image=ImageUtiles.getUserIcon(user.getIconUrl());
        userIcon.setImage(image);
        UserUtils.setUserIcon(image);
        nameLabel.setText(user.getNickName());
        personWord.setText(user.getPersonWord());
        personWord.setFocusTraversable(false);
    }

    private void initMenuBar(User user) {
        Integer id=user.getId();
        addFriendBtn.setOnMouseClicked(click->{
            try {
                if(StageManager.getStage(StageCodes.SEARCHCONTRACT)==null){
                    Stage searchContract=new Stage();
                    Scene scene=new Scene(searchContractView.getView());
                    searchContract.setScene(scene);
                    searchContract.setResizable(false);
                    searchContract.show();
                    searchContractController.init(String.valueOf(id));
                    StageManager.registerStage(StageCodes.SEARCHCONTRACT,searchContract);
                }else{
                    StageManager.getStage(StageCodes.SEARCHCONTRACT).show();
                    searchContractController.init(String.valueOf(id));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private void regesterChannel(User user) {
        //向服务器注册当前channel
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setSourceclientid(user.getId());
        messageInfo.setMsgcontent(Constant.REGEIST);
        messageInfo.setTime(new Date());
        NettyClient.sendMessage(JsonUtils.toJson(messageInfo));
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
           //空间列表
           }else if(index==2){

           }
        });
    }

    private void initRecentMessage(User user) {
        Integer id=user.getId();
        ObservableList<Pane> items =FXCollections.observableArrayList();
        List<RecentContract> recentChatLogDtos= chatService.getrecentContract(id);
        try {
            for (RecentContract recentContract:recentChatLogDtos){
                RecentContractView contractView=new RecentContractView(recentContract,id);
                items.add(contractView.getRecentContractPane());
            }
            messageList.setItems(items);
            messageList.setOnMouseClicked(click->{
                if (click.getClickCount() == 2) {
                    Pane selectedItem = messageList.getSelectionModel().getSelectedItem();
                    String userid=selectedItem.getId();
                    ImageView imageView=(ImageView) selectedItem.lookup("#userIcon");
                    if (userid != null) {
                        openChatWindow(Integer.valueOf(userid),imageView.getImage());
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
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
        rootItem.setExpanded(true);
        contractTree.setRoot(rootItem);
        contractTree.setShowRoot(false);
        groupList.clear();
        contractMap.clear();
        //添加联系人到列表
        for (ContractGroupDto contract:contracts) {
            TreeItem<ContractItemView> groupItem = new TreeItem<ContractItemView>();
            ContractItemView pane=new ContractItemView(contract.getGroupName());
            pane.setId("GROUP"+contract.getGroupId());
            groupItem.setValue(pane);
            groupList.add(groupItem);
            rootItem.getChildren().add(groupItem);
            List<User> users = contract.getUserList();
            for (User user : users) {
                TreeItem<ContractItemView> item = new TreeItem<ContractItemView>();
                ContractItemView contractItemView=new ContractItemView(user);
                contractItemView.setId(String.valueOf(user.getId()));
                contractItemView.startBlink();
                item.setValue(contractItemView);
                contractMap.put(String.valueOf(user.getId()),item);
                UserUtils.setUser(user.getId(),user);
                ImageUtiles.setUserIcon(user.getId(),contractItemView.getUserImage());
                groupItem.getChildren().add(item);
                groupItem.setExpanded(true);
            }
        }

        contractTree.setCellFactory(new Callback<TreeView<ContractItemView>,TreeCell<ContractItemView>>(){
            @Override
            public TreeCell<ContractItemView> call(TreeView<ContractItemView> pane) {
                return new ContractTreeCellImpl();
            }
        });

        contractTree.setOnMouseClicked(click -> {
            //左键双击弹出聊天框
            if (click.getButton()==MouseButton.PRIMARY&&click.getClickCount() == 2) {
                TreeItem<ContractItemView> selectedItem = contractTree.getSelectionModel().getSelectedItem();
                if(selectedItem!=null){
                    String userid = selectedItem.getValue().getId();
                    if (!userid.contains("GROUP")) {
                        openChatWindow(Integer.valueOf(userid),selectedItem.getValue().getUserImage());

                    }else{
                        //如果是分组，双击修改分组名称
                        System.out.println("修改分组名称");
                    }
                }
            }
        });

        ContextMenu addMenu = new ContextMenu();
        MenuItem addMenuItem = new MenuItem("添加分组");
        MenuItem deleteMenu = new MenuItem("删除分组");
        addMenu.getItems().add(addMenuItem);
        addMenu.getItems().add(deleteMenu);
        addMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                TreeItem<ContractItemView> newGroup = new TreeItem<ContractItemView>();
                ContractItemView itemView=new ContractItemView("");
                TextField textField=itemView.getGroupName();
                textField.setEditable(true);
                Platform.runLater(()->{
                    textField.setFocusTraversable(true);
                    textField.requestFocus();
                    textField.focusTraversableProperty().setValue(true);
                });

                textField.focusedProperty().addListener(new ChangeListener<Boolean>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                    {
                        if(!newPropertyValue)
                        {
                            if(StringUtils.isBlank(textField.getText())){
                                textField.setText("未命名");
                            }
                            if(textField.isEditable()){
                                chatService.createNewGroup(textField.getText());
                            }
                            textField.setEditable(false);
                        }
                    }
                });
                newGroup.setValue(itemView);
                //新分组都挂在根节点下
                contractTree.getRoot().getChildren().add(newGroup);
            }
        });
        contractTree.setEditable(true);
        contractTree.setContextMenu(addMenu);
    }

    private void openChatWindow(Integer id,Image userIcon) {
        try {
            //不重复打开聊天框
            if(ChatViewManager.getStage(String.valueOf(id))!=null){
                return;
            }
            List<MessageInfo> messageInfos=chatService.getOneDayRecentChatLog(id,UserUtils.getCurrentUserId());
            ChatView chatView= new ChatView(id,userIcon,messageInfos);
            ChatViewManager.registerStage(String.valueOf(id),chatView);
        }catch (Exception e){
            e.printStackTrace();
        }
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

    private final class ContractTreeCellImpl extends TreeCell<ContractItemView> {

        private String cellId;

        public ContractTreeCellImpl() {

            setOnDragEntered(e -> {

                //收缩分组
                for(TreeItem<ContractItemView> paneTreeItem:groupList){
                    paneTreeItem.setExpanded(false);
                }
                ClipboardContent content = new ClipboardContent();
                content.putString(this.cellId);
                e.getDragboard().setContent(content);
                e.consume();
            });
            setOnDragDetected(e -> {
                Dragboard db = startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString( "Hello!" );
                db.setContent(content);
                e.consume();
            });
            setOnDragDone(e -> {

                e.consume();
            });
            setOnDragDropped(e -> {
                //将用户移动到目标分组中
                Dragboard dragboard=e.getDragboard();
                String itemId=dragboard.getString();
                String newGroupId=this.cellId;
                String oldGroupId=contractMap.get(itemId).getParent().getValue().getId();
                chatService.updateContractGroup(itemId,oldGroupId.substring(oldGroupId.indexOf("P")+1,oldGroupId.length()),
                        newGroupId.substring(newGroupId.indexOf("P")+1,newGroupId.length()));
                e.setDropCompleted(true);
                e.consume();
                initContract(UserUtils.getCurrentUser());
            });
            setOnDragExited(e -> {
                for(TreeItem<ContractItemView> paneTreeItem:groupList){
                    paneTreeItem.setExpanded(true);
                }
                e.consume();
            });
            setOnDragOver(event -> {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            });


        }

        @Override
        public void updateItem(ContractItemView pane, boolean empty) {
            super.updateItem(pane,empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                this.cellId=pane.getId();
                setGraphic(pane.getItemPane());
            }
        }


    }
}
