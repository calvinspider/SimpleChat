package org.yang.zhang.service;

import java.util.List;

import org.yang.zhang.dto.AddContractDto;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.module.User;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 14 17:03
 */

public interface ContractService {
    List<ContractGroupDto> getContractList(FindByUserDto s);

    List<AddContractDto> getRecommendContract(String userName);

    List<AddContractDto> searchContract(Integer id,String key);
}
