package org.yang.zhang.entity;

import java.io.Serializable;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 20 17:47
 */

public class Contract implements Serializable {
    private String userName;
    private String userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
