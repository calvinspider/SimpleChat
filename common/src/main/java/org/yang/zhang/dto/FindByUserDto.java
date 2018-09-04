package org.yang.zhang.dto;

import lombok.Data;

@Data
public class FindByUserDto {
    private Integer userId;
    private Boolean onlyOnline;

    public Boolean getOnlyOnline() {
        return onlyOnline;
    }

    public void setOnlyOnline(Boolean onlyOnline) {
        this.onlyOnline = onlyOnline;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
