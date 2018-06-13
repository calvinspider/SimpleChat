package org.yang.zhang.mapper;

import org.yang.zhang.entity.MessageInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 17:02
 */

public interface ChatMessageRepository extends CrudRepository<MessageInfo, Integer> {

}
