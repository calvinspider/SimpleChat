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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.ClientContextUtils;
import org.yang.zhang.utils.DateUtils;
import org.yang.zhang.utils.JsonUtils;

@FXMLController
public class ChatWindowController  implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label sourceNameLabel;

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
        String targetUser=nameLabel.getText();
        MessageInfo messageInfo=new MessageInfo();
        messageInfo.setSourceclientid(String.valueOf(ClientContextUtils.getCurrentUser().getId()));
        messageInfo.setTargetclientid(targetUser);
        messageInfo.setMsgcontent(chatArea.getText());
        messageInfo.setTime(new Date());

        //向聊天框中添加聊天内容
        ImageView imageView= new ImageView(MainController.userIconMap.get(String.valueOf(ClientContextUtils.getCurrentUser().getId())));
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);
        Label label=new Label(chatArea.getText(),imageView);
        label.setAlignment(Pos.CENTER_RIGHT);
        label.setPrefWidth(570);
        label.setStyle("-fx-padding: 5 5 5 5");

        Label time=new Label(DateUtils.formatDateTime(new Date()));
        time.setPrefWidth(570);
        time.setAlignment(Pos.CENTER);
        chatHistory.getChildren().add(time);
        chatHistory.getChildren().add(label);
        chatPane.setVvalue(chatPane.getVvalue()+25);

        //情况打字区
        chatArea.setText("");
        //发送消息
        NettyClient.sendMessage(JsonUtils.toJson(messageInfo));
    }
}
