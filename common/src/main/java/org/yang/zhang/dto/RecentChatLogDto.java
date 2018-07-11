package org.yang.zhang.dto;

import java.util.Date;

import lombok.Data;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 07 11 12:14
 */
@Data
public class RecentChatLogDto {
    String id1;
    String id2;
    Date start;
    Date end;

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
