package org.yang.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yang.zhang.dto.in.QrLoginDto;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;
import org.yang.zhang.service.ApiService;
import org.yang.zhang.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private ApiService apiService;

    @Override
    public Result<User> login(String userId, String passWord,Integer status) {

        return apiService.login(userId,passWord,status);

    }

    public Result<Void> findPassWord(String userId) {

        return apiService.findPassWord(userId);

    }

    @Override
    public Result<User> loginByQrCode(QrLoginDto qrLoginDto) {
        return apiService.loginByQrCode(qrLoginDto);
    }
}
