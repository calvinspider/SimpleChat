package org.yang.zhang.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.yang.zhang.module.ContractGroup;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 05 15:23
 */

public interface ContractGroupRepository extends CrudRepository<ContractGroup,Integer> {

    List<ContractGroup> findByUserId(Integer userId);
}
