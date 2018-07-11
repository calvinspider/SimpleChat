package org.yang.zhang.service;

import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.module.MessageInfo;

import java.util.List;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 18:43
 */

public interface ChatService {

    List<ContractGroupDto> getContractList(FindByUserDto userId);

    List<MessageInfo> oneDayChatLog(RecentChatLogDto chatLogDto);
}
