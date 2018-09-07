package org.yang.zhang.service;

import java.util.List;

import org.yang.zhang.dto.ChatRoomDto;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.module.ChatRoom;
import org.yang.zhang.module.ContractGroup;
import org.yang.zhang.module.GroupUser;
import org.yang.zhang.module.MessageInfo;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 11 12:09
 */

public interface ChatService {
    List<MessageInfo> getOneDayRecentChatLog(Integer id1, Integer id2);

    List<RecentContract> getrecentContract(Integer id);

    ContractGroup createNewGroup(String text);

    void updateContractGroup(String itemId, String oldGroupId, String newGroupId);

    ContractGroupDto updateGroup(String id, String text);

    void deleteGroup(Integer currentUserId, Integer integer);

    void deleteFriend(Integer currentUserId, Integer integer);

    void addFriend(Integer userid, Integer friendId);

    List<ChatRoom> getUserChatRoom(Integer id);

    ChatRoomDto getRoomDetail(Integer integer);
}
