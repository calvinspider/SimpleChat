package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.enums.BubbleType;
import org.yang.zhang.enums.IDType;
import org.yang.zhang.enums.MessageType;
import org.yang.zhang.utils.ActionManager;
import org.yang.zhang.utils.AnimationUtils;
import org.yang.zhang.utils.ChatUtils;
import org.yang.zhang.utils.ChatViewManager;
import org.yang.zhang.utils.FileSizeUtil;
import org.yang.zhang.utils.IDUtils;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.view.RightFileMessageView;
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
        initEvent();
    }

    private void initEvent() {

        root.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        root.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                VBox chatHistory=(VBox)chatPane.getContent();
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    for (File file:db.getFiles()) {
                        String filePath = file.getAbsolutePath();

                    //将文件框添加到聊天框中
                    RightFileMessageView messageView=new RightFileMessageView(null,filePath
                            , "("+FileSizeUtil.getFileOrFilesSize(file,FileSizeUtil.SIZETYPE_KB)+"KB"+")"
                            ,UserUtils.getUserIcon());
                    chatHistory.getChildren().add(messageView.getRoot());
                        Platform.runLater(()->chatPane.setVvalue(1.0));
                        AnimationUtils.slowScrollToBottom(chatPane);
                    Float[] values = new Float[] {-1.0f, 0f, 0.6f, 1.0f};
                    for (int i = 0; i < values.length; i++) {
                        messageView.getProcessBar().setProgress(values[i]);
                        final ProgressIndicator pin = new ProgressIndicator();
                        pin.setProgress(values[i]);
                    }
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
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
