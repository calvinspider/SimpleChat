package org.yang.zhang.service.impl;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yang.zhang.dto.in.UserLoginDto;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;
import org.yang.zhang.repository.UserRepository;
import org.yang.zhang.service.UserService;
import org.yang.zhang.utils.MailUtils;
import org.yang.zhang.utils.Md5SaltTool;

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
    public Result<User> login(UserLoginDto userLoginDto) {
        try {
            Optional<User> optionalUser=userRepository.findById(userLoginDto.getUserId());
            if(optionalUser.isPresent()){
                User user=optionalUser.get();
                if(Md5SaltTool.validPassword(userLoginDto.getPassWord(),user.getPassword())){
                    //设置用户登录状态
                    user.setStatus(userLoginDto.getStatus());
                    userRepository.saveAndFlush(user);
                    return Result.successData(user);
                }
            }
        }catch (Exception e){
            return Result.errorMessage("用户登陆失败");
        }
        return Result.errorMessage("用户登陆失败");
    }

    @Override
    public User register(User user) {
        user.setPassword(Md5SaltTool.getEncryptedPwd(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Result<Void> findPassWord(String userId) {
        Optional<User> user=userRepository.findById(Integer.valueOf(userId));
        if(!user.isPresent()){
            return Result.errorMessage("该用户不存在!");
        }
        String pwd=user.get().getPassword();
        String email=user.get().getEmail();
        if(StringUtils.isBlank(email)){
            return Result.errorMessage("该用户未配置邮箱,无法找回密码!");
        }
        MailUtils.send(email,"密码找回","您的密码为:"+pwd);
        return Result.success();
    }
}
