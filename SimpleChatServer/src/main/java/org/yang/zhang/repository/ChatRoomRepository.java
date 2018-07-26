package org.yang.zhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.yang.zhang.module.ChatRoom;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 18:13
 */

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Integer>{

}
