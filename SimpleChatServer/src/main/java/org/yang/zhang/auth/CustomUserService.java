package org.yang.zhang.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.yang.zhang.mapper.UserCustMapper;
import org.yang.zhang.module.User;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 04 28 13:17
 */

public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserCustMapper userCustMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userCustMapper.selectByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.out.println("username:"+user.getUsername()+";password:"+user.getPassword());
        return user;
    }
}
