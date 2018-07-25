package org.yang.zhang.dto;

import java.io.Serializable;
import java.util.List;

import org.yang.zhang.module.ChatRoom;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.RoomChatInfo;
import org.yang.zhang.module.User;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 11:52
 */
@Data
public class ChatRoomDto implements Serializable {
    private Integer id;
    private ChatRoom chatRoom;
    private List<User> users;
    private List<String> publicMsg;
    private List<RoomChatInfo> recentMessage;

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

    public List<RoomChatInfo> getRecentMessage() {
        return recentMessage;
    }

    public void setRecentMessage(List<RoomChatInfo> recentMessage) {
        this.recentMessage = recentMessage;
    }
}
