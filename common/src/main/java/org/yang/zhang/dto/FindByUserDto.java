package org.yang.zhang.dto;

import lombok.Data;

@Data
public class FindByUserDto {
    private Integer userId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
