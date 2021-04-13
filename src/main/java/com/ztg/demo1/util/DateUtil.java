package com.ztg.demo1.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 日期工具类
 * @author: gaodm
 * @time: 2018/8/6 11:17
 */
public class DateUtil {

    public static final long TEN_MINUTES = 10 * 60 * 1000;
    public static final long ONE_DAY = 24 * 60 * 60 * 1000;

    // Internal values for using in date/time calculations
    public static final long MILLISECOND_OF_SECOND = 1000;
    public static final long MILLISECOND_OF_MINUTE = MILLISECOND_OF_SECOND * 60;
    public static final long MILLISECOND_OF_HOUR = MILLISECOND_OF_MINUTE * 60;
    public static final long MILLISECOND_OF_DAY = MILLISECOND_OF_HOUR * 24;
    public static final long MILLISECOND_OF_WEEK = MILLISECOND_OF_DAY * 7;

    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 日期格式：yyyyMMdd
     */
    public static final String DATE_FORMAT_NO_SPLIT = "yyyyMMdd";
    /**
     * 日期格式：yyyyMMddHHmmss
     */
    public static final String DATE_TIME_FORMAT_NO_SPLIT = "yyyyMMddHHmmss";
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATE_TIME_MS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String FORMAT_TIME = "HH:mm:ss";

    /**
     * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 中文简写  如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";

    /**
     * 中文全称  如：2010年12月01日  23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 中文全称精确到分钟  如：2010年12月01日  23时15分
     */
    public static String FORMAT_LONG_CN_MI = "yyyy年MM月dd日  HH时mm分";

    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";


    /**
     * Adds or subtracts the specified amount of time to the given calendar field, based on the calendar's rules. For
     * example, to subtract 5 days from the current time of the calendar, you can achieve it by calling:
     * <p>
     * <code>add(Calendar.DAY_OF_MONTH, -5)</code>.
     *
     * @param date   the date of before the changed.
     * @param field  the calendar field.
     * @param amount the amount of date or time to be added to the field.
     * @return
     */
    public static Date add(final Date date, Integer field, Integer amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(field, amount);

        return calendar.getTime();
    }

    /**
     * 添加天数
     *
     * @param date 要操作的日期
     * @param days 天数
     * @return
     */
    public static Date addDate(final Date date, Integer days) {
        return add(date, Calendar.DATE, days);
    }

    /**
     * 按指定的格式，将日期转换成为字符
     *
     * @param date   日期
     * @param format 格式
     * @return 按指定的格式，将日期转换成为字符
     */
    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }

        if (isEmpty(format)) {
            format = DATE_FORMAT;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 要判断的字符串
     * @return boolean
     */
    private static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 将日期转换成为字符（yyyy-MM-dd）
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return format(date, DATE_FORMAT);
    }

    /**
     * 今天日期的字符（yyyy-MM-dd）
     *
     * @return 今天日期的字符（yyyy-MM-dd）
     */
    public static String today() {
        return formatDate(new Date());
    }

    /**
     * 将日期转换成为字符（yyyy-MM-dd HH:mm:ss）
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return format(date, DATE_TIME_FORMAT);
    }

    /**
     * 当前时间的字符（yyyy-MM-dd HH:mm:ss）
     *
     * @return 当前时间的字符（yyyy-MM-dd HH:mm:ss）
     */
    public static String nowString() {
        return formatDateTime(now());
    }

    /**
     * 当前时间
     *
     * @return 当前时间
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 将日期转换成为字符（yyyy-MM-dd HH:mm:ss.SSS）
     *
     * @param date 要格式化的数据
     * @return 将日期转换成为字符（yyyy-MM-dd HH:mm:ss.SSS）
     */
    public static String formatDateTimeMs(Date date) {
        return format(date, DATE_TIME_MS_FORMAT);
    }

    /**
     * 生成日期
     *
     * @param object 要转换的数据
     * @return 生成日期
     */
    public static Date getDateValue(Object object) {
        return null == object ? null : (Date) object;
    }

    /**
     * 按指定的格式，将字符转换为日期
     *
     * @param dateString 日期
     * @param format     格式
     * @return 按指定的格式，将字符转换为日期
     */
    public static Date parseDate(String dateString, String format) {
        if (isEmpty(format)) {
            return null;
        }

        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取昨天的日期格式化字符串 yyyy-MM-dd
     *
     * @return
     */
    public static String yesterdayFormate() {
        Date date = new Date();
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        return formatDate(dBefore);
    }

    /**
     * 将字符（yyyy-MM-dd）转换为日期
     *
     * @param dateString
     * @return 将字符（yyyy-MM-dd）转换为日期
     */
    public static Date parseDate(String dateString) {
        return parseDate(dateString, DATE_FORMAT);
    }

    /**
     * 将字符（yyyy-MM-dd HH:mm:ss）转换为日期
     *
     * @param dateString
     * @return 将字符（yyyy-MM-dd HH:mm:ss）转换为日期
     */
    public static Date parseDateTime(String dateString) {
        return parseDate(dateString, DATE_TIME_FORMAT);
    }

    /**
     * milliseconds 转化为日期
     *
     * @param date
     * @return milliseconds 转化为日期
     */
    public static Date toDate(Long date) {
        if (date == null) {
            return null;
        }
        return new Date(date);
    }

    /**
     * 获取当前时间 milliseconds
     *
     * @return 获取当前时间 milliseconds
     */
    public static long getCurrentTime() {
        return new Date().getTime();
    }

    /**
     * 获取当前时间字符串 默认格式：yyyy-MM-dd HH:mm:ss
     *
     * @param dateFormatPattern 日期转换格式
     * @return 获取当前时间字符串 默认格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateStr(String dateFormatPattern) {
        if (isEmpty(dateFormatPattern)) {
            dateFormatPattern = DATE_TIME_FORMAT;
        }
        return format(new Date(), dateFormatPattern);
    }

    public static Date getServerTime(String operateTimeStr) {
        Date serverTime = new Date();
        if (isEmpty(operateTimeStr)) {
            return serverTime;
        }

        Date operateTime = parseDate(operateTimeStr, DATE_TIME_FORMAT);

        if (operateTime == null) {
            operateTime = parseDate(operateTimeStr, DATE_TIME_MS_FORMAT);
        }

        Long interval = operateTime.getTime() - serverTime.getTime();
        if (operateTime.after(serverTime) && TEN_MINUTES < interval) {
            return serverTime;
        } else if (operateTime.before(serverTime) && ONE_DAY < Math.abs(interval)) {
            return serverTime;
        } else {
            return operateTime;
        }
    }

    /**
     * 添加或减少月
     *
     * @param date   要操作的日期
     * @param months 月
     * @return操作过的日期
     */
    public static Date addMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 添加或减少周
     *
     * @param date  要操作的日期
     * @param weeks 周
     * @return操作过的日期
     */
    public static Date addWeek(Date date, int weeks) {
        if (date != null) {
            return new Date(date.getTime() + weeks * MILLISECOND_OF_WEEK);
        }
        return date;
    }

    /**
     * 添加或减少天数
     *
     * @param date 日期
     * @param days 天数
     * @return 操作过的日期
     */
    public static Date addDay(Date date, int days) {
        if (date != null) {
            return new Date(date.getTime() + days * MILLISECOND_OF_DAY);
        }
        return date;
    }

    /**
     * 添加或减少小时
     *
     * @param date  要操作的日期
     * @param hours 小时
     * @return 操作过的日期
     */
    public static Date addHour(Date date, int hours) {
        if (date != null) {
            return new Date(date.getTime() + hours * MILLISECOND_OF_HOUR);
        }
        return date;
    }

    /**
     * 添加或减少分钟
     *
     * @param date    要操作的日期
     * @param minutes 分钟
     * @return 操作过的日期
     */
    public static Date addMinutes(Date date, int minutes) {
        if (date != null) {
            return new Date(date.getTime() + minutes * MILLISECOND_OF_MINUTE);
        }
        return date;
    }

    /**
     * 获得某个月的第一天0时0分0秒的时间
     *
     * @param year  年
     * @param month 月
     * @return java.util.Date
     */
    public static Date getFirstDateOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取某一天的0时0分0秒的时间
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 返回加上0点的时间
     */
    public static Date getFirstTimeOfDay(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取某一天的0时0分0秒的时间
     *
     * @param date 输入日期
     * @return 返回加上0点的时间
     */
    public static Date getFirstTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取某一天的23时59分59秒的时间
     *
     * @param date 日期
     * @return 某一天的23时59分59秒的时间
     */
    public static Date getLastTimeOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 获得Date型对象，根据时间点
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 时间点
     */
    public static Date getDateFromTime(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);

        return cal.getTime();
    }

    /**
     * 将时间字符串进行相加后操作后格式化输出
     *
     * @param timeStr 格式<HH:mm:ss>
     * @param hour    小时
     * @param minute  分钟
     * @param second  秒
     * @return 格式<HH:mm>
     */
    public static String timeOperate(String timeStr, int hour, int minute, int second) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_TIME);
        String[] times = timeStr.trim().split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1]));
        if (times.length == 3) {
            calendar.set(Calendar.SECOND, Integer.valueOf(times[2]));
        }
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        return df.format(calendar.getTime());

    }

    /**
     * 根据指定string生成当天时间
     *
     * @param timeStr 格式<HH:mm> or <HH:mm:ss>
     * @return 当天时间
     */
    public static Date getCurrentDayTimeByStr(String timeStr) {
        String[] times = timeStr.trim().split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1]));
        if (times.length == 3) {
            calendar.set(Calendar.SECOND, Integer.valueOf(times[2]));
        } else {
            calendar.set(Calendar.SECOND, 0);
        }
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 根据指定string生成指定日期时间
     *
     * @param timeStr 格式<HH:mm> or <HH:mm:ss>
     * @param date    日期
     * @return 日期时间
     */
    public static Date getSpecialDayTimeByStr(Date date, String timeStr) {
        String[] times = timeStr.trim().split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(times[0]));
        calendar.set(Calendar.MINUTE, Integer.valueOf(times[1]));
        if (times.length == 3) {
            calendar.set(Calendar.SECOND, Integer.valueOf(times[2]));
        } else {
            calendar.set(Calendar.SECOND, 0);
        }
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取两个日期间所跨0点次数
     *
     * @param d1 开始日期
     * @param d2 结束日期
     * @return 得到两个日期相差的天数
     */
    public static Integer getDaysBetweenTwoDate(Date d1, Date d2) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Date d11 = sdf.parse(sdf.format(d1));
            Date d21 = sdf.parse(sdf.format(d2));
            Calendar cal = Calendar.getInstance();
            cal.setTime(d11);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d21);
            long time2 = cal.getTimeInMillis();
            long between_days = Math.abs((time2 - time1) / (1000 * 3600 * 24));

            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            return null;
        }
    }

    public static boolean after(Date date1, Date date2) {
        return date1.compareTo(date2) > 0;
    }

    public static boolean before(Date date1, Date date2) {
        return date1.compareTo(date2) < 0;
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static String getYear(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        return df.format(date).substring(0, 4);
    }

    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫
     *
     * @param date 日期
     * @return 返回毫
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }
    
    /**
     * 计算2个日期相差多少年
     * @param fromDate
     * @param toDate
     * @return
     */
    public static Integer yearCompare(Date fromDate,Date toDate){
    	if(fromDate==null||toDate==null){
    		return null;
    	}
    	Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        int fromYear = from.get(Calendar.YEAR);
        int toYear = to.get(Calendar.YEAR);
        int year = toYear  -  fromYear;
        return year;
    }
    
}
