package org.yang.zhang.service;

import org.yang.zhang.entity.Contract;
import org.yang.zhang.entity.User;

import java.util.List;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 18:43
 */

public interface ChatService {

    List<Contract> getContractList(String userId);
}
