package org.yang.zhang.controller;

import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.entity.Contract;
import org.yang.zhang.entity.User;
import org.yang.zhang.service.ChatService;
import org.yang.zhang.entity.Result;
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
    public List<Contract> getContractList(@RequestBody  FindByUserDto userId)
    {
        return chatService.getContractList(userId);
    }
}
