package org.yang.zhang.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 11:52
 */
@Data
public class ChatRoomDto implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
