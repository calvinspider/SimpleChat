package org.yang.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.dto.LoginDto;
import org.yang.zhang.entity.Result;
import org.yang.zhang.service.LoginService;
import org.yang.zhang.utils.JsonUtils;

import com.fasterxml.jackson.core.type.TypeReference;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Result login(String userName, String passWord) {
        TypeReference type = new TypeReference<Result>(){};
        LoginDto loginDto=new LoginDto();
        loginDto.setUserName(userName);
        loginDto.setPassWord(passWord);
        try {
            String result=restTemplate.postForObject(Constant.LoginUrl,loginDto,String.class);
            return JsonUtils.fromJson(result, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.errorMessage("",null);
    }
}
