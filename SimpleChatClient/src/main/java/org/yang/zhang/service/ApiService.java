package org.yang.zhang.service;

import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;

/**
 * @Auther: Administrator
 * @Date: 2018/12/13 22:49
 * @Description: 接口调用
 */
public interface ApiService {

    Result<User> login(String userId, String passWord, Integer status);

    Result<Void> findPassWord(String userId);
}
