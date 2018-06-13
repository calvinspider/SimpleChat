package org.yang.zhang.mapper;

import org.yang.zhang.entity.ClientInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 16:08
 */

public interface ClientInfoRepository extends CrudRepository<ClientInfo, Integer> {
    ClientInfo findClientByclientid(Integer clientId);
}
