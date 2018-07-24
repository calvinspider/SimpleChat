package org.yang.zhang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.dto.AddContractDto;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.ContractGroup;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.utils.UserUtils;
import org.yang.zhang.utils.JsonUtils;

import com.fasterxml.jackson.core.type.TypeReference;


/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 11 12:09
 */
@Service("chatService")
public class ChatServiceImpl implements ChatService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<MessageInfo> getOneDayRecentChatLog(Integer id1, Integer id2) {
        TypeReference type=new TypeReference<Result<List<MessageInfo>>>(){};
        RecentChatLogDto chatLogDto=new RecentChatLogDto();
        chatLogDto.setId1(id1);
        chatLogDto.setId2(id2);
        String result=restTemplate.postForObject(Constant.ONEDAYCHATLOGURL,chatLogDto,String.class);
        Result<List<MessageInfo>> listResult=JsonUtils.fromJson(result,type);
        return listResult.getData();
    }

    @Override
    public List<RecentContract> getrecentContract(Integer id) {
        FindByUserDto findByUserDto=new FindByUserDto();
        findByUserDto.setUserId(id);
        TypeReference type=new TypeReference<Result<List<RecentContract>>>(){};
        String result=restTemplate.postForObject(Constant.ONEMONTHCONTRACT,findByUserDto,String.class);
        Result<List<RecentContract>> listResult=JsonUtils.fromJson(result,type);
        return listResult.getData();
    }

    @Override
    public ContractGroup createNewGroup(String text) {
        TypeReference typeReference=new TypeReference<Result<ContractGroup>>() {};
        ContractGroupDto contractGroupDto=new ContractGroupDto();
        contractGroupDto.setGroupName(text);
        contractGroupDto.setUserId(UserUtils.getCurrentUserId());
        String result=restTemplate.postForObject(Constant.NEWGROUP,contractGroupDto,String.class);
        Result<ContractGroup> result1=JsonUtils.fromJson(result,typeReference);
        return result1.getData();
    }

    @Override
    public void updateContractGroup(String itemId, String oldGroupId, String newGroupId) {
        ContractGroupDto contractGroupDto=new ContractGroupDto();
        contractGroupDto.setUserId(Integer.valueOf(itemId));
        contractGroupDto.setOldGroupId(oldGroupId);
        contractGroupDto.setNewGroupId(newGroupId);
        restTemplate.postForObject(Constant.UPDATECONTRACTGROUP,contractGroupDto,String.class);
    }

    @Override
    public void updateGroup(String id, String text) {
        ContractGroupDto contractGroupDto=new ContractGroupDto();
        contractGroupDto.setGroupName(text);
        contractGroupDto.setGroupId(Integer.valueOf(id.substring(id.indexOf("P")+1,id.length())));
        restTemplate.postForObject(Constant.UPDATEGROUP,contractGroupDto,String.class);
    }

    @Override
    public void deleteGroup(Integer currentUserId, Integer groupId) {
        ContractGroupDto contractGroupDto=new ContractGroupDto();
        contractGroupDto.setUserId(currentUserId);
        contractGroupDto.setGroupId(groupId);
        restTemplate.postForObject(Constant.DELETEGROUP,contractGroupDto,String.class);
    }

    @Override
    public void deleteFriend(Integer groupId, Integer friendId) {
        ContractGroupDto contractGroupDto=new ContractGroupDto();
        contractGroupDto.setUserId(friendId);
        contractGroupDto.setGroupId(groupId);
        restTemplate.postForObject(Constant.DELETEFRIEND,contractGroupDto,String.class);
    }

    @Override
    public void addFriend(Integer userid, Integer friendId) {
        AddContractDto addContractDto=new AddContractDto();
        addContractDto.setFriendId(friendId);
        addContractDto.setCurrentUserId(userid);
        restTemplate.postForObject(Constant.ADDFRIEND,addContractDto,String.class);
    }
}
