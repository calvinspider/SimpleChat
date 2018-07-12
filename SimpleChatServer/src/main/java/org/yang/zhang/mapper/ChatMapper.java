package org.yang.zhang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.yang.zhang.dto.RecentChatLogDto;
import org.yang.zhang.dto.RecentContract;
import org.yang.zhang.module.MessageInfo;
import org.yang.zhang.module.User;

import java.util.Date;
import java.util.List;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 18:45
 */
@Repository
@Mapper
public interface ChatMapper{

    List<MessageInfo> oneDayChatLog(RecentChatLogDto chatLogDto);

    List<RecentContract> oneMonthContract(@Param("userid") Integer userId);

}
