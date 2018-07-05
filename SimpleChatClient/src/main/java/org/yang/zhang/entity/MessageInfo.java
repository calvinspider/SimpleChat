package org.yang.zhang.entity;

import java.io.Serializable;
import java.util.Date;

import org.yang.zhang.enums.MessageType;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 06 07 15:35
 */

public class MessageInfo implements Serializable {

    private Integer id;
    private String sourceclientid;
    private String targetclientid;
    private Integer msgtype;
    private String msgcontent;
    private Integer sendflag;
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceclientid() {
        return sourceclientid;
    }

    public void setSourceclientid(String sourceclientid) {
        this.sourceclientid = sourceclientid;
    }

    public String getTargetclientid() {
        return targetclientid;
    }

    public void setTargetclientid(String targetclientid) {
        this.targetclientid = targetclientid;
    }

    public Integer getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(Integer msgtype) {
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
