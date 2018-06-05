package org.yang.zhang.service;

import org.yang.zhang.module.User;

import java.util.List;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 18:43
 */

public interface ChatService {

    List<User> getFriendList(Integer userId);
}
