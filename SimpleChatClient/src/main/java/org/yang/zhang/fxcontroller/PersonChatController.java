package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.enums.BubbleType;
import org.yang.zhang.enums.IDType;
import org.yang.zhang.enums.MessageType;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.utils.ActionManager;
import org.yang.zhang.utils.AnimationUtils;
import org.yang.zhang.utils.ChatUtils;
import org.yang.zhang.utils.ChatViewManager;
import org.yang.zhang.utils.IDUtils;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.utils.DateUtils;
import org.yang.zhang.utils.JsonUtils;
import org.yang.zhang.view.RightMessageBubble;

@FXMLController
public class PersonChatController implements Initializable {

    @FXML
    private Pane root;
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
        ActionManager.setKeyPressAction(chatArea,KeyCode.ENTER,this::sendMessage);
    }

    @FXML
    public void sendMessage() {
        if(StringUtils.isBlank(chatArea.getText())){
            return;
        }
        ChatUtils.sendMessage(Integer.valueOf(userId.getText()),MessageType.NORMAL,chatArea.getText());
        ChatUtils.appendBubble(chatPane,BubbleType.RIGHT,chatArea.getText(),UserUtils.getUserIcon(),670D);
        chatArea.setText("");
    }

    @FXML
    public void closeWindow(){
        Stage stage=StageManager.getStage(IDUtils.formatID(userId.getText(),IDType.CHATWINDOW));
        if(stage!=null){
            stage.close();
            ChatViewManager.unregisterStage(IDUtils.formatID(userId.getText(),IDType.ID));
        }
    }

    @FXML
    public void minWindow(){
        Stage stage=StageManager.getStage(IDUtils.formatID(userId.getText(),IDType.CHATWINDOW));
        stage.setIconified(true);
    }
}
