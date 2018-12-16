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
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yang.zhang.constants.Constant;
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
                Dragboard db = event.getDragboard();
                event.setDropCompleted(true);
                event.consume();
                if (db.hasFiles()) {
                    //打开文件传输框
                    SendFileWindowManager.openStage(getUserId());
                    SendFileView sendFileView=SendFileWindowManager.getView(getUserId());
                    VBox vBox=sendFileView.getvBox();
                    int index=0;
                    for (File file:db.getFiles()) {
                        String filePath = file.getAbsolutePath();
                        String fileName=filePath.substring(filePath.lastIndexOf(File.separator)+1,filePath.length());

                        SmallFileMessage smallFileMessage=new SmallFileMessage(new Image(Constant.DEFAULT_FILE_ICON),fileName);
                        //插入文件传送框
                        vBox.getChildren().add(smallFileMessage.getRoot());

                        //提交文件传输线程
                        ThreadPoolUtils.run(()->{
                            NettyClient.sendFileWithProcess(UserUtils.getCurrentUserId()
                                    ,Integer.valueOf(getUserId())
                                    ,file,new Date().getTime()+fileName
                                    ,smallFileMessage.getProcessbar());
                        Platform.runLater(()->{
                            //文件传送完之后删除右侧文件节点
                            vBox.getChildren().remove(smallFileMessage.getRoot());
                            if(vBox.getChildren().size()==1){
                                SendFileWindowManager.closeStage(getUserId());
                            }
                            //传输完成后将文件框添加到聊天框中
                            RightFileMessageView messageView=new RightFileMessageView(new Image(Constant.DEFAULT_FILE_ICON)
                                    ,fileName
                                    , "("+FileSizeUtil.getFileOrFilesSize(file,FileSizeUtil.SIZETYPE_KB)+"KB"+")"
                                    ,UserUtils.getUserIcon());
                            VBox chatHistory=(VBox)chatPane.getContent();

                            chatHistory.getChildren().add(messageView.getRoot());
                            //聊天框下拉到底
                            chatPane.setVvalue(1.0);
                            AnimationUtils.slowScrollToBottom(chatPane);
                        });
                        });
                        index++;
                    }
                }

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
        String id=getUserId();
        Stage stage=StageManager.getStage(IDUtils.formatID(id,IDType.CHATWINDOW));
        if(stage!=null){
            stage.close();
            ChatViewManager.unregisterStage(IDUtils.formatID(id,IDType.ID));
            SendFileWindowManager.closeStage(id);
        }
    }

    @FXML
    public void minWindow(){
        String id=getUserId();
        Stage stage=StageManager.getStage(IDUtils.formatID(id,IDType.CHATWINDOW));
        stage.setIconified(true);
        SendFileWindowManager.hideStage(id);
    }

    public String getUserId(){
        return userId.getText();
    }


}
