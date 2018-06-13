package org.yang.zhang.service.impl;

import org.yang.zhang.mapper.ChatMapper;
import org.yang.zhang.entity.User;
import org.yang.zhang.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 18:43
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMapper chatMapper;

    @Override
    public List<User> getFriendList(Integer userId) {
        List<User> list= chatMapper.getFriendList(userId);
        return list;
    }
}
