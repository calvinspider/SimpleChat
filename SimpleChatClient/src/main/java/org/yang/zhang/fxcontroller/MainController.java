package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.TreeViewSample;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.enums.StageType;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.User;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.service.ContractService;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.ChatViewManager;
import org.yang.zhang.utils.ClientContextUtils;
import org.yang.zhang.utils.DateUtils;
import org.yang.zhang.utils.ImageUtiles;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.view.ChatView;
import org.yang.zhang.view.ContractItemView;
import org.yang.zhang.view.RecentContractView;
import org.yang.zhang.view.SearchContractView;

import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.sun.deploy.uitoolkit.DragListener;

@FXMLController
public class MainController  implements Initializable {

    @FXML
    public Tab groupTab;
    @FXML
    public TreeView<Pane> contractTree;
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

    private List<TreeItem<Pane>> groupList=new ArrayList<>();
    public static Map<String,TreeItem<Pane>> contractMap=new HashMap<>();
    public static Map<String,Image> userIconMap=new HashMap<>();
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
        regesterChannel();
        initContract(user.getId());
        initRecentMessage(user.getId());
        initTabPane(user.getId());
        initMenuBar(user.getId());
    }

    private void initMainPane(User user) {
        userIcon.setImage(ImageUtiles.getHttpImage(Constant.serverHost+"/static/images/userIcon/"+user.getIconUrl()));
        nameLabel.setText(String.valueOf(user.getId()));
        personWord.setFocusTraversable(false);
        personWord.setText(user.getPersonWord());
    }

    private void initMenuBar(Integer id) {
        addFriendBtn.setOnMouseClicked(click->{
            try {
                if(StageManager.getStage(StageCodes.SEARCHCONTRACT)==null){
                    Stage searchContract=new Stage();
                    Scene scene=new Scene(searchContractView.getView());
                    searchContract.setScene(scene);
                    searchContract.setResizable(false);
                    searchContract.show();
                    searchContractController.init(String.valueOf(ClientContextUtils.getCurrentUser().getId()));
                    StageManager.registerStage(StageCodes.SEARCHCONTRACT,searchContract);
                }else{
                    StageManager.getStage(StageCodes.SEARCHCONTRACT).show();
                    searchContractController.init(String.valueOf(ClientContextUtils.getCurrentUser().getId()));
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private void regesterChannel() {
        //向服务器注册当前channel
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setSourceclientid(nameLabel.getText());
        messageInfo.setMsgcontent(Constant.REGEIST);
        messageInfo.setTime(new Date());
        NettyClient.sendMessage(JsonUtils.toJson(messageInfo));

    }

    private void initTabPane(Integer id) {
        tabPane.setOnMouseClicked(click->{
           int index=tabPane.getSelectionModel().getSelectedIndex();
            System.out.println(index);
           //联系人
           if(index==0){
               initContract(id);
            //最近消息
           }else if (index==1){
               initRecentMessage(id);
           //空间列表
           }else if(index==2){

           }
        });
    }

    private void initRecentMessage(Integer id) {
        ObservableList<Pane> items =FXCollections.observableArrayList ();
        List<RecentContract> recentChatLogDtos= chatService.getrecentContract(id);
        try {
            for (RecentContract recentContract:recentChatLogDtos){
                RecentContractView contractView=new RecentContractView(recentContract,String.valueOf(id));
                items.add(contractView.getRecentContractPane());
            }
            messageList.setItems(items);
            messageList.setOnMouseClicked(click->{
                if (click.getClickCount() == 2) {
                    Pane selectedItem = messageList.getSelectionModel().getSelectedItem();
                    String userid=selectedItem.getId();
                    ImageView imageView=(ImageView) selectedItem.lookup("#userIcon");
                    if (userid != null) {
                        openChatWindow(userid,imageView.getImage());
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initContract(Integer id){
        //获取当前用户联系人列表
        FindByUserDto findByUserDto=new FindByUserDto();
        findByUserDto.setUserId(id);
        List<ContractGroupDto> contracts= contractService.getContractList(findByUserDto);
        //添加根节点
        TreeItem<Pane> rootItem = new TreeItem<Pane>();
        rootItem.setValue(new Pane());
        rootItem.setExpanded(true);
        contractTree.setRoot(rootItem);
        contractTree.setShowRoot(false);
        groupList.clear();
        contractMap.clear();
        userIconMap.clear();
        userIconMap.put(String.valueOf(id),userIcon.getImage());
        //添加联系人到列表
        for (ContractGroupDto contract:contracts) {
            TreeItem<Pane> groupItem = new TreeItem<Pane>();
            Pane pane=new ContractItemView("",contract.getGroupName(),"",true).getItemPane();
            pane.setId("GROUP"+contract.getGroupId());
            groupItem.setValue(pane);
            groupList.add(groupItem);
            rootItem.getChildren().add(groupItem);
            List<User> users = contract.getUserList();
            for (User user : users) {
                TreeItem<Pane> item = new TreeItem<Pane>();
                ContractItemView contractItemView=new ContractItemView(user.getIconUrl(),String.valueOf(user.getId()),user.getPersonWord(),false);
                contractItemView.getItemPane().setId(String.valueOf(user.getId()));
                item.setValue(contractItemView.getItemPane());
                contractMap.put(String.valueOf(user.getId()),item);
                userIconMap.put(String.valueOf(user.getId()),contractItemView.getUserImage());
                groupItem.getChildren().add(item);
                groupItem.setExpanded(true);
            }
        }

        contractTree.setCellFactory(new Callback<TreeView<Pane>,TreeCell<Pane>>(){
            @Override
            public TreeCell<Pane> call(TreeView<Pane> pane) {
                return new ContractTreeCellImpl();
            }
        });

        contractTree.setOnMouseClicked(click -> {
            //左键双击弹出聊天框
            if (click.getButton()==MouseButton.PRIMARY&&click.getClickCount() == 2) {
                TreeItem<Pane> selectedItem = contractTree.getSelectionModel().getSelectedItem();
                if(selectedItem!=null){
                    String userid = selectedItem.getValue().getId();
                    if (!userid.contains("GROUP")) {
                        openChatWindow(userid,userIconMap.get(userid));
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
                TreeItem<Pane> newGroup = new TreeItem<Pane>();
                ContractItemView itemView=new ContractItemView("","","",true);
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
                newGroup.setValue(itemView.getItemPane());
                //新分组都挂在根节点下
                contractTree.getRoot().getChildren().add(newGroup);
            }
        });
        contractTree.setEditable(true);
        contractTree.setContextMenu(addMenu);
    }


    private void openChatWindow(String id,Image userIcon) {
        try {
            //不重复打开聊天框
            if(ChatViewManager.getStage(id)!=null){
                return;
            }
            List<MessageInfo> messageInfos=chatService.getOneDayRecentChatLog(id,nameLabel.getText());
            ChatView chatView= new ChatView(id,userIcon,String.valueOf(ClientContextUtils.getCurrentUser().getId()),messageInfos);
            ChatViewManager.registerStage(id,chatView);
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

    private final class ContractTreeCellImpl extends TreeCell<Pane> {

        private String cellId;

        public ContractTreeCellImpl() {

            setOnDragEntered(e -> {

                //收缩分组
                for(TreeItem<Pane> paneTreeItem:groupList){
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
                initContract(ClientContextUtils.getCurrentUser().getId());
            });
            setOnDragExited(e -> {

                for(TreeItem<Pane> paneTreeItem:groupList){
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
        public void updateItem(Pane pane, boolean empty) {
            super.updateItem(pane,empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                this.cellId=pane.getId();
                setGraphic(pane);
            }
        }


    }
}
