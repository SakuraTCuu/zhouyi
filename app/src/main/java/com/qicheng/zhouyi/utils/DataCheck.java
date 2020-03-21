package com.qicheng.zhouyi.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataCheck {

    /**
     * 过滤emoji表情
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("*");
                return source;
            }
            return source;
        }
        return source;
    }

    //循环判断
    public static boolean isHanzi(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!isHanzi2(String.valueOf(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    //  只能判断开头一个汉字
    public static boolean isHanzi2(String str) {
        String strPattern = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    //判断是否是0-9 10位数
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean ispass(String pass) {
        Pattern pattern = Pattern.compile("[\\da-zA-Z]{6,16}");
        Matcher matcher = pattern.matcher(pass);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * 电话号码中间部分变*号
     * */
    public static String numberModify(String number) {

        String modify = null;
        if (!TextUtils.isEmpty(number)) {
            if (number.length() >= 3) {
                modify = number.substring(0, 3);
                modify = modify + "****";
            }

            if (number.length() >= 4) {
                modify = modify + number.substring(number.length() - 4, number.length());
            }
        }
        return modify;
    }

    /*
     * 判断密码是否大于6长度小于20
     * */

    public static Boolean NumberlenLength(String number) {

        int size = number.length();
        if (size >= 6 && size <= 20) {

            return false;
        } else {
            return true;
        }
    }

    //去除首位双引号
    public static String RemoveQuotes(String money) {

        if (money != null) {
            money = money.replaceAll("\"", "");
        }
        return money;
    }

    /*
     * 去除姓
     * */
    public static String RemoveSurnames(String name) {

        if (!TextUtils.isEmpty(name)) {
            name = name.substring(1, name.length());
            name = "*" + name;
        }
        return name;
    }

    /*
     * 只保留身份证首末数
     * */
    public static String RemoveBacnkCard(String name) {

        if (!TextUtils.isEmpty(name)) {

            int length = name.length();
            if (length > 7) {
                name = name.substring(0, 4) + "***" + name.substring(length - 3, length);
            }
        }
        return name;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode(Context mContext) {
        PackageManager manager = mContext.getPackageManager();//获取包管理器
        try {
            //通过当前的包名获取包的信息
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);//获取包对象信息
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取App的名称
     *
     * @param context 上下文
     * @return 名称
     */
    public static String getAppName(Context context) {
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //获取应用 信息
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            //获取albelRes
            int labelRes = applicationInfo.labelRes;
            //返回App的名称
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
