package com.myutils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyDateUtils {

    public final static String FORM_NORMAL = "yyyy-MM-dd";
    // TODO 注意格式，不然时间会有差异 yyyy-MM-dd HH:mm:ss
    public final static String FORM_NORMAL_FULL = "yyyy-MM-dd HH:mm:ss";

    public static Date getDate(String date ){
        return getDate(date, FORM_NORMAL);
    }

    public static Date getDate(String date, String format ){
        Date d = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.CHINA);
            d = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String getStrDate(Long date ){
        return getStrDate( date, FORM_NORMAL_FULL );
    }

    public static String getStrDate(Long date, String format ){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(date);
        return getStrDate(calendar.getTime(), format);
    }

    public static String getStrDate(Date date ){
        return getStrDate(date, FORM_NORMAL);
    }

    public static String getStrDate(Date date, String format ){
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(date);
    }

}
