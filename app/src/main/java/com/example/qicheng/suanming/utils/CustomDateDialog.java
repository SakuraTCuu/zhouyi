package com.example.qicheng.suanming.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.example.qicheng.suanming.R;


//interface onClickNLListener {
//    public void onClickNL();
//}

/**
 * Created by Sakura on 2020-04-18 10:55
 */
public class CustomDateDialog extends Dialog implements OnChangeLisener {

    private FrameLayout wheelLayout;
    private TextView cancel;
    private TextView sure;
    private TextView messgeTv;
    private Button btn_nl;
    private Button btn_gl;

    private String format;
    //开始时间
    private Date startDate = new Date();
    //年分限制，默认上下5年
    private int yearLimt = 5;

    private boolean flag = true;

    private OnChangeLisener onChangeLisener;

    private OnCustomSureLisener onSureLisener;

    private CustomDatePicker mDatePicker;

    //设置选择日期显示格式，设置显示message,不设置不显示message
    public void setMessageFormat(String format) {
        this.format = format;
    }

    //设置开始时间
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }

    //设置年份限制，上下年份
    public void setYearLimt(int yearLimt) {
        this.yearLimt = yearLimt;
    }

    //设置选择回调
    public void setOnChangeLisener(OnChangeLisener onChangeLisener) {
        this.onChangeLisener = onChangeLisener;
    }

    public interface OnCustomSureLisener {
        void onSure(Date date, boolean flag);
    }

    //设置点击确定按钮，回调
    public void setOnSureLisener(OnCustomSureLisener onSureLisener) {
        this.onSureLisener = onSureLisener;
    }

    public CustomDateDialog(Context context) {
        super(context, R.style.dialog_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_datepicker);

        initView();
        initParas();
    }

    private CustomDatePicker getDatePicker() {
        CustomDatePicker picker = new CustomDatePicker(getContext());
        picker.setStartDate(startDate);
        picker.setYearLimt(yearLimt);
        picker.setOnChangeLisener(this);
        picker.init();
        return picker;
    }

    private void initView() {
        this.sure = (TextView) findViewById(R.id.sure);
        this.cancel = (TextView) findViewById(R.id.cancel);
        this.wheelLayout = (FrameLayout) findViewById(R.id.wheelLayout);
        this.btn_nl = (Button) findViewById(R.id.btn_click_nl);
        this.btn_gl = (Button) findViewById(R.id.btn_click_gl);
        messgeTv = (TextView) findViewById(R.id.tv_top_date);

        mDatePicker = getDatePicker();
        this.wheelLayout.addView(mDatePicker);

        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        this.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onSureLisener != null) {
                    onSureLisener.onSure(mDatePicker.getSelectDate(), flag);
                }
            }
        });

        this.btn_gl.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                flag = true;
                mDatePicker.onClickGL();
                btn_gl.setBackgroundColor(Color.parseColor("#395C74"));
                btn_nl.setBackgroundColor(Color.parseColor("#D7E6EF"));
//                btn_gl.setTextColor(R.color.white);
//                btn_nl.setTextColor(R.color.black);
                btn_nl.setTextColor(getContext().getColor(R.color.black));
                btn_gl.setTextColor(getContext().getColor(R.color.white));
            }
        });

        this.btn_nl.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                flag = false;
                mDatePicker.onClickNL();
                btn_gl.setBackgroundColor(Color.parseColor("#D7E6EF"));
                btn_nl.setBackgroundColor(Color.parseColor("#395C74"));
                btn_gl.setTextColor(getContext().getColor(R.color.black));
                btn_nl.setTextColor(getContext().getColor(R.color.white));
//                ToastUtils.showShortToast("农历");
            }
        });
    }

    private void initParas() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = DateUtils.getScreenWidth(getContext());
        getWindow().setAttributes(params);
    }

    @Override
    public void onChanged(Date date) {

        if (onChangeLisener != null) {
            onChangeLisener.onChanged(date);
        }

        if (!TextUtils.isEmpty(format)) {
            String messge = "";
            try {
                messge = new SimpleDateFormat(format).format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
            messgeTv.setText(messge);
        }
    }
}
