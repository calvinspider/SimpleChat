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
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 11:16
 */

public class ChatView {

    private Integer id;
    private Scene scene;
    private Stage chatStage;
    private Image icon;
    private Label nameLabel;
    private Label  userIdLabel;
    private ImageView userImage;
    private TextArea chatArea;
    private VBox chatHistory;
    private ScrollPane chatPane;
    private Label personWordLabel;
    private Pane root;
    private static Double DEFAULTDUBBLEWIDTH=670D;
    private double xOffset = 0;
    private double yOffset = 0;
    public ChatView(Integer openUserId,String openUserName,String personword,Image userIcon) {
        try {
            scene=new Scene(FXMLLoader.load(getClass().getResource("/fxml/chatWindow.fxml")));
        }catch (Exception e){
            e.printStackTrace();
        }
        initMember(scene);
        setMemberValue(openUserId,openUserName,personword,userIcon);
        initStage(scene);
        initChatHistory();
        initEvent();
        show();
    }

    private void initEvent() {
        chatArea.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                chatArea.setScrollTop(Double.MAX_VALUE);
            }
        });

        chatStage.setOnCloseRequest(event ->  {
            ChatViewManager.unregisterStage(IDUtils.formatID(id,IDType.CHATWINDOW));
        });

        root.setOnMousePressed(event ->  {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            StageManager.getStage(IDUtils.formatID(id,IDType.CHATWINDOW)).setX(event.getScreenX() - xOffset);
            StageManager.getStage(IDUtils.formatID(id,IDType.CHATWINDOW)).setY(event.getScreenY() - yOffset);
        });
    }

    private void initChatHistory() {
        ChatService chatService=SpringContextUtils.getBean("chatService");
        List<MessageInfo> messageInfos=chatService.getOneDayRecentChatLog(id,UserUtils.getCurrentUserId());
        //获取近一天的聊天记录
        for (MessageInfo messageInfo:messageInfos){
            if(id.equals(messageInfo.getSourceclientid())){
                ChatUtils.appendBubble(chatPane,BubbleType.LEFT,messageInfo.getMsgcontent(),icon,DEFAULTDUBBLEWIDTH);

            }else{
                ChatUtils.appendBubble(chatPane,BubbleType.RIGHT,messageInfo.getMsgcontent(),UserUtils.getUserIcon(),DEFAULTDUBBLEWIDTH);
            }
        }
    }

    private void show() {
        chatStage.show();
    }

    private void initStage(Scene scene) {
        chatStage=new Stage();
        chatStage.setScene(scene);
        chatStage.setResizable(false);
        chatStage.initStyle(StageStyle.UNDECORATED);
        chatStage.getIcons().add(icon);
        StageManager.registerStage(IDUtils.formatID(id,IDType.CHATWINDOW),chatStage);
    }

    private void setMemberValue(Integer openUserId,String openUserName,String personWord,Image userIcon) {
        nameLabel.setText(openUserName);
        userIdLabel.setVisible(false);
        userIdLabel.setText(String.valueOf(openUserId));
        userImage.setImage(userIcon);
        personWordLabel.setText(personWord);
        this.id=openUserId;
        this.icon=userIcon;
        chatArea.wrapTextProperty().setValue(true);
    }

    private void initMember(Scene scene) {
        nameLabel = (Label)scene.lookup("#nameLabel");
        userIdLabel= (Label)scene.lookup("#userId");
        userImage  = (ImageView)scene.lookup("#userIcon");
        chatArea=(TextArea) scene.lookup("#chatArea");
        chatPane = (ScrollPane)scene.lookup("#chatPane");
        VBox vBox=new VBox();
        vBox.setBackground(Background.EMPTY);
        vBox.setStyle("-fx-background-color: transparent");

        chatPane.setContent(vBox);
        root=(Pane)scene.lookup("#root");
        personWordLabel=(Label)scene.lookup("#personWordLabel");
    }

    public ScrollPane getChatPane() {
        return chatPane;
    }

    public Integer getId() {
        return id;
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getChatStage() {
        return chatStage;
    }

    public Image getIcon() {
        return icon;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public Label getUserIdLabel() {
        return userIdLabel;
    }

    public ImageView getUserImage() {
        return userImage;
    }

    public TextArea getChatArea() {
        return chatArea;
    }
}
