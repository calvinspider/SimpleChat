package org.yang.zhang.view;

import org.yang.zhang.constants.Constant;
import org.yang.zhang.module.User;
import org.yang.zhang.utils.ImageUtiles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 12:00
 */

public class ContractItemView {
    private String id;
    private Pane itemPane;
    private Image userImage;
    private TextField groupName;
    private User user;
    private Boolean blink;
    private Timeline timeline=new Timeline();

    public ContractItemView(String text){
        try {
            itemPane=FXMLLoader.load(getClass().getResource("/fxml/groupItem.fxml"));
            groupName=(TextField) itemPane.lookup("#groupName");
            groupName.setEditable(false);
            groupName.setText(text);
            groupName.setStyle("-fx-background-color: #A1BBD8;");
            itemPane.setStyle("-fx-background-color: #A1BBD8;");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public ContractItemView(User user) {
        try {
            itemPane=FXMLLoader.load(getClass().getResource("/fxml/contractItem.fxml"));
            ImageView usericon = (ImageView)itemPane.lookup("#usericon");
            userImage=ImageUtiles.getHttpImage(Constant.serverHost+"/static/images/userIcon/"+user.getIconUrl());
            usericon.setImage(userImage);
            Label username = (Label) itemPane.lookup("#username");
            Label personword = (Label) itemPane.lookup("#personword");
            username.setText(user.getNickName());
            personword.setText(user.getPersonWord());
            this.user=user;
            timeline.setCycleCount( Animation.INDEFINITE ) ;
            this.blink=false;
            EventHandler<ActionEvent> on_finished = (ActionEvent event ) ->
            {
                if (!this.blink)
                {
                    this.itemPane.setPrefHeight(32);
                    this.blink=true;
                }else {
                    this.itemPane.setPrefHeight(28);
                    this.blink=false;
                }
            } ;
            KeyFrame keyframe = new KeyFrame( Duration.millis( 500 ), on_finished ) ;
            timeline.getKeyFrames().add(keyframe);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pane getItemPane() {
        return itemPane;
    }

    public void setItemPane(Pane itemPane) {
        this.itemPane = itemPane;
    }

    public Image getUserImage() {
        return userImage;
    }

    public void setUserImage(Image userImage) {
        this.userImage = userImage;
    }

    public TextField getGroupName() {
        return groupName;
    }

    public void setGroupName(TextField groupName) {
        this.groupName = groupName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getBlink() {
        return blink;
    }

    public void setBlink(Boolean blink) {
        this.blink = blink;
    }

    public void startBlink(){
        timeline.play();
    }

    public void stopBlink(){
        timeline.stop();
    }
}
