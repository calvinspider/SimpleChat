package org.yang.zhang.service;

import java.util.List;

import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.entity.Contract;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 14 17:03
 */

public interface ContractService {
    List<Contract> getContractList(FindByUserDto s);
}
