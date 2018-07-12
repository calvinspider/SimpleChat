package org.yang.zhang.view;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.utils.ChatViewManager;
import org.yang.zhang.utils.DateUtils;
import org.yang.zhang.utils.StageManager;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
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
    private String openUserId;
    private String mainUserId;

    public ChatView(String openUserId,String mainUserId,List<MessageInfo> messageInfos) {
        try {

            //创建聊天框
            Stage chatStage=new Stage();
            Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/fxml/chatWindow.fxml")));
            chatStage.setScene(scene);
            //聊天框大小不可修改
            chatStage.setResizable(false);
            //目标联系人
            Label nameLabel1 = (Label)scene.lookup("#nameLabel");
            nameLabel1.setText(openUserId);
            //当前登陆用户
            Label sourceNameLabel = (Label)scene.lookup("#sourceNameLabel");
            sourceNameLabel.setText(mainUserId);
            sourceNameLabel.setVisible(false);
            chatStage.show();

            chatStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    ChatViewManager.unregisterStage(openUserId);
                }
            });
            String sourceId=sourceNameLabel.getText();
            //聊天记录框
            VBox chatHistory = (VBox)scene.lookup("#chatHistory");
            //TODO 滚动条置底 没反应
            ScrollPane chatPane = (ScrollPane)scene.lookup("#chatPane");
            chatPane.setVvalue(chatPane.getMaxHeight());
            //获取近一天的聊天记录

            for (MessageInfo messageInfo:messageInfos){
                ImageView imageView=new ImageView("images/personIcon.jpg");
                imageView.setFitWidth(25);
                imageView.setFitHeight(25);
                Label label=new Label(messageInfo.getMsgcontent(),imageView);
                if(openUserId.equals(messageInfo.getSourceclientid())){
                    label.setAlignment(Pos.CENTER_LEFT);
                }else{
                    label.setAlignment(Pos.CENTER_RIGHT);
                }
                label.setPrefWidth(570);
                label.setStyle("-fx-padding: 5 5 5 5");
                Label time=new Label(DateUtils.formatDateTime(messageInfo.getTime()));
                time.setPrefWidth(570);
                time.setAlignment(Pos.CENTER);
                chatHistory.getChildren().add(time);
                chatHistory.getChildren().add(label);
            }
            //set value
            this.chatStage=chatStage;
            this.mainUserId=mainUserId;
            this.openUserId=openUserId;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Stage getChatStage() {
        return chatStage;
    }

    public VBox getChatBox(){
        VBox chatHistory = (VBox)this.chatStage.getScene().lookup("#chatHistory");
        return chatHistory;
    }

    public void setChatStage(Stage chatStage) {
        this.chatStage = chatStage;
    }

    public String getOpenUserId() {
        return openUserId;
    }

    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    public String getMainUserId() {
        return mainUserId;
    }

    public void setMainUserId(String mainUserId) {
        this.mainUserId = mainUserId;
    }

    public void show() {
        this.chatStage.show();
    }
}
