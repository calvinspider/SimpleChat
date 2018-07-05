package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.JsonUtils;

@FXMLController
public class ChatWindowController  implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label sourceNameLabel;

    @FXML
    private FlowPane chatHistory;

    @FXML
    private TextArea chatArea;

    @FXML
    private Button closeBtn;

    @FXML
    private Button sendBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void sendMessage(ActionEvent event) {
        if("".equals(chatArea.getText())){
            return;
        }
        String targetUser=nameLabel.getText();
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setSourceclientid(sourceNameLabel.getText());
        messageInfo.setTargetclientid(targetUser);
        messageInfo.setMsgcontent(chatArea.getText());
        messageInfo.setTime(new Date());

        //向聊天框中添加聊天内容
        ImageView imageView=new ImageView("images/personIcon.jpg");
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        Label label=new Label(chatArea.getText(),imageView);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPrefWidth(570);
        chatHistory.getChildren().add(label);

        //情况打字区
        chatArea.setText("");
        //发送消息
        NettyClient.sendMessage(JsonUtils.toJson(messageInfo));

    }
}
