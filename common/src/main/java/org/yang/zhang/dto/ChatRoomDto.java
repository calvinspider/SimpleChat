package org.yang.zhang.dto;

import java.io.Serializable;
import java.util.List;

import org.yang.zhang.module.ChatRoom;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.RoomChatInfo;
import org.yang.zhang.module.User;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 11:52
 */
public class ChatRoomDto implements Serializable {
    private Integer id;
    private ChatRoom chatRoom;
    private List<User> users;
    private List<String> publicMsg;
    private List<RoomChatInfoDto> recentMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<String> getPublicMsg() {
        return publicMsg;
    }

    public void setPublicMsg(List<String> publicMsg) {
        this.publicMsg = publicMsg;
    }

    public List<RoomChatInfoDto> getRecentMessage() {
        return recentMessage;
    }

    public void setRecentMessage(List<RoomChatInfoDto> recentMessage) {
        this.recentMessage = recentMessage;
    }
}
