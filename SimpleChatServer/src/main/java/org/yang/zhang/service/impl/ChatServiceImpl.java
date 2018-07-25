package org.yang.zhang.service.impl;

import org.springframework.web.bind.annotation.RequestBody;
import org.yang.zhang.dto.AddContractDto;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.dto.SearchContractDto;
import org.yang.zhang.entity.Result;
import org.yang.zhang.mapper.ChatMapper;
import org.yang.zhang.mapper.ChatRoomMapper;
import org.yang.zhang.mapper.GroupUserMapper;
import org.yang.zhang.module.ChatRoom;
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
import java.util.Optional;
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
    private GroupUserMapper groupUserMapper;
    @Autowired
    private ContractGroupRepository contractGroupRepository;
    @Autowired
    private GroupUserRepository groupUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomMapper chatRoomMapper;

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

    @Override
    public List<AddContractDto> recommondContract(FindByUserDto userId) {
        List<AddContractDto> list= chatMapper.recommondContract(userId.getUserId());
        return list;
    }

    @Override
    public List<AddContractDto> searchContractDto(SearchContractDto key) {
        List<AddContractDto> list= chatMapper.searchContractDto(key);
        return list;
    }

    @Override
    public Result<ContractGroup> newGroup(ContractGroupDto contractGroupDto) {
        ContractGroup contractGroup=new ContractGroup();
        contractGroup.setGroupName(contractGroupDto.getGroupName());
        contractGroup.setUserId(contractGroupDto.getUserId());
        contractGroupRepository.save(contractGroup);
        return Result.successData(contractGroup);
    }

    @Override
    public Result<Void> updateContractGroup(ContractGroupDto contractGroupDto) {
        GroupUser groupUser=new GroupUser();
        groupUser.setUserId(contractGroupDto.getUserId());
        groupUser.setGroupId(Integer.valueOf(contractGroupDto.getNewGroupId()));
        groupUserMapper.deleteByUserIdAndGroupId(contractGroupDto.getUserId(),Integer.valueOf(contractGroupDto.getOldGroupId()));
        groupUserRepository.save(groupUser);
        return Result.success();
    }

    @Override
    public void updateGroup(ContractGroupDto contractGroupDto) {
        ContractGroup old=contractGroupRepository.getOne(contractGroupDto.getGroupId());
        old.setGroupName(contractGroupDto.getGroupName());
        contractGroupRepository.save(old);
    }

    @Override
    public void deleteGroup(ContractGroupDto contractGroupDto) {
        Integer groupId=contractGroupDto.getGroupId();
        Integer userId=contractGroupDto.getUserId();
        contractGroupRepository.deleteById(groupId);
        List<ContractGroup> list=contractGroupRepository.findByUserId(userId);
        if(list.size()>0){
            ContractGroup group=list.get(0);
            List<GroupUser> groupUsers=groupUserRepository.findByGroupId(groupId);
            for (GroupUser groupUser:groupUsers){
                groupUser.setGroupId(group.getId());
                groupUserRepository.save(groupUser);
            }
        }
    }

    @Override
    public void deleteFriend(ContractGroupDto contractGroupDto) {
        Integer groupId=contractGroupDto.getGroupId();
        Integer userId=contractGroupDto.getUserId();
        groupUserMapper.deleteByUserIdAndGroupId(userId,groupId);
    }

    @Override
    public void addFriend(AddContractDto addContractDto) {
        List<ContractGroup> list=contractGroupRepository.findByUserId(addContractDto.getCurrentUserId());
        if(list.size()>0){
            ContractGroup group=list.get(0);
            GroupUser groupUser=new GroupUser();
            groupUser.setUserId(addContractDto.getFriendId());
            groupUser.setGroupId(group.getId());
            groupUserRepository.save(groupUser);
        }
    }

    @Override
    public List<ChatRoom> getUerChatRooms(@RequestBody  Integer id) {
        List<ChatRoom> list= chatRoomMapper.getUerChatRooms(id);
        return list;
    }
}
