package org.yang.zhang.service;

import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 13 12:01
 */

public interface UserService {
    Result<User> register(User user);
}
