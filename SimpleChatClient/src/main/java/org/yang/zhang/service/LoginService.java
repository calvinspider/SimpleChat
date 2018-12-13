package org.yang.zhang.service;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 13 16:27
 */

public interface LoginService {

    Result<User> login(String userName, String passWord, Integer status);

    Result<Void> findPassWord(String text);
}
