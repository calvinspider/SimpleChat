package org.yang.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yang.zhang.entity.Result;
import org.yang.zhang.entity.User;
import org.yang.zhang.repository.UserRepository;
import org.yang.zhang.service.UserService;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 13 17:29
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userMapper;

    @Override
    public Result login(String userName, String passWord) {
        User user=userMapper.findByName(userName);
        if(user==null){
            return Result.errorMessage("未找到该用户",null);
        }
        if(user.getPassword().equals(passWord)){
            return Result.successData(user.getId());
        }
        return Result.errorMessage("用户登陆失败",user.getId());
    }
}
