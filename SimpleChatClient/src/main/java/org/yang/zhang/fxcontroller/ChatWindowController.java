package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.utils.DateUtils;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.view.RightMessageBubble;

@FXMLController
public class ChatWindowController  implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label userId;

    @FXML
    private ImageView userIcon;

    @FXML
    private VBox chatHistory;

    @FXML
    private ScrollPane chatPane;

    @FXML
    private TextArea chatArea;

    @FXML
    private Button closeBtn;

    @FXML
    private Button sendBtn;

    @Autowired
    private MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void sendMessage(ActionEvent event) {
        if("".equals(chatArea.getText())){
            return;
        }
        String targetUser=userId.getText();
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setSourceclientid(UserUtils.getCurrentUserId());
        messageInfo.setTargetclientid(Integer.valueOf(targetUser));
        messageInfo.setMsgcontent(chatArea.getText());
        messageInfo.setTime(new Date());

        //向聊天框中添加聊天内容
        RightMessageBubble rightMessageBubble=new RightMessageBubble(chatArea.getText(),UserUtils.getUserIcon());
        Label time=new Label(DateUtils.formatDateTime(new Date()));
        time.setPrefWidth(570);
        time.setAlignment(Pos.CENTER);
        time.setPrefHeight(200);
        time.setStyle("-fx-padding: 10,10,10,10");
        chatHistory.getChildren().add(time);
        chatHistory.getChildren().add(rightMessageBubble.getPane());
        chatPane.setVvalue(chatPane.getVvalue()+25);

        //情况打字区
        chatArea.setText("");
        //发送消息
        NettyClient.sendMessage(JsonUtils.toJson(messageInfo));
    }
}
