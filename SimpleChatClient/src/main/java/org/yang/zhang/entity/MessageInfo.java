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

    private String msgcontent;

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

    public String getMsgcontent() {
        return msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
