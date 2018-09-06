package org.yang.zhang.view;

import org.yang.zhang.constants.Constant;
import org.yang.zhang.module.ContractGroup;
import org.yang.zhang.module.User;
import org.yang.zhang.utils.ImageUtiles;
import org.yang.zhang.utils.TextUtils;

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
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private Label groupName;
    private User user;
    private Boolean blink;
    private ImageView userIconView;
    private Timeline timeline=new Timeline();
    private Label nameLabel;
    private Label personwordLabel;
    private String nickName;

    public ContractItemView(String text){
        try {
            itemPane=FXMLLoader.load(getClass().getResource("/fxml/groupItem.fxml"));
            groupName=(Label) itemPane.lookup("#groupName");
            groupName.setText(text);
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
            personwordLabel = (Label) itemPane.lookup("#personWord");
            username.setText(user.getNickName());
            personwordLabel.setText(user.getPersonWord());
            username.setPrefWidth(TextUtils.computeTextWidth(username.getFont(),
                    username.getText(), 0.0D) + 10);
            personwordLabel.setLayoutX(username.getLayoutX()+username.getPrefWidth());
            this.nameLabel=username;
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

    public Label getGroupName() {
        return groupName;
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
        this.nameLabel.setLayoutX(nameLabel.getLayoutX()-20);
        this.nameLabel.setLayoutY(nameLabel.getLayoutY()-10);
        this.personwordLabel.setLayoutX(personwordLabel.getLayoutX()-20);
        this.personwordLabel.setLayoutY(personwordLabel.getLayoutY()-10);
    }

    public void setFocus() {
        this.getItemPane().setPrefHeight(50);
        this.userIconView.setFitHeight(40);
        this.userIconView.setFitWidth(40);
        this.userIconView.setLayoutX(this.userIconView.getLayoutX()+5);
        this.userIconView.setLayoutY(this.userIconView.getLayoutY()+5);
        this.nameLabel.setLayoutX(nameLabel.getLayoutX()+20);
        this.nameLabel.setLayoutY(nameLabel.getLayoutY()+10);
        this.personwordLabel.setLayoutX(personwordLabel.getLayoutX()+20);
        this.personwordLabel.setLayoutY(personwordLabel.getLayoutY()+10);
        this.nameLabel.setStyle("-fx-text-fill: black");
    }

    public void setGroupEditable(){

    }

    public void setGroupEditDisable(){

    }

    public void setGroupFocus() {

    }

    public void setGroupNoFocus() {

    }

    public String getNickName() {
        return nickName;
    }
}
