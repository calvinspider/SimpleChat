package org.yang.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yang.zhang.dto.LoginDto;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;
import org.yang.zhang.service.UserService;
import org.yang.zhang.utils.JsonUtils;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 07 16:28
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        Result<User> result= userService.login(loginDto.getUserName(),loginDto.getPassWord(),loginDto.getStatus());
        System.out.println(JsonUtils.toJson(result));
        return JsonUtils.toJson(result);
    }

    @RequestMapping("/findPassWord")
    public String findPassWord(String userId){
        Result<Void> result= userService.findPassWord(userId);
        return JsonUtils.toJson(result);
    }
}
