package com.docker.common.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerUtils {

    public static Date parseTimeString2Date(String timeString) {
        if ((timeString == null) || (timeString.equals(""))) {
            return null;
        }
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = new Date(dateFormat.parse(timeString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertDate2String(Date date, String pattern) {
        if (date == null)
            return null;
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static int getYear(String timeString) {
        String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd HH:mm:ss");
        return Integer.parseInt(timeStr.substring(0, 4));
    }

    public static int getMonth(String timeString) {
        String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd HH:mm:ss");
        return Integer.parseInt(timeStr.substring(5, 7));
    }

    public static int getDay(String timeString) {
        String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd HH:mm:ss");
        return Integer.parseInt(timeStr.substring(8, 10));
    }

    public static int getHour(String timeString) {
        String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd HH:mm:ss");
        return Integer.parseInt(timeStr.substring(11, 13));
    }

    public static int getMinute(String timeString) {
        String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd HH:mm:ss");
        return Integer.parseInt(timeStr.substring(14, 16));
    }

    public static int getSecond(String timeString) {
        String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd HH:mm:ss");
        return Integer.parseInt(timeStr.substring(17, 19));
    }
}
