package com.examples;

import android.app.Application;

import com.myutils.MyApplication;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.init(this, "");
    }
}
