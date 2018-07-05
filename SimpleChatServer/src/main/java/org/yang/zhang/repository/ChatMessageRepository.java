package org.yang.zhang.repository;


import org.springframework.data.repository.CrudRepository;
import org.yang.zhang.module.MessageInfo;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 17:02
 */

public interface ChatMessageRepository extends CrudRepository<MessageInfo, Integer> {

}
