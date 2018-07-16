package org.yang.zhang.service;

import java.util.List;

import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.module.MessageInfo;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 11 12:09
 */

public interface ChatService {
    List<MessageInfo> getOneDayRecentChatLog(String id1, String id2);

    List<RecentContract> getrecentContract(Integer id);

    void createNewGroup(String text);
}
