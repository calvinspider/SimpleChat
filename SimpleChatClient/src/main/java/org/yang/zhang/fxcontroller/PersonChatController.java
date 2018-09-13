package org.yang.zhang.fxcontroller;

import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.constants.StageCodes;
import org.yang.zhang.enums.BubbleType;
import org.yang.zhang.enums.IDType;
import org.yang.zhang.enums.MessageType;
import org.yang.zhang.socket.NettyClient;
import org.yang.zhang.sound.Client;
import org.yang.zhang.utils.*;
import org.yang.zhang.view.ChatView;
import org.yang.zhang.view.RightFileMessageView;
import org.yang.zhang.view.RightMessageBubble;
import org.yang.zhang.view.SendFileView;
import org.yang.zhang.view.SmallFileMessage;

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

    private SendFileView sendFileView;

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
                if (db.hasFiles()) {
                    event.setDropCompleted(true);
                    event.consume();
                    openSendFileWindow();
                    for (File file:db.getFiles()) {
                        String filePath = file.getAbsolutePath();
                        String fileName=filePath.substring(filePath.lastIndexOf(File.separator)+1,filePath.length());
                        SmallFileMessage smallFileMessage=new SmallFileMessage(new Image("images/icon/file.png"),fileName);
                        if(sendFileView!=null){
                            VBox vBox=sendFileView.getvBox();
                            vBox.getChildren().add(smallFileMessage.getRoot());
                        }

                        ThreadPoolUtils.run(()->{
                            NettyClient.sendFileWithProcess(file,fileName,smallFileMessage.getProcessbar());
                            //将文件框添加到聊天框中
                            RightFileMessageView messageView=new RightFileMessageView(null,fileName
                                    , "("+FileSizeUtil.getFileOrFilesSize(file,FileSizeUtil.SIZETYPE_KB)+"KB"+")"
                                    ,UserUtils.getUserIcon());
                            chatHistory.getChildren().add(messageView.getRoot());
                            Platform.runLater(()->chatPane.setVvalue(1.0));
                            AnimationUtils.slowScrollToBottom(chatPane);
                        });
                    }
                }

            }
        });
    }

    public void openSendFileWindow() {
        String id=userId.getText();
        Stage window=StageManager.getStage(IDUtils.formatID(id,IDType.CHATWINDOW)+id);
        if(window!=null){
            Stage chat=ChatViewManager.getStage(id).getChatStage();
            window.setX(chat.getX()+680);
            window.setY(chat.getY()+60);
            window.show();
        }else{
            Stage stage=new Stage();
            sendFileView=new SendFileView();
            Scene scene=new Scene(sendFileView.getRoot());
            stage.setScene(scene);
            Stage chat=ChatViewManager.getStage(id).getChatStage();
            stage.setX(chat.getX()+680);
            stage.setY(chat.getY()+60);
            stage.initStyle(StageStyle.UNDECORATED);
            sendFileView.getRoot().setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            sendFileView.getRoot().setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            stage.show();
            StageManager.registerStage(IDUtils.formatID(id,IDType.CHATWINDOW)+id,stage);
        }
    }

    public void closeSendFileWindow() {
        String id=userId.getText();
        Stage window=StageManager.getStage(IDUtils.formatID(id,IDType.CHATWINDOW)+id);
        if(window!=null){
            window.close();
            StageManager.unregisterStage(IDUtils.formatID(id,IDType.CHATWINDOW)+id);
            sendFileView=null;
        }
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
            closeSendFileWindow();
        }
    }

    @FXML
    public void minWindow(){
        String id=userId.getText();
        Stage stage=StageManager.getStage(IDUtils.formatID(userId.getText(),IDType.CHATWINDOW));
        stage.setIconified(true);
        Stage window=StageManager.getStage(IDUtils.formatID(id,IDType.CHATWINDOW)+id);
        if(window!=null){
            window.hide();
        }
    }



}
