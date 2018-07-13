package org.yang.zhang.service.impl;

import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;
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
    private UserRepository userRepository;

    @Override
    public Result<User> login(String userName, String passWord) {
        try {
            User user=userRepository.getOne(Integer.valueOf(userName));
            User newUser=new User();
            BeanUtils.copyProperties(user,newUser);
            if(newUser.getPassword().equals(passWord)){
                return Result.successData(newUser);
            }
        }catch (Exception e){
            return Result.errorMessage("用户登陆失败");
        }

        return Result.errorMessage("用户登陆失败");
    }

    @Override
    public User register(User user) {
        return userRepository.save(user);
    }
}
