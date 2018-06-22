package org.yang.zhang.entity;

import java.io.Serializable;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 20 17:47
 */

public class Contract implements Serializable {
    private Integer id;
    private String userId;
    private String contractId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
}
