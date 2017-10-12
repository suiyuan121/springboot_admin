package com.fuwo.b3d.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jin.zhang@fuwo.com on 2017/8/25.
 */
public class DateUtils {


    private static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";


    public static Date getDate(String dateStr) {
        Date date = null;
        DateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    // 获得本周一0点时间
    public static Date getMondayOfWooek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        calendar.add(Calendar.DATE, -day_of_week + 1);
        return calendar.getTime();
    }

    // 获得本周日24点时间
    public static Date getSundayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        calendar.add(Calendar.DATE, -day_of_week + 7);
        return calendar.getTime();
    }
}
