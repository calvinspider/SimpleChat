package org.yang.zhang.service;

import org.yang.zhang.entity.Result;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 13 17:29
 */

public interface UserService {
    Result login(String userName, String passWord);
}
