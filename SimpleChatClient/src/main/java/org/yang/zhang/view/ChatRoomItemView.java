package org.yang.zhang.view;


import org.yang.zhang.module.ChatRoom;
import org.yang.zhang.utils.ImageUtiles;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 11:20
 */

public class ChatRoomItemView {
    private String id;
    private Image icon;
    private ImageView imageView;
    private String roomName;
    private Label roomLabel;
    private Pane roomPane;
    public ChatRoomItemView(ChatRoom chatRoom){
        try {
            roomPane=FXMLLoader.load(getClass().getResource("/fxml/chatRoom.fxml"));
            roomLabel=(Label)roomPane.lookup("#chatRoomName");
            imageView=(ImageView)roomPane.lookup("#chatRoomIcon");
            imageView.setImage(this.icon);
            roomLabel.setText(chatRoom.getName());
            this.roomName = chatRoom.getName();
            this.icon=ImageUtiles.getUserIcon(chatRoom.getIcon());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Image getIcon() {
        return icon;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getRoomName() {
        return roomName;
    }

    public Label getRoomLabel() {
        return roomLabel;
    }

    public Pane getRoomPane() {
        return roomPane;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
