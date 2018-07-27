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
    private String roomName;
    private Image icon;
    private Pane roomPane;
    private Label chatRoomName;
    private ImageView chatRoomIcon;
    public ChatRoomItemView(ChatRoom chatRoom){
        try {
            roomPane=FXMLLoader.load(getClass().getResource("/fxml/chatRoom.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        initMember();
        initMemberValue(chatRoom);
    }

    private void initMemberValue(ChatRoom chatRoom) {
        this.icon=ImageUtiles.getUserIcon(chatRoom.getIcon());
        this.roomName = chatRoom.getName();
        this.id=String.valueOf(chatRoom.getId());
        this.chatRoomIcon.setImage(this.icon);
        this.chatRoomName.setText(chatRoom.getName());
    }

    private void initMember() {
        chatRoomName=(Label)roomPane.lookup("#chatRoomName");
        chatRoomIcon=(ImageView)roomPane.lookup("#chatRoomIcon");
    }

    public String getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }

    public Image getIcon() {
        return icon;
    }

    public Pane getRoomPane() {
        return roomPane;
    }

    public Label getChatRoomName() {
        return chatRoomName;
    }

    public ImageView getChatRoomIcon() {
        return chatRoomIcon;
    }
}
