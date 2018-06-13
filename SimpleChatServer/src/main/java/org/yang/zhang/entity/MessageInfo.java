package org.yang.zhang.entity;

import javax.persistence.*;

import java.util.Date;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 18 16:06
 */

@Entity
@Table(name = "t_chatinfo")
public class MessageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
}
