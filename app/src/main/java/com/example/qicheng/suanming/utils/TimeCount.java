package com.example.qicheng.suanming.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by asus on 2017/6/12.
 * 验证码计时器
 */

public class TimeCount extends CountDownTimer {

    private TextView textview = null;

    public void setTextview(TextView textview) {
        this.textview = textview;
    }

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        if (textview != null) {
            textview.setText("重新获取");
            textview.setClickable(true);
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示

        if (textview != null) {
            textview.setClickable(false);
            textview.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
