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
    //源客户端id
    private Integer sourceclientid;
    //目标客户端id
    private Integer targetclientid;
    //消息类型
    private String msgtype;
    //消息内容
    private String msgcontent;
    private Integer sendflag;
    private Date time;
    private MessageType type;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

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

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
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
