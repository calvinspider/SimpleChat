package org.yang.zhang.service;

import org.yang.zhang.dto.in.QrLoginDto;
import org.yang.zhang.dto.in.UserLoginDto;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 13 17:29
 */

public interface UserService {

    Result<User> login(UserLoginDto userLoginDto);

    User register(User user);

    Result<Void> findPassWord(String userId);

    Result<User> loginByQrCode(QrLoginDto qrToken);

    Result<Void> registerQrCode(QrLoginDto qrToken);
}
