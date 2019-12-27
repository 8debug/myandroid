package com.myutils;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

public class MyCrashUtils {

    public static void init(Context context, String appId){
        MyBugly.init(context, appId);
    }

    public static void checkUpgrade(){
        Beta.checkUpgrade(false,false);
    }

    /**
     * 收集指定用户的异常信息
     * @param id
     */
    public static void setUserId(String id){
        MyBugly.setUserId(id);
    }

    /*static class Pgy{
        private static void initCrash(){
            PgyCrashManager.register();
        }
    }*/

    static class MyBugly{
        private static void init(Context context, String appId){
            Bugly.init(context, appId, BuildConfig.DEBUG);
        }
        private static void initCrashReport(Context context, String appId){
            // bugly
            context = context.getApplicationContext();
            // 获取当前包名
            String packageName = context.getPackageName();
            // 获取当前进程名
            String processName = MyAppUtils.getProcessName(android.os.Process.myPid());
            // 设置是否为上报进程
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
            strategy.setAppChannel("myChannel");  //设置渠道
            strategy.setAppVersion(MyAppUtils.getName(context)+ BuildConfig.VERSION_NAME);      //App的版本
            strategy.setAppPackageName(packageName);  //App的包名
            strategy.setUploadProcess(processName == null || processName.equals(packageName));
            CrashReport.initCrashReport(context, appId, BuildConfig.DEBUG);
            // 设置当前设备是否为开发设备
            CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG);
        }

        public static void setUserId(String id){
            if(!TextUtils.isEmpty(id)){
                CrashReport.setUserId(id);
            }
        }
    }

}
