package org.yang.zhang.view;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.yang.zhang.enums.BubbleType;
import org.yang.zhang.enums.IDType;
import org.yang.zhang.fxcontroller.MainController;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.utils.*;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 11:16
 */

public class ChatView {
    private Label nameLabel;
    private Label  userIdLabel;
    private ImageView userImage;
    private TextArea chatArea;
    private VBox chatHistory;
    private ScrollPane chatPane;

    public ChatView(Integer openUserId,String openUserName,Image userIcon) {
        try {
            Stage chatStage=new Stage();
            Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/fxml/chatWindow.fxml")));
            chatStage.setScene(scene);
            chatStage.setResizable(false);
            initMember(scene);
            setMemberValue(openUserId,openUserName,userIcon);
            chatArea.textProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                    chatArea.setScrollTop(Double.MAX_VALUE);
                }
            });
            chatArea.wrapTextProperty().setValue(true);
            StageManager.registerStage(IDUtils.formatID(openUserId,IDType.CHATWINDOW),chatStage);
            chatStage.show();
            chatStage.setOnCloseRequest(event ->  {
                    ChatViewManager.unregisterStage(String.valueOf(openUserId));
            });
            ChatService chatService=SpringContextUtils.getBean("chatService");
            List<MessageInfo> messageInfos=chatService.getOneDayRecentChatLog(openUserId,UserUtils.getCurrentUserId());
            //获取近一天的聊天记录
            for (MessageInfo messageInfo:messageInfos){
                Label label;
                if(openUserId.equals(messageInfo.getSourceclientid())){
                    ChatUtils.appendBubble(chatPane,BubbleType.LEFT,messageInfo.getMsgcontent(),userIcon,570D);

                }else{
                    ChatUtils.appendBubble(chatPane,BubbleType.RIGHT,messageInfo.getMsgcontent(),UserUtils.getUserIcon(),570D);
                }
            }
            StageManager.registerStage(IDUtils.formatID(openUserId,IDType.CHATWINDOW),chatStage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setMemberValue(Integer openUserId,String openUserName,Image userIcon) {
        nameLabel.setText(openUserName);
        userIdLabel.setVisible(false);
        userIdLabel.setText(String.valueOf(openUserId));
        userImage.setImage(userIcon);
    }

    private void initMember(Scene scene) {
        nameLabel = (Label)scene.lookup("#nameLabel");
        userIdLabel= (Label)scene.lookup("#userId");
        userImage  = (ImageView)scene.lookup("#userIcon");
        chatArea=(TextArea) scene.lookup("#chatArea");
        chatHistory = (VBox)scene.lookup("#chatHistory");
        chatPane = (ScrollPane)scene.lookup("#chatPane");
    }

    public VBox getChatHistory() {
        return chatHistory;
    }

    public ScrollPane getChatPane() {
        return chatPane;
    }
}
