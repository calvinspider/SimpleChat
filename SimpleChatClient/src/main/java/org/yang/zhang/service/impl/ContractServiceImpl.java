package org.yang.zhang.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.yang.zhang.constants.Constant;
import org.yang.zhang.dto.ContractGroupDto;
import org.yang.zhang.dto.FindByUserDto;
import org.yang.zhang.service.ContractService;
import org.yang.zhang.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 14 17:02
 */
@Service("contractService")
public class ContractServiceImpl implements ContractService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<ContractGroupDto> getContractList(FindByUserDto userId) {
        TypeReference type = new TypeReference<List<ContractGroupDto>>(){};
        try {
            String result=restTemplate.postForObject(Constant.ContractUrl,userId,String.class);
            return JsonUtils.fromJson(result, type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
