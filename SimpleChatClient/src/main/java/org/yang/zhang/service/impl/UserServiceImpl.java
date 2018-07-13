package org.yang.zhang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.entity.Result;
import org.yang.zhang.module.User;
import org.yang.zhang.service.UserService;
import org.yang.zhang.utils.JsonUtils;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 13 12:01
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Result<User> register(User user) {
        TypeReference type = new TypeReference<Result<User>>() {};
        String result=restTemplate.postForObject(Constant.USERREGISTER,user,String.class);
        return JsonUtils.fromJson(result, type);
    }

}
