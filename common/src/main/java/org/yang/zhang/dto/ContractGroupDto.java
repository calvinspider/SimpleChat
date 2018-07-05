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
}
