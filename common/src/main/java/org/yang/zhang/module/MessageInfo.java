package org.yang.zhang.module;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.yang.zhang.enums.MessageType;

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
    private Integer sourceclientid;
    private Integer targetclientid;
    private MessageType msgtype;
    private String msgcontent;
    private Integer sendflag;
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceclientid() {
        return sourceclientid;
    }

    public void setSourceclientid(Integer sourceclientid) {
        this.sourceclientid = sourceclientid;
    }

    public Integer getTargetclientid() {
        return targetclientid;
    }

    public void setTargetclientid(Integer targetclientid) {
        this.targetclientid = targetclientid;
    }

    public MessageType getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(MessageType msgtype) {
        this.msgtype = msgtype;
    }

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    public Integer getSendflag() {
        return sendflag;
    }

    public void setSendflag(Integer sendflag) {
        this.sendflag = sendflag;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
