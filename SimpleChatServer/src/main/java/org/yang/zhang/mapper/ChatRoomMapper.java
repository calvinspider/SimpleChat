package org.yang.zhang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.yang.zhang.module.ChatRoom;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 11:42
 */
@Repository
@Mapper
public interface ChatRoomMapper {
    List<ChatRoom> getUerChatRooms(Integer id);
}
