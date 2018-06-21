package org.yang.zhang.controller;

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
    public List<Contract> getContractList(String userId)
    {
        List<Contract> list=new ArrayList<>();
        Contract contract=new Contract();
        contract.setUserId("10001");
        contract.setUserName("USER1");
        list.add(contract);
        return list;
    }

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
    public Result getGroupList(){
        //TODO
        return null;
    }

    @RequestMapping("/recentChat")
    public Result recentChat(){
        //TODO
        return null;
    }

    @RequestMapping("/chatHistory")
    public Result chatHistory(Integer userId){
        //TODO
        return null;
    }



}
