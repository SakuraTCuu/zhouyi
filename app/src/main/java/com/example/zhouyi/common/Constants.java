package com.example.zhouyi.common;

import com.example.zhouyi.ui.MyApplication;

import utils.SPUtils;

public class Constants {

    public static final String Filename = "zhouyi";       //shaperpager保存文件

    public static String getUid() {
        return (String) SPUtils.get(MyApplication.getInstance(), "uid", "");
    }

    public static String getUser() {
        return (String) SPUtils.get(MyApplication.getInstance(), "user", "");
    }

    public static class getApi{
        public static final String URL = "http://.cn/";
    }
}
