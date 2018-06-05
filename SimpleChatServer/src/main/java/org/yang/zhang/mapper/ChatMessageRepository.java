package org.yang.zhang.mapper;

import org.yang.zhang.module.ClientInfo;
import org.yang.zhang.module.MessageInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 17:02
 */

public interface ChatMessageRepository extends CrudRepository<MessageInfo, Integer> {

}
