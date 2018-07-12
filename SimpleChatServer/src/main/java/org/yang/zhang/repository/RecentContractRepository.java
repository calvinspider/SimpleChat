package org.yang.zhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yang.zhang.module.RecentContract;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 15:18
 */

public interface RecentContractRepository extends JpaRepository<RecentContract,Integer> {

    RecentContract findByUserIdAndContractUserId(Integer sourceclientid, Integer targetclientid);
}
