package com.rehoshi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public enum Unit {
        SECOND,
        MINUTE,
        HOUR,
        DAY,
        MONTH,
        YEAR,
    }

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static SimpleDateFormat dateFormat;

    private static SimpleDateFormat getDateFormat() {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(DATE_FORMAT);
        }
        return dateFormat;
    }

    public static String formatDate(Date date) {
        return formatDate(DATE_FORMAT, date);
    }

    public static String formatDateTime(Date date) {
        return formatDate(DATETIME_FORMAT, date);
    }

    public static String formatDate(String pattern, Date date) {
        getDateFormat().applyPattern(pattern);
        return date == null ? "" : getDateFormat().format(date);
    }

    /**
     * 获取时间到当前时间的偏移量
     * 当前时间 - 传入时间
     *
     * @param date
     * @return
     */
    public static long getDiff(Date date) {
        if (date == null) {
            return 0;
        }
        return System.currentTimeMillis() - date.getTime();
    }

    public static boolean isAfterNow(Date date){
        return getDiff(date) < 0 ;
    }

    public static Date addTime(Date date, double time, Unit unit) {
        if (date != null) {
            long addTime = date.getTime();
            switch (unit) {
                case YEAR:
                    time *= 365;
                case MONTH:
                    time *= 30;
                case DAY:
                    time *= 24;
                case HOUR:
                    time *= 60;
                case MINUTE:
                    time *= 60;
                case SECOND:
                    time *= 1000;
                    break;
            }

            return new Date((long) (addTime + time));
        }
        return null;
    }

    public static Date toDate(String dateStr) {
        return toDate(DATE_FORMAT, dateStr);
    }

    public static Date toDate(String pattern, String dateStr) {
        Date date = null;
        if (dateStr != null && !dateStr.trim().equals("")) {
            try {
                dateFormat.applyPattern(pattern);
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date toDateTime(String dateStr) {
        return toDate(DATETIME_FORMAT, dateStr);
    }

    public static Date today() {
        return toDate(formatDate(new Date()));
    }

    public static Date todayEnd() {
        return endOfDay(today());
    }

    public static Date tomorrow() {
        return addTime(today(), 1, Unit.DAY);
    }

    public static Date endOfDay(Date date){
        return toDate("yyyy-MM-dd HH:mm:ss:SSS",formatDate("yyyy-MM-dd 23:59:59:999", date)) ;
    }
}

