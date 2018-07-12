package org.yang.zhang.dto;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 18:09
 */

public class AddContractDto {
    private String userName;
    private String userId;
    private String commonCount;
    private String userIcon;

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

    public String getCommonCount() {
        return commonCount;
    }

    public void setCommonCount(String commonCount) {
        this.commonCount = commonCount;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }
}
