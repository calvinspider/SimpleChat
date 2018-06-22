package org.yang.zhang.service.impl;

import org.yang.zhang.entity.Contract;
import org.yang.zhang.mapper.ChatMapper;
import org.yang.zhang.entity.User;
import org.yang.zhang.repository.ContractRepository;
import org.yang.zhang.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 18:43
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private ContractRepository contractRepository;
    @Override
    public List<Contract> getContractList(String userId) {
        return contractRepository.findByUserId(userId);
    }
}
