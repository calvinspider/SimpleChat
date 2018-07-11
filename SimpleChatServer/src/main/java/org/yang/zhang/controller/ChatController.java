package org.yang.zhang.controller;

import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return Result.successData(chatService.oneMonthContract(userDto));
    }
}
