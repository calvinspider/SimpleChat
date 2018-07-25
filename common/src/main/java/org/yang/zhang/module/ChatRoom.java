package org.yang.zhang.module;

import java.io.Serializable;
import java.io.StreamCorruptedException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 11:01
 */
@Entity
@Table(name = "t_chatroom")
@Data
public class ChatRoom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String type;
    private String remark;
    private String icon;

}
