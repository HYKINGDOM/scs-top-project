package com.scs.top.project.common.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * 时间处理工具类
 * 1,不建议使用SimpleDateFormat方法来格式化时间,因为在高并发的情况下会出现时间错乱的问题
 * 建议获取时间或者转换时间使用java.time.format.DateTimeFormatter方法
 * 2,新建在该类中的方法,尽量设置为,需要的时间格式传入参数形式
 */
public class DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String MM_DD = "MM-dd";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDD = "yyyyMMdd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     *
     * @param date 日期
     * @return 返回yyyy-MM-dd格式日期
     */
    public static String format(Date date) {
        return format(date, YYYY_MM_DD);
    }

    /**
     * 按照传入的日期格式进行日期格式化
     *
     * @param date    日期
     * @param pattern 格式，如： YYYY_MM_DD_HH_MM_SS
     * @return 返回格式日期
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
            Instant instant = date.toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            return localDateTime.format(dateTimeFormatter);
        }
        return null;
    }

    /**
     * 当前时间转换成String格式
     *
     * @param pattern 传入需要转换的时间格式
     * @return
     */
    public static String currentDateToString(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * 当前时间转换成String格式
     *
     * @param pattern 传入需要转换的时间格式
     * @return
     */
    public static String currentDateToDate(String pattern) {
        org.joda.time.format.DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
        return dateTimeFormatter.print(System.currentTimeMillis());
    }

    /**
     * 将字符串时间格式转换为自定义时间格式
     * @param dateTimeStr
     * @param dateFormate
     * @return
     */
    public static Date toFormatDate(String dateTimeStr,String dateFormate) {
        org.joda.time.format.DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormate);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }


    /**
     * 字符串转换成日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        org.joda.time.format.DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
        return dateTimeFormatter.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     *
     * @param week 周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return 返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date    日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date    日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date  日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date  日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date   日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date  日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }


    /**
     * 传入（字符串）起止时间得到中间的所有月份（包括起止时间）
     *
     * @param minDate 字符串类型开始时间
     * @param maxDate 字符串类型结束时间
     * @return 返回时间是结束时间的上一个月时间
     */
    public static List<String> getMonthBetween(String minDate, String maxDate ,String dateFormate){
        List<String> result = new ArrayList<>();

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(Objects.requireNonNull(stringToDate(minDate, dateFormate)));
        max.setTime(Objects.requireNonNull(stringToDate(maxDate, dateFormate)));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 1);

        while (min.before(max)) {
            result.add(format(min.getTime(),dateFormate));
            min.add(Calendar.MONTH, 1);
        }
        result.add(maxDate);
        return result;
    }
    
}
