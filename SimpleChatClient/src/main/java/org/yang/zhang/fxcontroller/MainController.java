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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.view.ChatView;
import org.yang.zhang.view.ContractItemView;
import org.yang.zhang.view.GroupItemView;
import org.yang.zhang.view.RecentContractView;
import org.yang.zhang.view.SearchContractView;

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
                    StageManager.registerStage(StageCodes.SEARCHCONTRACT,searchContract);
                }else{
                    StageManager.getStage(StageCodes.SEARCHCONTRACT).show();
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
                    if (userid != null) {
                        openChatWindow(userid);
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
        //添加联系人到列表
        for (ContractGroupDto contract:contracts) {
            TreeItem<Pane> groupItem = new TreeItem<Pane>();
            groupItem.setValue(new GroupItemView(contract.getGroupName()).getGroupItepane());
            rootItem.getChildren().add(groupItem);
            List<User> users = contract.getUserList();
            for (User user : users) {
                TreeItem<Pane> item = new TreeItem<Pane>();
                ContractItemView contractItemView=new ContractItemView("",String.valueOf(user.getId()),user.getPersonWord());
                contractItemView.getItemPane().setId(String.valueOf(user.getId()));
                item.setValue(contractItemView.getItemPane());
                groupItem.getChildren().add(item);
                groupItem.setExpanded(true);
            }
        }

        contractTree.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                TreeItem<Pane> selectedItem = contractTree.getSelectionModel().getSelectedItem();
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
            if(ChatViewManager.getStage(id)!=null){
                return;
            }
            List<MessageInfo> messageInfos=chatService.getOneDayRecentChatLog(id,nameLabel.getText());
            ChatView chatView= new ChatView(id,String.valueOf(ClientContextUtils.getCurrentUser().getId()),messageInfos);
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
}
