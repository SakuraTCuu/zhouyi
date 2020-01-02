package com.example.zhouyi.ui;

import android.app.Application;

import com.example.zhouyi.common.Constants;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
