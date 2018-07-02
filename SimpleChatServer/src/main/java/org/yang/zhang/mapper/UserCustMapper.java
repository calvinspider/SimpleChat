package org.yang.zhang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yang.zhang.entity.User;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 04 28 13:33
 */
@Repository
@Mapper
public interface UserCustMapper {

    User selectByUsername(@Param("userName") String userName);
}
