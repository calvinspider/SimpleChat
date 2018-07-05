package org.yang.zhang.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author calvin.zhang
 * @Description
 * @Date 2018 05 16 13:12
 */

public class DateUtiles extends DateUtils {
    /**
     * 根据当前日期获得所在周的日期区间（周一和周日日期）
     */
    public static Date[] getWeekTimeInterval(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        Date imptimeBegin = cal.getTime();
        cal.add(Calendar.DATE, 6);
        Date imptimeEnd = cal.getTime();
        return new Date[]{imptimeBegin,imptimeEnd};
    }

    public static Date getMonthEnd(Date time) {
        String strY = null;
        String strZ = null;
        boolean leap = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int x = cal.get(Calendar.YEAR);
        int y = cal.get(Calendar.MONTH);
        if (y == 1 || y == 3 || y == 5 || y == 7 || y == 8 || y == 10 || y == 12) {
            strZ = "31";
        }
        if (y == 4 || y == 6 || y == 9 || y == 11) {
            strZ = "30";
        }
        if (y == 2) {
            leap = leapYear(x);
            if (leap) {
                strZ = "29";
            }
            else {
                strZ = "28";
            }
        }
        strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        try {
            return parseDate(x + "-" + strY + "-" + strZ,"yyyy-MM-dd") ;
        }catch (Exception e){
            e.printStackTrace();
            return new Date();
        }

    }

    public static Date[] getyearInterval(Integer year) {
        Date[] dates={new Date(),new Date()};
        try {
            dates[0]= parseDate(year + "-01" + "-01","yyyy-MM-dd") ;
            dates[1]= parseDate(year + "-12" + "-31","yyyy-MM-dd") ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * 功能：判断输入年份是否为闰年<br>
     *
     * @param year
     * @return 是：true  否：false
     * @author pure
     */
    public static boolean leapYear(int year) {
        boolean leap;
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) leap = true;
                else leap = false;
            }
            else leap = true;
        }
        else leap = false;
        return leap;
    }


}
