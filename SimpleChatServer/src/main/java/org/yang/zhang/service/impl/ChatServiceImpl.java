package org.yang.zhang.service.impl;

import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.mapper.ChatMapper;
import org.yang.zhang.module.ContractGroup;
import org.yang.zhang.module.GroupUser;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.User;
import org.yang.zhang.repository.ContractGroupRepository;
import org.yang.zhang.repository.GroupUserRepository;
import org.yang.zhang.repository.UserRepository;
import org.yang.zhang.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 18:43
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private ContractGroupRepository contractGroupRepository;
    @Autowired
    private GroupUserRepository groupUserRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ContractGroupDto> getContractList(FindByUserDto parma) {
        List<ContractGroupDto> result=new ArrayList<>();
        List<ContractGroup> contractGroups=contractGroupRepository.findByUserId(parma.getUserId());
        contractGroups.forEach(item->{
            ContractGroupDto groupDto=new ContractGroupDto();
            List<GroupUser> groupUsers=groupUserRepository.findByGroupId(item.getId());
            List<Integer> userIds=groupUsers.stream().map(groupUser -> groupUser.getUserId()).collect(Collectors.toList());
            List<User> users=userRepository.findByIdIn(userIds);
            groupDto.setGroupId(item.getId());
            groupDto.setGroupName(item.getGroupName());
            groupDto.setUserList(users);
            result.add(groupDto);
        });
        return result;
    }

    @Override
    public List<MessageInfo> oneDayChatLog(RecentChatLogDto chatLogDto) {
        Calendar calendar=Calendar.getInstance();
        chatLogDto.setEnd(new Date());
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        chatLogDto.setStart(calendar.getTime());
        return chatMapper.oneDayChatLog(chatLogDto);
    }

    @Override
    public List<RecentContract> oneMonthContract(FindByUserDto userDto) {
        List<RecentContract> list= chatMapper.oneMonthContract(userDto.getUserId());
        return list;
    }
}
