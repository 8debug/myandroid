package com.myutils;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class MyDataUtils {

    public static String toStr(Object obj){
        return obj==null?"":String.valueOf(obj);
    }

    public static <T> T toNumber(Object obj){
        if( obj==null ){
            return null;
        }
        String str = obj.toString();
        return !TextUtils.isEmpty(str) && isNumber(str)? (T)str: null;
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
