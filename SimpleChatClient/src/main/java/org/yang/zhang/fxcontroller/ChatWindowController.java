package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import io.netty.channel.Channel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.entity.MessageInfo;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.JsonUtils;

@FXMLController
public class ChatWindowController  implements Initializable {

    private NettyClient client;

    @FXML
    private Label nameLabel;

    @FXML
    private Pane chatHistory;

    @FXML
    private Pane otherChat;

    @FXML
    private Pane myChat;

    @FXML
    private TextArea chatArea;

    @FXML
    private Button closeBtn;

    @FXML
    private Button sendBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client=new NettyClient();
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setSourceclientid("10003");
        messageInfo.setMsgcontent("register");
        messageInfo.setTime(new Date());
        client.sendMessage(JsonUtils.toJson(messageInfo));
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        String targetUser=nameLabel.getText();
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setSourceclientid("10003");
        messageInfo.setTargetclientid(targetUser);
        messageInfo.setMsgcontent(chatArea.getText());
        messageInfo.setTime(new Date());
        Label label=new Label(chatArea.getText());
        myChat.getChildren().add(label);
        client.sendMessage(JsonUtils.toJson(messageInfo));
        chatArea.setText("");
    }




}
