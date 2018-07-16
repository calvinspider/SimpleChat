package org.yang.zhang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GroupUserMapper {

    void deleteByUserIdAndGroupId(@Param("userId") Integer userId, @Param("groupId") Integer groupId);
}
