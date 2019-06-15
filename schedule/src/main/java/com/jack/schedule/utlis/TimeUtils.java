package com.jack.schedule.utlis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2016/12/12 17:32
 */
public class TimeUtils {


    public static final long MIN_MILLIS = 60  * 1000;
    public static final long HOUT_MILLIS = MIN_MILLIS * 60;
    public static final long DAY_MILLIS = HOUT_MILLIS * 24;
    public static final long WEEK_MILLIS = DAY_MILLIS * 7;
    public static final long MONTH_MILLIS = DAY_MILLIS * 30;
    public static final long YEAR_MILLIS = DAY_MILLIS * 365;

    public static final int MIN_SECOND = 60;
    public static final int HOUT_SECOND = MIN_SECOND * 60;
    public static final int DAY_SECOND = HOUT_SECOND * 24;
    public static final int WEEK_SECOND = DAY_SECOND * 7;
    public static final int MONTH_SECOND = DAY_SECOND * 30;
    public static final int YEAR_SECOND = DAY_SECOND * 365;

    public static final String DATE_FORMAT_1 = "yyyy年MM月dd号";
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_3 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_4 = "MM-dd HH:mm";
    public static final String DATE_FORMAT_5 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_FORMAT_6 = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_7 = "MM/dd HH:mm";
    public static final String DATE_FORMAT_8 = "yyyy年MM月dd号HH时";
    public static final String DATE_FORMAT_9 = "yyyy-MM-dd HH:mm";



    public static String getTime() {
        return String.valueOf(System.currentTimeMillis());
    }


    public static String getDate(String format) {
        return getDate(format, System.currentTimeMillis());
    }

    public static String getDate(String format, long millisecond) {
        if (millisecond < Integer.MAX_VALUE){
            millisecond *= 1000L;
        }
        return new SimpleDateFormat(format).format(new Date(millisecond));
    }

    public static String getDate(String format, Date date) {
        return new SimpleDateFormat(format).format(date);
    }


    public static String getDate(long time){
        int h = (int) (time / 1000 / 3600);
        int m = (int) (time / 1000 / 60 % 60);
        int s = (int) (time / 1000 % 60);
        String hour = h < 10 ? "0" + h : String.valueOf(h);
        String min = m < 10 ? "0" + m : String.valueOf(m);
        String sec = s < 10 ? "0" + s : String.valueOf(s);
        return hour + ":" + min + ":" + sec;
    }

    /**
     * 字符串转时间戳
     * @param date 日期
     * @return 时间戳
     */
    public static long getTime(String format, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = new Date();
        try {
            d = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d.getTime();
    }


    /**
     * 日期转时间戳
     * @param time
     * @return
     */
    public static long getTimeMillis(String format, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static long getTimeMillis(int hour, int min, int s){
        return getTimeMillis(getYears(), getMonth(), getDay(), hour, min, s);
    }

    /**
     * 获取指定日期的时间戳
     */
    public static long getTimeMillis(int year, int month, int day, int hour, int min, int s){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, s);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取今天0点的时间戳
     */
    public static long getTodayZero(){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, getYears());
        calendar.set(Calendar.MONTH, getMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, getDay());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }



    /**
     * 获取某一天0点的时间戳
     */
    public static long getTodayZero(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取某一天0点的时间戳
     */
    public static long getTodayZero(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(millis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }


    /**
     * time    : 2019-06-12 11:28
     * desc    : 返回某一月第一天0点的时间戳
     * versions: 1.0
     */
    public static long getStartZeroMonth(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(millis);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * time    : 2019-06-12 11:28
     * desc    : 返回某一月最后一天0点的时间戳
     * versions: 1.0
     */
    public static long getEndZeroMonth(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(millis);
        calendar.set(Calendar.MONTH, getMonth(millis));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis() - DAY_MILLIS;
    }


    /**
     * 判断是非闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }


    /**
     * 获取今天的前几天或者后几天
     * @param offset 偏移的天, > 0 为未来, < 0 为过去
     * @return 时间戳
     */
    public static long getDayOffset(int offset) {
        int day = getDay();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day + offset);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取当前年
     * @return
     */
    public static int getYears(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前年
     * @return
     */
    public static int getYears(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     * @return
     */
    public static int getMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前月
     * @return
     */
    public static int getMonth(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * 获取当前日
     * @return
     */
    public static int getDay(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前日
     * @return
     */
    public static int getDay(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前小时
     * @return
     */
    public static int getHour(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前小时
     * @return
     */
    public static int getHour(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }


    /**
     * 获取当前分钟
     * @return
     */
    public static int getMin(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取当前分钟
     * @return
     */
    public static int getMin(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取当前月的总天数
     * @return
     */
    public static int getDayCount(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 获取指定年月的总天数
     * @param years
     * @param month
     * @return
     */
    public static int getDay(int years, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, years);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getActualMaximum(Calendar.DATE);

    }

    public static int getWeek(int year, int month, int day){
        int[] date = {7,1,2,3,4,5,6};
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return date[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }


    /**
     * time    : 2019/5/20 15:24
     * desc    : 获取离传入时间最新的星期几的时间戳  Calendar.MONDAY
     * versions: 1.0
     */
    public static long getWeekAndDay(long millis, int dayOfWeek){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.setFirstDayOfWeek(dayOfWeek);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //获取周一的时间戳
        return calendar.getTimeInMillis();
    }

    /**
     * time    : 2019/4/23 15:52
     * desc    : 获取当前年是第几周
     * versions: 1.0
     */
    public static int getYearAndWeek(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * time    : 2019/4/24 21:55
     * desc    : 根据时间获取是这年的第几周
     * versions: 1.0
     */
    public static int getYearAndWeek(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * time    : 2019/4/24 21:57
     * desc    : 获取今年第X周的时间戳
     * versions: 1.0
     */
    public static long getYearAndWeek(int yearAndWeek){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, yearAndWeek);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, getWeek(calendar.getTimeInMillis()));
        return calendar.getTimeInMillis();
    }


    /**
     * 返回今天的日期
     *
     * @return
     */
    public static String getTodayDate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        return sf.format(c.getTime());
    }

    /**
     * 返回指定时间的星期
     *
     * @param millis
     * @return
     */
    public static String getWeekOfDate(long millis) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 返回指定时间的星期
     *
     * @param millis
     * @return
     */
    public static String getWeekOfDate2(long millis) {
        if (millis < Integer.MAX_VALUE){
            millis *= 1000L;
        }
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    public static int getWeek(long millis){
        int[] weekDays = {Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY};
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    /**
     * 是否过去的时间
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean getPastTime(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, getYears());
        calendar2.set(Calendar.MONTH, getMonth() - 1);
        calendar2.set(Calendar.DAY_OF_MONTH, getDay());
        return calendar2.getTimeInMillis() >= calendar.getTimeInMillis();
    }
}