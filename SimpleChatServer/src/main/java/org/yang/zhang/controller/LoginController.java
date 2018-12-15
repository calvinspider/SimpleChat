package org.yang.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yang.zhang.dto.in.QrLoginDto;
import org.yang.zhang.dto.in.UserLoginDto;
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
    public Result<User> login(@RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }

    @RequestMapping("/findPassWord")
    public String findPassWord(String userId){
        Result<Void> result= userService.findPassWord(userId);
        return JsonUtils.toJson(result);
    }

    @RequestMapping(value = "/loginByQrCode",method = RequestMethod.POST)
    public Result<User> loginByQrCode(@RequestBody QrLoginDto qrToken){
        System.out.println("loging........");
        return userService.loginByQrCode(qrToken);
    }

    @RequestMapping(value = "/registerQrCode",method = RequestMethod.GET)
    public Result<Void> registerQrCode(QrLoginDto qrToken){
        System.out.println("register........");
        return userService.registerQrCode(qrToken);
    }

}
