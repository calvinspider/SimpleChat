package org.yang.zhang.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yang.zhang.module.User;

import java.util.List;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 18:45
 */
@Repository
public interface ChatMapper{

    List<User> getFriendList(Integer userId);
}
