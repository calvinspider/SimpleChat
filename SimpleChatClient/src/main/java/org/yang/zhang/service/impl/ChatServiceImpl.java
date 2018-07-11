package org.yang.zhang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.utils.JsonUtils;

import com.fasterxml.jackson.core.type.TypeReference;


/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 11 12:09
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<MessageInfo> getOneDayRecentChatLog(String id1, String id2) {
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
}
