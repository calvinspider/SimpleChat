package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.User;
import org.yang.zhang.service.ContractService;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.StageManager;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@FXMLController
public class MainController  implements Initializable {

    @FXML
    public Tab groupTab;
    @FXML
    public FlowPane groupPane;
    @FXML
    public Tab messageTab;
    @FXML
    public ListView<Label> messageList;
    @FXML
    public Tab spaceTab;
    @FXML
    public ListView<Label> spaceList;

    @FXML
    public ImageView userIcon;
    @FXML
    public Label nameLabel;
    @FXML
    public Label personWord;
    @FXML
    public TextField searchField;

    @FXML
    public Menu addFriendMenu;

    @Autowired
    public ContractService contractService;


    /**
     * 主页面初始化
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initContract(Integer id){
        //获取当前用户联系人列表
        FindByUserDto findByUserDto=new FindByUserDto();
        findByUserDto.setUserId(id);
        List<ContractGroupDto> contracts= contractService.getContractList(findByUserDto);
        //添加联系人到列表
        for (ContractGroupDto contract:contracts){
            //新建分组
            TreeView<Label> treeView=new TreeView<Label>();
            //分组根节点
            TreeItem<Label> rootItem = new TreeItem<Label>();
            Label root=new Label();
            root.setVisible(false);
            rootItem.setValue(root);
            treeView.setRoot(rootItem);
            //隐藏根节点
            treeView.setShowRoot(false);
            //默认展开节点
            rootItem.setExpanded(false);
            List<User> users=contract.getUserList();
            for (User user:users){
                TreeItem<Label> i1 = new TreeItem<Label>();
                ImageView imageView=new ImageView("images/personIcon.jpg");
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                Label sign=new Label(user.getNickName(),imageView);
                sign.setId(String.valueOf(user.getId()));
                i1.setValue(sign);
                rootItem.getChildren().add(i1);
            }
            //为每一个分组绑定点击时间
            treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue,Object newValue) {
                    TreeItem<Label> selectedItem = (TreeItem<Label>) newValue;
                    String id=selectedItem.getValue().getId();
                    openChatWindow(id);
                }
            });
            groupPane.getChildren().add(treeView);
        }

        //向服务器注册当前channel
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setSourceclientid(nameLabel.getText());
        messageInfo.setMsgcontent(Constant.REGEIST);
        messageInfo.setTime(new Date());
        NettyClient.sendMessage(JsonUtils.toJson(messageInfo));

    }

    private void openChatWindow(String id) {
        try {
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
            chatStage.show();
            //注册聊天框
            StageManager.registerStage(id,chatStage);

            chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    StageManager.unregisterStage(id);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Tab getGroupTab() {
        return groupTab;
    }

    public FlowPane getGroupPane() {
        return groupPane;
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

    public ListView<Label> getMessageList() {
        return messageList;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public ImageView getUserIcon() {
        return userIcon;
    }

    public Label getPersonWord() {
        return personWord;
    }
}
