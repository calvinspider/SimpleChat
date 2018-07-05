package org.yang.zhang.module;

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