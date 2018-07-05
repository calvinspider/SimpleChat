package org.yang.zhang.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.yang.zhang.module.GroupUser;
import org.yang.zhang.module.User;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 05 15:24
 */

public interface GroupUserRepository extends CrudRepository<GroupUser,Integer> {
    List<GroupUser> findbyGroupId(Integer id);
}
