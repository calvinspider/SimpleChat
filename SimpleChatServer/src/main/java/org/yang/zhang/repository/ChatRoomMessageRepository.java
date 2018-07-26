package org.yang.zhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yang.zhang.module.RoomChatInfo;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 26 15:51
 */

public interface ChatRoomMessageRepository extends JpaRepository<RoomChatInfo,Integer> {
}
