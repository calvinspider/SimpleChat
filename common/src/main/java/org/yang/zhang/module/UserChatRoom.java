package org.yang.zhang.module;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 11:03
 */
@Entity
@Table(name = "t_chatroom_user")
@Data
public class UserChatRoom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer roomId;
    private Integer userId;
}
