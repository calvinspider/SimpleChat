package org.yang.zhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 04 28 13:45
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }

    @RequestMapping("/home")
    public String home(){
        return "index";}
}
