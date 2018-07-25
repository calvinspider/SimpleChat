package org.yang.zhang.module;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 25 17:58
 */
@Entity
@Data
@Table(name = "t_room_chatinfo")
public class RoomChatInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer roomId;
    private Integer userId;
    private String message;
    private Date messageTime;


}
