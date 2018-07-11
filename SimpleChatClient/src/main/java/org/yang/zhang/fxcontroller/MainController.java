package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.User;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.service.ContractService;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.DateUtils;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.StageManager;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@FXMLController
public class MainController  implements Initializable {

    @FXML
    public Tab groupTab;
    @FXML
    public TreeView<Label> contractTree;
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
    public Menu addFriendMenu;
    @FXML
    public Menu systemMenu;

    @Autowired
    public ContractService contractService;

    @Autowired
    private ChatService chatService;


    /**
     * 主页面初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(Integer id){
        regesterChannel();
        initContract(id);
        initRecentMessage(id);
        initTabPane(id);
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
                Pane pane=FXMLLoader.load(getClass().getResource("/fxml/RecentContract.fxml"));
                Label namelabel = (Label)pane.lookup("#namelabel");
                if(recentContract.getTargetUserid().equals(String.valueOf(id))){
                    namelabel.setText(recentContract.getSourceUserid());
                }else{
                    namelabel.setText(recentContract.getTargetUserid());
                }
                Label messagelabel = (Label)pane.lookup("#messagelabel");
                messagelabel.setText(recentContract.getLastMessage());
                Label timelabel = (Label)pane.lookup("#timelabel");
                timelabel.setText(DateUtils.formatDate(recentContract.getLastMessageDate(),"YYYY-MM-dd"));
                ImageView contracticon=(ImageView)pane.lookup("#contracticon");
                contracticon.setImage(new Image("images/personIcon.jpg"));
                contracticon.setFitWidth(45);
                contracticon.setFitHeight(45);
                items.add(pane);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        messageList.setItems(items);

    }

    public void initContract(Integer id){

        //获取当前用户联系人列表
        FindByUserDto findByUserDto=new FindByUserDto();
        findByUserDto.setUserId(id);
        List<ContractGroupDto> contracts= contractService.getContractList(findByUserDto);
        final VBox vb = new VBox();
        //添加根节点
        TreeItem<Label> rootItem = new TreeItem<Label>();
        Label root=new Label();
        rootItem.setValue(root);
        rootItem.setExpanded(true);
        contractTree.setRoot(rootItem);
        contractTree.setShowRoot(false);
        //添加联系人到列表
        for (ContractGroupDto contract:contracts) {
            TreeItem<Label> groupItem = new TreeItem<Label>();
            groupItem.setValue(new Label(contract.getGroupName()));
            rootItem.getChildren().add(groupItem);
            List<User> users = contract.getUserList();
            for (User user : users) {
                TreeItem<Label> i1 = new TreeItem<Label>();
                ImageView imageView = new ImageView("images/personIcon.jpg");
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                Label sign = new Label(user.getNickName(), imageView);
                sign.setId(String.valueOf(user.getId()));
                i1.setValue(sign);
                groupItem.getChildren().add(i1);
                groupItem.setExpanded(true);
            }
        }

        contractTree.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                TreeItem<Label> selectedItem = contractTree.getSelectionModel().getSelectedItem();
                String userid = selectedItem.getValue().getId();
                if (userid != null) {
                    openChatWindow(userid);
                }

            }
        });
    }



    private void openChatWindow(String id) {
        try {
            //不重复打开聊天框
            if(StageManager.getStage(id)!=null){
                return;
            }
            //创建聊天框
            Stage chatStage=new Stage();
            Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/fxml/chatWindow.fxml")));
            chatStage.setScene(scene);
            //聊天框大小不可修改
            chatStage.setResizable(false);
            //目标联系人
            Label nameLabel1 = (Label)scene.lookup("#nameLabel");
            nameLabel1.setText(id);
            //当前登陆用户
            Label sourceNameLabel = (Label)scene.lookup("#sourceNameLabel");
            sourceNameLabel.setText(nameLabel.getText());
            sourceNameLabel.setVisible(false);
            chatStage.show();
            //注册聊天框
            StageManager.registerStage(id,chatStage);

            chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    StageManager.unregisterStage(id);
                }
            });
            String sourceId=sourceNameLabel.getText();
            //聊天记录框
            VBox chatHistory = (VBox)scene.lookup("#chatHistory");
            //TODO 滚动条置底 没反应
            ScrollPane chatPane = (ScrollPane)scene.lookup("#chatPane");
            chatPane.setVvalue(chatPane.getMaxHeight());
            //获取近一天的聊天记录
            List<MessageInfo> messageInfos=chatService.getOneDayRecentChatLog(id,sourceId);
            for (MessageInfo messageInfo:messageInfos){
                ImageView imageView=new ImageView("images/personIcon.jpg");
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                Label label=new Label(messageInfo.getMsgcontent(),imageView);
                if(id.equals(messageInfo.getSourceclientid())){
                    label.setAlignment(Pos.CENTER_LEFT);
                }else{
                    label.setAlignment(Pos.CENTER_RIGHT);
                }
                label.setPrefWidth(570);
                label.setStyle("-fx-padding: 5 5 5 5");
                Label time=new Label(DateUtils.formatDateTime(messageInfo.getTime()));
                time.setPrefWidth(570);
                time.setAlignment(Pos.CENTER);
                chatHistory.getChildren().add(time);
                chatHistory.getChildren().add(label);
            }
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

    public Tab getGroupTab() {
        return groupTab;
    }

    public Tab getMessageTab() {
        return messageTab;
    }

    public Tab getSpaceTab() {
        return spaceTab;
    }

    public ListView<Label> getSpaceList() {
        return spaceList;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public Menu getAddFriendMenu() {
        return addFriendMenu;
    }

    public ListView<Pane> getMessageList() {
        return messageList;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public ImageView getUserIcon() {
        return userIcon;
    }

    public TextField getPersonWord() {
        return personWord;
    }
}
