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
}
