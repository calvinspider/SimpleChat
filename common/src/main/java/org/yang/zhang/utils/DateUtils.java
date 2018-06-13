package org.yang.zhang.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;


/**
 * 日期工具类
 */

public class
DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"};
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 获取字符串Timestamp，精确到毫秒
     */
    public static String getTimestamp(Date date) {
        return getTimestamp(date, null);
    }

    /**
     * 获取字符串Timestamp，精确到毫秒
     */
    public static String getTimestamp(Date date, Date defaultDate) {
        Date tmp = date == null ? defaultDate : date;
        return tmp == null ? null : String.valueOf(tmp.getTime());
    }

    /**
     * 获取短Timestamp，精确到秒
     */
    public static String getShortTimestamp(Date date) {
        return getShortTimestamp(date, null);
    }

    /**
     * 获取短Timestamp，精确到秒
     */
    public static String getShortTimestamp(Date date, Date defaultDate) {
        String timestamp = getTimestamp(date, defaultDate);
        return timestamp == null ? null : String.valueOf((int) (Long.valueOf(timestamp) / 1000));
    }

    /**
     * 获得当前Calendar
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 获得今年
     */
    public static int getThisYear() {
        return getCalendar().get(Calendar.YEAR);
    }

    /**
     * 获得本月
     */
    public static int getThisMonth() {
        return getCalendar().get(Calendar.MONTH) + 1;
    }

    /**
     * 获得当前时间
     */
    public static Date getNow() {
        return getCalendar().getTime();
    }

    /**
     * 获取格式化日期
     */
    public static String getDate(Date d, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(d);
    }

    /**
     * 增加年数
     */
    public static Date addYear(Date d, int n) {
        Calendar c = getCalendar();
        c.setTime(d);
        c.add(Calendar.YEAR, n);
        d = c.getTime();
        return d;
    }

    /**
     * 取两个时间之间的年数
     */
    public static int getYears(Date d1, Date d2) {
        int years = 0;
        Calendar c = getCalendar();
        c.setTime(d1);
        while (true) {
            if (c.getTime().after(d2))
                break;
            c.add(Calendar.YEAR, 1);
            years++;
        }
        return years;
    }

    /**
     * 增加月数
     */
    public static Date addMonth(Date d, int n) {
        Calendar c = getCalendar();
        c.setTime(d);
        c.add(Calendar.MONTH, n);
        d = c.getTime();
        return d;
    }

    /**
     * 判断是不是本月最后一天
     */
    public static Boolean MonthEnd(Date d) {
        Calendar c = getCalendar();
        c.setTime(d);
        int last = c.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取该月最大天数
        return (c.get(Calendar.DATE) == last);
    }

    /**
     * 获得本日
     */
    public static int getThisday(Date d) {
        Calendar c = getCalendar();
        c.setTime(d);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取本月最后一天
     */
    public static Date getMonthEnd(Date d) {
        Calendar c = getCalendar();
        c.setTime(d);
        int last = c.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取该月最大天数
        c.set(Calendar.DATE, last);
        d = c.getTime();
        return d;
    }

    /**
     * 增加天数
     */

    public static Date addDayOFMonth(Date d, int n) {
        Calendar c = getCalendar();
        c.setTime(d);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + n);
        d = c.getTime();
        return d;
    }

    /**
     * 设置成为该月的第 n 天的日期
     */

    public static Date setDayINMonth(Date d, int n) {
        Calendar c = getCalendar();
        c.setTime(d);
        c.set(Calendar.DAY_OF_MONTH, n);
        d = c.getTime();
        return d;
    }

    /**
     * 比较两个日期是否相同
     */
    public static boolean compare(Date d1, Date d2) {
        return (DateUtils.getDate(d1, "yyyyMMdd")).equalsIgnoreCase(DateUtils.getDate(d2, FORMAT_YYYYMMDD));
    }

    /**
     * 获得本月后续天数占该月的比率
     */
    public static float getMonthlyRate(Date d) {
        Calendar c = getCalendar();
        c.setTime(d);
        int day = c.get(Calendar.DAY_OF_MONTH); // 获取该月当前天数
        int last = c.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取该月最大天数
        return (float) ((last - day + 1.0) / last);
    }

    /**
     * 两个日期之间的天数占该月的比率
     */
    public static float getMonthlyRate2(Date d1, Date d2) {
        Calendar c = getCalendar();
        c.setTime(d2);
        long diff = d1.getTime() - d2.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        int last = c.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取该月最大天数
        return (float) ((days - 1.0) / last);
    }

    /**
     * 字符串转日期
     */
    public static Date getSdfDate(String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYYMMDDHHMMSS);
        Date date = null;
        try {
            date = sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转日期
     */
    public static Date getSdfDate_FORMAT_YYYY_MM_DD(String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
        Date date = null;
        try {
            date = sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }


    public static Date getDateStart(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateEnd(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取某天所在周的星期一
     */
    public static Date getMonday(Date date, TimeZone timeZone) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        if (timeZone != null) {
            calendar.setTimeZone(timeZone);
        }
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            calendar.add(Calendar.DATE, -6);
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        }
        return calendar.getTime();
    }

    /**
     * 获取某天所在周的星期一
     */
    public static Date getMonday(Date date) {
        return getMonday(date, null);
    }

    /**
     * 获取本周星期一
     */
    public static Date getMonday() {
        return getMonday(null);
    }
}
