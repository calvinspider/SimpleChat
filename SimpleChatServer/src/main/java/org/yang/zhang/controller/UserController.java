package org.yang.zhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;
import org.yang.zhang.service.UserService;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 07 16:35
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public Result<User> register(@RequestBody User user){
        return Result.successData(userService.register(user));
    }

}
