package org.yang.zhang.controller;

import org.yang.zhang.dto.GroupDto;
import org.yang.zhang.dto.RecentChatDto;
import org.yang.zhang.module.User;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/getfriendlist")
    public Result getfriendlist(@RequestBody User user)
    {
        return Result.successData(chatService.getFriendList(user.getId()));
    }

    @RequestMapping("/getFriendGroup")
    public Result getFriendGroup(Integer userId){
        //TODO
        return null;
    }

    @RequestMapping("/getGroupList")
    public Result getGroupList(@RequestBody GroupDto groupDto){
        //TODO
        return null;
    }

    @RequestMapping("/recentChat")
    public Result recentChat(@RequestBody RecentChatDto recentChatDto){
        //TODO
        return null;
    }

    @RequestMapping("/chatHistory")
    public Result chatHistory(Integer userId){
        //TODO
        return null;
    }



}
