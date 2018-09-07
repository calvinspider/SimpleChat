package org.yang.zhang.service;

import org.yang.zhang.dto.AddContractDto;
import org.yang.zhang.dto.ChatRoomDto;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.dto.SearchContractDto;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.ChatRoom;
import org.yang.zhang.module.ContractGroup;
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

    List<RecentContract> oneMonthContract(FindByUserDto userDto);

    List<AddContractDto> recommondContract(FindByUserDto userId);

    List<AddContractDto> searchContractDto(SearchContractDto key);

    Result<ContractGroup> newGroup(ContractGroupDto contractGroupDto);

    Result<Void> updateContractGroup(ContractGroupDto contractGroupDto);

    Result<ContractGroupDto> updateGroup(ContractGroupDto contractGroupDto);

    void deleteGroup(ContractGroupDto contractGroupDto);

    void deleteFriend(ContractGroupDto contractGroupDto);

    void addFriend(AddContractDto addContractDto);

    List<ChatRoom> getUerChatRooms(Integer id);

    ChatRoomDto getRoomDetail(Integer id);
}
