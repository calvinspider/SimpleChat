package org.yang.zhang.fxcontroller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.AnimationUtils;
import org.yang.zhang.utils.ChatViewManager;
import org.yang.zhang.utils.DateUtils;
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
    private Label roomId;
    @FXML
    private ScrollPane chatPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    sendMessage(null);
                    keyEvent.consume();
                }
            }
        });
    }

    @FXML
    public void sendMessage(ActionEvent event) {
        if("".equals(chatArea.getText())){
            return;
        }
        String rid=roomId.getText();
        //向聊天框中添加聊天内容
        RightMessageBubble rightMessageBubble=new RightMessageBubble(chatArea.getText(),UserUtils.getUserIcon());
        rightMessageBubble.getPane().setPrefWidth(670);
        Label time=new Label(DateUtils.formatDateTime(new Date()));
        time.setPrefWidth(550);
        time.setAlignment(Pos.CENTER);
        time.setPrefHeight(30);
        time.setStyle("-fx-padding: 10,10,10,10");
        VBox chatHistory=(VBox)chatPane.getContent();
        chatHistory.getChildren().add(time);
        chatHistory.getChildren().add(rightMessageBubble.getPane());
        AnimationUtils.slowScrollToBottom(chatPane);
        //发送消息
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setSourceclientid(UserUtils.getCurrentUserId());
        messageInfo.setTargetclientid(Integer.valueOf(rid));
        messageInfo.setMsgtype(3);//群聊信息
        messageInfo.setMsgcontent(chatArea.getText());
        messageInfo.setTime(new Date());
        NettyClient.sendMessage(JsonUtils.toJson(messageInfo));
        //情况打字区
        chatArea.setText("");
    }

    @FXML
    public void closeChatWindow(){
        StageManager.getStage("CHATROOM"+roomId.getText()).close();
        StageManager.unregisterStage(roomId.getText());
    }
}
