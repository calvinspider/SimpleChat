package org.yang.zhang.dto;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 19:03
 */

public class SearchContractDto {
    private String key;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
