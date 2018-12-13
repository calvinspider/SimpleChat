package org.yang.zhang.dto.in;

import lombok.Data;
import org.yang.zhang.enums.UserStatusType;

import java.io.Serializable;

/**
 * @Auther: Administrator
 * @Date: 2018/12/13 22:42
* @Description: 用户登录DTO
 * status:
 * @see UserStatusType
 */

@Data
public class UserLoginDto implements Serializable {

    private static final long serialVersionUID = 6665756844044782479L;

    Integer userId;
    String passWord;
    Integer status;

}
