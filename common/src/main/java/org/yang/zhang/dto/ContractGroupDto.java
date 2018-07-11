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
    private List<User> userList;

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
