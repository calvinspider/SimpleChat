package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.entity.Contract;
import org.yang.zhang.entity.MessageInfo;
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
    public TreeView<Label> GroupList;

    @FXML
    public ListView<Label> messageList;

    @FXML
    public Label nameLabel;

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

    public void initContract(){
        //联系人列表根节点
        TreeItem<Label> rootItem = new TreeItem<Label>();
        Label root=new Label();
        root.setVisible(false);
        rootItem.setValue(root);
        GroupList.setRoot(rootItem);
        //隐藏根节点
        GroupList.setShowRoot(false);
        //默认展开节点
        rootItem.setExpanded(true);
        //获取当前用户联系人列表
        FindByUserDto findByUserDto=new FindByUserDto();
        findByUserDto.setUserId(nameLabel.getText());
        List<Contract> contracts= contractService.getContractList(findByUserDto);
        //添加联系人到列表
        for (Contract contract:contracts){
            TreeItem<Label> i1 = new TreeItem<Label>();
            ImageView imageView=new ImageView("images/personIcon.jpg");
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            Label sign=new Label(contract.getContractId(),imageView);
            sign.setId(contract.getContractId());
            i1.setValue(sign);
            rootItem.getChildren().add(i1);
        }

        //点击联系人弹出聊天框
        GroupList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue,Object newValue) {
                TreeItem<Label> selectedItem = (TreeItem<Label>) newValue;
                String id=selectedItem.getValue().getText();
                openChatWindow(id);
            }
        });

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




}
