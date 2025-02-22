package com.example.qicheng.suanming.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sakura on 2020-04-18 10:42
 */
public class PickerDateHelper {

    //开始年
    private int YEAR_START;
    //开始月
    private int MONTH_START;
    //开始天
    private int DAY_START;
    //开始周
    private int WEEK_START;
    //开始小时
    private int HOUR_START;
    //开始分钟
    private int MINUTE_START;
    //开始时间
    private Date startDate = new Date();
    //年份限制，上下5年
    private int yearLimt = 5;

    private ArrayList<Integer> tem = new ArrayList<>();
    private ArrayList<String> dispalyTem = new ArrayList<>();
    private String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};


    public enum Type {
        YEAR,
        MOTH,
        DAY,
        WEEK,
        HOUR,
        MINUTE
    }

    public PickerDateHelper() {
        init();
    }

    private void init() {
        Date date = startDate;
        //获取年 月 日 时 分
        YEAR_START = DateUtils.getYear(date);
        MONTH_START = DateUtils.getMoth(date);
        DAY_START = DateUtils.getDay(date);
        WEEK_START = DateUtils.getWeek(date);
        HOUR_START = DateUtils.getHour(date);
        MINUTE_START = DateUtils.getMinute(date);
    }


    //设置初始化时间
    public void setStartDate(Date date, int yearLimt) {

        this.startDate = date;
        this.yearLimt = yearLimt;

        if (this.startDate == null) {
            this.startDate = new Date();
        }
        init();
    }

    public int getToady(PickerDateHelper.Type type) {
        switch (type) {
            case YEAR:
                return YEAR_START;
            case MOTH:
                return MONTH_START;
            case DAY:
                return DAY_START;
            case WEEK:
                return WEEK_START;
            case HOUR:
                return HOUR_START;
            case MINUTE:
                return MINUTE_START;
        }
        return 0;
    }

    public String[] getDisplayValue(Integer[] arr, String per) {
        dispalyTem.clear();
        for (Integer i : arr) {
            String value = i < 10 ? ("0" + i) : "" + i;
            dispalyTem.add(value + per);
        }
        return dispalyTem.toArray(new String[0]);
    }

    public Integer[] genMonth() {
        return new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    }

    public Integer[] genHour() {
        return genArr(24, true);
    }

    public Integer[] genMinut() {
        return genArr(60, true);
    }

    public Integer[] genArr(int size, boolean isZero) {
        tem.clear();
        for (int i = isZero ? 0 : 1; i < (isZero ? size : size + 1); i++) {
            tem.add(i);
        }
        return tem.toArray(new Integer[0]);
    }

    //生成年
    public Integer[] genYear() {
        tem.clear();
        for (int i = YEAR_START - yearLimt; i < YEAR_START; i++) {
            tem.add(i);
        }
        tem.add(YEAR_START);

        for (int i = YEAR_START + 1; i < YEAR_START + yearLimt; i++) {
            tem.add(i);
        }
        return tem.toArray(new Integer[0]);
    }


    public Integer[] genDay(int year, int moth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, moth, 1);
        calendar.add(Calendar.DATE, -1);
        int day = Integer.parseInt(new SimpleDateFormat("d").format(calendar.getTime()));
        return genArr(day, false);
    }

    public Integer[] genDay() {
        return genDay(YEAR_START, MONTH_START);
    }

    public String[] getNlMonth() {
        // 农历月份
        String[] nlMonth = {"正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月"};
        return nlMonth;
    }

    public String[] getNlDay() {
        // 农历天分
        String[] nlDay2 = {"初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"};
        return nlDay2;
    }

    public String[] getNlHours() {
        String[] hours = {
                "早子时", "丑时", "丑时", "寅时", "寅时", "卯时", "卯时", "辰时", "辰时", "巳时", "巳时", "午时", "午时", "未时", "未时", "申时", "申时", "酉时", "酉时", "戌时", "戌时", "亥时", "亥时", "晚子时"};
        return hours;
    }

    public int findIndextByValue(int value, Integer[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (value == arr[i]) {
                return i;
            }
        }
        return -1;
    }

    public String getDisplayWeek(int year, int moth, int day) {
        return weeks[DateUtils.getWeek(year, moth, day) - 1];
    }

    public String getDisplayStartWeek() {
        return getDisplayWeek(YEAR_START, MONTH_START, DAY_START);
    }


    public static void main(String[] args) {
        System.out.println(Arrays.toString(new PickerDateHelper().genDay(2016, 2)));
    }
}

