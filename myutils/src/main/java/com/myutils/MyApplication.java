package com.myutils;

import android.content.Context;
import android.text.TextUtils;

public class MyApplication {

    private static Context appContext;

    public static Context getContext(){
        return appContext;
    }

    public static void setContext( Context context ){
        appContext = context.getApplicationContext();
    }

    public static void init( Context context, String appidBugly ){
        appContext = context.getApplicationContext();
        if( !TextUtils.isEmpty(appidBugly) ){
            MyCrashUtils.init(appContext, appidBugly);
        }
    }

}
