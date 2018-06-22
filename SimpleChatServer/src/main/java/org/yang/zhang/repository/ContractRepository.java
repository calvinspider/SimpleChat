package org.yang.zhang.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.yang.zhang.entity.Contract;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 22 18:30
 */

public interface ContractRepository extends CrudRepository<Contract,Integer> {

    List<Contract> findByUserId(String userId);
}
