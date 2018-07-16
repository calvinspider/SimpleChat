package org.yang.zhang.dto;

import java.util.List;

import org.yang.zhang.module.User;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 05 14:35
 */
@Data
public class ContractGroupDto {
    private Integer groupId;
    private String groupName;
    private Integer userId;
    private List<User> userList;
    private String oldGroupId;
    private String newGroupId;

    public String getOldGroupId() {
        return oldGroupId;
    }

    public void setOldGroupId(String oldGroupId) {
        this.oldGroupId = oldGroupId;
    }

    public String getNewGroupId() {
        return newGroupId;
    }

    public void setNewGroupId(String newGroupId) {
        this.newGroupId = newGroupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
