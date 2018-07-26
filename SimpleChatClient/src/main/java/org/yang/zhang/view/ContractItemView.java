package org.yang.zhang.view;

import org.yang.zhang.constants.Constant;
import org.yang.zhang.module.ContractGroup;
import org.yang.zhang.module.User;
import org.yang.zhang.utils.ImageUtiles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private ImageView userIconView;
    private Timeline timeline=new Timeline();
    private Label nameLabel;
    private Label personLabel;
    private String nickName;

    public ContractItemView(String text){
        try {
            itemPane=FXMLLoader.load(getClass().getResource("/fxml/groupItem.fxml"));
            groupName=(TextField) itemPane.lookup("#groupName");
            groupName.setEditable(false);
            groupName.setText(text);
            groupName.setStyle("-fx-background-color: #A1BBD8");
            groupName.setCursor(Cursor.DEFAULT);
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
            nameLabel=username;
            personLabel=personword;
            username.setStyle("-fx-font-size:13");
            personword.setStyle("-fx-font-size:13");
            username.setText(user.getNickName());
            personword.setText(user.getPersonWord());
            this.user=user;
            this.nickName=user.getNickName();
            this.userIconView=usericon;
            timeline.setCycleCount( Animation.INDEFINITE ) ;
            this.blink=false;
            EventHandler<ActionEvent> on_finished = (ActionEvent event ) ->
            {
                if (!this.blink)
                {
                    this.userIconView.setFitWidth(30);
                    this.userIconView.setFitHeight(30);
                    this.blink=true;
                }else {
                    this.userIconView.setFitWidth(20);
                    this.userIconView.setFitHeight(20);
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
        this.userIconView.setFitHeight(25);
        this.userIconView.setFitWidth(25);
        timeline.stop();
    }

    public void setNoFocus() {
        this.getItemPane().setPrefHeight(30);
        this.userIconView.setFitHeight(25);
        this.userIconView.setFitWidth(25);
        this.userIconView.setLayoutX(this.userIconView.getLayoutX()-5);
        this.userIconView.setLayoutY(this.userIconView.getLayoutY()-5);
        this.nameLabel.setLayoutY(this.nameLabel.getLayoutY()-10);
        this.personLabel.setLayoutY(this.personLabel.getLayoutY()-10);
        this.nameLabel.setStyle("-fx-font-size:13");
        this.personLabel.setStyle("-fx-font-size:13");
        this.getItemPane().setStyle("-fx-background-color: #A1BBD8");
    }

    public void setFocus() {
        this.getItemPane().setPrefHeight(50);
        this.userIconView.setFitHeight(40);
        this.userIconView.setFitWidth(40);
        this.userIconView.setLayoutX(this.userIconView.getLayoutX()+5);
        this.userIconView.setLayoutY(this.userIconView.getLayoutY()+5);
        this.nameLabel.setLayoutY(this.nameLabel.getLayoutY()+10);
        this.personLabel.setLayoutY(this.personLabel.getLayoutY()+10);
        this.nameLabel.setStyle("-fx-font-size:15");
        this.personLabel.setStyle("-fx-font-size:15");
        this.nameLabel.setTextFill(Color.web("#000000"));
        this.personLabel.setTextFill(Color.web("#000000"));
        this.getItemPane().setStyle("-fx-background-color: #D3D6C1");
    }

    public void setGroupEditable(){

        this.groupName.setEditable(true);
        this.groupName.setStyle("-fx-background-color: #FFFFFF");
        this.groupName.setFocusTraversable(true);
    }

    public void setGroupEditDisable(){
        this.groupName.setEditable(false);
        this.groupName.setStyle("-fx-background-color: #A1BBD8");
    }

    public void setGroupFocus() {
        this.getItemPane().setStyle("-fx-background-color: #D3D6C1");
        this.groupName.setStyle("-fx-background-color: #D3D6C1");
    }

    public void setGroupNoFocus() {
        this.getItemPane().setStyle("-fx-background-color: #A1BBD8");
        this.groupName.setStyle("-fx-background-color: #A1BBD8");
    }

    public String getNickName() {
        return nickName;
    }
}
