package org.yang.zhang.view;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.yang.zhang.fxcontroller.MainController;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.utils.AnimationUtils;
import org.yang.zhang.utils.ChatViewManager;
import org.yang.zhang.utils.DateUtils;
import org.yang.zhang.utils.StageManager;
import org.yang.zhang.utils.UserUtils;

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
    private Stage chatStage;
    private Integer userId;
    private Image userIcon;
    public ChatView(Integer openUserId,Image userIcon,List<MessageInfo> messageInfos) {
        try {
            //创建聊天框
            Stage chatStage=new Stage();
            Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/fxml/chatWindow.fxml")));
            chatStage.setScene(scene);
            //聊天框大小不可修改
            chatStage.setResizable(false);
            //目标联系人
            Label nameLabel1 = (Label)scene.lookup("#nameLabel");
            nameLabel1.setText(UserUtils.getUser(openUserId).getNickName());
            Label  userIdLabel= (Label)scene.lookup("#userId");
            userIdLabel.setVisible(false);
            userIdLabel.setText(String.valueOf(openUserId));
            ImageView userImage  = (ImageView)scene.lookup("#userIcon");
            userImage.setImage(userIcon);
            TextArea chatArea=(TextArea) scene.lookup("#chatArea");
            chatArea.textProperty().addListener(new ChangeListener<Object>() {
                @Override
                public void changed(ObservableValue<?> observable, Object oldValue,
                                    Object newValue) {
                    chatArea.setScrollTop(Double.MAX_VALUE);
                }
            });
            chatArea.wrapTextProperty().setValue(true);
            chatStage.show();

            chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    ChatViewManager.unregisterStage(String.valueOf(openUserId));
                }
            });

            //聊天记录框
            VBox chatHistory = (VBox)scene.lookup("#chatHistory");
            ScrollPane chatPane = (ScrollPane)scene.lookup("#chatPane");
            //获取近一天的聊天记录
            for (MessageInfo messageInfo:messageInfos){
                Label label;
                if(openUserId.equals(messageInfo.getSourceclientid())){
                    LeftMessageBubble leftMessageBubble=new LeftMessageBubble(messageInfo.getMsgcontent(),userIcon);
                    Label time=new Label(DateUtils.formatDateTime(messageInfo.getTime()));
                    time.setPrefWidth(570);
                    time.setAlignment(Pos.CENTER);
                    time.setPrefHeight(200);
                    time.setStyle("-fx-padding: 10,10,10,10");
                    chatHistory.getChildren().add(time);
                    chatHistory.getChildren().add(leftMessageBubble.getPane());

                }else{
                    RightMessageBubble rightMessageBubble=new RightMessageBubble(messageInfo.getMsgcontent(),UserUtils.getUserIcon());
                    Label time=new Label(DateUtils.formatDateTime(messageInfo.getTime()));
                    time.setPrefWidth(570);
                    time.setAlignment(Pos.CENTER);
                    time.setPrefHeight(200);
                    time.setStyle("-fx-padding: 10,10,10,10");
                    chatHistory.getChildren().add(time);
                    chatHistory.getChildren().add(rightMessageBubble.getPane());
                }
            }
            this.chatStage=chatStage;
            this.userId=openUserId;
            this.userIcon=userIcon;
            Platform.runLater(()->{
                chatPane.setVvalue(1.0);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public VBox getChatBox(){
        VBox chatHistory = (VBox)this.chatStage.getScene().lookup("#chatHistory");
        return chatHistory;
    }

    public ScrollPane getChatPane(){
        ScrollPane chatHistory = (ScrollPane)this.chatStage.getScene().lookup("#chatPane");
        return chatHistory;
    }

    public Stage getChatStage() {
        return chatStage;
    }

    public Integer getUserId() {
        return userId;
    }

    public Image getUserIcon() {
        return userIcon;
    }
}
