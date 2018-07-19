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
    Integer id1;
    Integer id2;
    Date start;
    Date end;

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
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
