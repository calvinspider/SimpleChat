package org.yang.zhang.fxcontroller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.yang.zhang.enums.BubbleType;
import org.yang.zhang.enums.IDType;
import org.yang.zhang.enums.MessageType;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.ActionManager;
import org.yang.zhang.utils.AnimationUtils;
import org.yang.zhang.utils.ChatUtils;
import org.yang.zhang.utils.ChatViewManager;
import org.yang.zhang.utils.DateUtils;
import org.yang.zhang.utils.IDUtils;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.view.RightMessageBubble;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 26 15:22
 */
@FXMLController
public class ChatRoomController implements Initializable {

    @FXML
    private TextArea chatArea;
    @FXML
    private Label roomIdLabel;
    @FXML
    private ScrollPane chatPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //回车发送信息
        ActionManager.setKeyPressAction(chatArea,KeyCode.ENTER,this::sendMessage);
    }

    @FXML
    public void sendMessage() {
        if(StringUtils.isBlank(chatArea.getText())){
            return;
        }
        String roomId=roomIdLabel.getText();
        //追加聊天框
        ChatUtils.appendBubble(chatPane,BubbleType.RIGHT,chatArea.getText(),UserUtils.getUserIcon(),670D);
        //发送消息
        ChatUtils.sendMessage(Integer.valueOf(roomId),MessageType.ROOM,chatArea.getText());
        chatArea.setText("");
    }

    @FXML
    public void closeChatWindow(){
        String roomStageId=IDUtils.formatID(roomIdLabel.getText(),IDType.ROOMWINDOW);
        Stage stage=StageManager.getStage(roomStageId);
        if(stage!=null){
            stage.close();
        }
        StageManager.unregisterStage(roomStageId);
    }

    @FXML
    public void closeApp(){
        closeChatWindow();
    }
}
