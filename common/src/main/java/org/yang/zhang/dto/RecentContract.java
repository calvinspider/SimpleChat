package org.yang.zhang.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 11 17:30
 */

public class RecentContract implements Serializable
{
    private String targetUserid;
    private String sourceUserid;
    private String nickName;
    private String lastMessage;
    private Date lastMessageDate;

    public String getTargetUserid() {
        return targetUserid;
    }

    public void setTargetUserid(String targetUserid) {
        this.targetUserid = targetUserid;
    }

    public String getSourceUserid() {
        return sourceUserid;
    }

    public void setSourceUserid(String sourceUserid) {
        this.sourceUserid = sourceUserid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }
}
