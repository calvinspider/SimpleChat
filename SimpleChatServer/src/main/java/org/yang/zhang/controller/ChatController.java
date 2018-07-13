package org.yang.zhang.controller;

import org.yang.zhang.dto.AddContractDto;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.dto.SearchContractDto;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 18:41
 */
@RestController
@RequestMapping(value = "/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @RequestMapping(value = "/contract")
    public List<ContractGroupDto> getContractList(@RequestBody FindByUserDto userId)
    {
        return chatService.getContractList(userId);
    }

    @RequestMapping(value = "/oneDayChatLog")
    public Result<List<MessageInfo>> oneDayChatLog(@RequestBody RecentChatLogDto chatLogDto){
        return Result.successData(chatService.oneDayChatLog(chatLogDto));
    }

    @RequestMapping(value = "/oneMonthContract")
    public Result<List<RecentContract>> oneMonthContract(@RequestBody FindByUserDto userDto){
        List<RecentContract> list=chatService.oneMonthContract(userDto);
        return Result.successData(list);
    }

    @RequestMapping(value = "/recommondContract")
    public Result<List<AddContractDto>> recommondContract(@RequestBody FindByUserDto userId){
        List<AddContractDto> list=chatService.recommondContract(userId);
        return Result.successData(list);
    }

    @RequestMapping(value = "/searchContract")
    public Result<List<AddContractDto>> searchContractDto(@RequestBody SearchContractDto searchContractDto){
        List<AddContractDto> list=chatService.searchContractDto(searchContractDto);
        return Result.successData(list);
    }
}
