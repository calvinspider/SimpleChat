package org.yang.zhang.dto.in;

import java.io.Serializable;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 12 14 15:28
 */
@Data
public class QrLoginDto implements Serializable {

    private Integer userId;
    private String qrToken;

}
