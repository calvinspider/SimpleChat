package org.yang.zhang.module;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.omg.CORBA.PRIVATE_MEMBER;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 12 15:16
 */
@Data
@Entity
@Table(name = "t_recent_contract")
public class RecentContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer contractUserId;
    private String lastMessage;
    private Date messageTime;
}
