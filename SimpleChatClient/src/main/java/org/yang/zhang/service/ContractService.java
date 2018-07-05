package org.yang.zhang.service;

import java.util.List;

import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 14 17:03
 */

public interface ContractService {
    List<ContractGroupDto> getContractList(FindByUserDto s);
}
