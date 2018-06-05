package org.yang.zhang.mapper;

import org.yang.zhang.module.MessageInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageCustRepository {
    List<MessageInfo> findUnsendChatMessage(Integer clientId);
}
