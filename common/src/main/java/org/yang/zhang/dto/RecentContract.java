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
    private String userId;
    private String nickName;
    private String lastMessage;
    private Date lastMessageDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
