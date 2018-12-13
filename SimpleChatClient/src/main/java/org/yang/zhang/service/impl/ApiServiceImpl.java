package org.yang.zhang.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.dto.in.UserLoginDto;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;
import org.yang.zhang.service.ApiService;
import org.yang.zhang.utils.JsonUtils;

/**
 * @Auther: Administrator
 * @Date: 2018/12/13 22:50
 * @Description:
 */
@Service("apiService")
public class ApiServiceImpl implements ApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Result<User> login(String userId, String passWord, Integer status) {
        TypeReference type = new TypeReference<Result<User>>(){};
        UserLoginDto userLoginDto=new UserLoginDto();
        userLoginDto.setUserId(Integer.valueOf(userId));
        userLoginDto.setPassWord(passWord);
        userLoginDto.setStatus(status);
        try {
            String result=restTemplate.postForObject(Constant.LoginUrl,userLoginDto,String.class);
            return JsonUtils.fromJson(result, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.errorMessage("登陆失败");
    }

    @Override
    public Result<Void> findPassWord(String userId) {
        TypeReference type = new TypeReference<Result<Void>>(){};
        try {
            String result=restTemplate.postForObject(Constant.FindPassword,null,String.class,userId);
            return JsonUtils.fromJson(result, type);
        }catch (Exception e){
            e.printStackTrace();
            return Result.errorMessage("系统异常,请稍后再试!");
        }
    }
}
