package org.yang.zhang.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 16:06
 */
@Data
@Entity
@Table(name = "t_chatinfo")
public class MessageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String sourceclientid;
    private String targetclientid;
    private Integer msgtype;
    private String msgcontent;
    private Integer sendflag;
    private Date time;
}
