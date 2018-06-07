package org.yang.zhang.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yang.zhang.dto.LoginDto;
import org.yang.zhang.utils.Result;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 07 16:28
 */
@RestController
public class LoginController {

    @RequestMapping("/login")
    public Result login(@RequestBody LoginDto loginDto) {
        //TODO
        return null;
    }
}
