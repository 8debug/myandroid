package com.myutils;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class MyDataUtils {

    public static String toStr(Object obj){
        return obj==null?"":String.valueOf(obj);
    }

    public static Integer toInteger(String str){
        return !TextUtils.isEmpty(str) && isNumber(str)? Integer.parseInt(str): null;
    }

    public static Double toDouble(String str){
        return !TextUtils.isEmpty(str) && isNumber(str)? Double.parseDouble(str): null;
    }

    public static Float toFloat(String str){
        return !TextUtils.isEmpty(str) && isNumber(str)? Float.parseFloat(str): null;
    }

    public static boolean isMobileValid(String mobile) {
        return Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$").matcher(mobile).matches();
    }

    public static boolean isNotMobileValid(String mobile) {
        return !isMobileValid(mobile);
    }

    public static boolean isNumber(String str) {
        return Pattern.compile("-?[0-9]+(\\.[0-9]+)?").matcher(str).matches();
    }

}
