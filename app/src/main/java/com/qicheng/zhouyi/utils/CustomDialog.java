package com.qicheng.zhouyi.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qicheng.zhouyi.R;

/**
 * Created by Sakura on 2020-03-24 13:39
 */
public abstract class CustomDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private LinearLayout btnPickBySelect, btnPickByTake;

    public CustomDialog(Activity activity) {
        super(activity, R.style.CustomDialogTheme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);

        btnPickBySelect = (LinearLayout) findViewById(R.id.ll_share_wx);
        btnPickByTake = (LinearLayout) findViewById(R.id.ll_share_pyq);

        btnPickBySelect.setOnClickListener(this);
        btnPickByTake.setOnClickListener(this);

        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_share_wx:
                clickWx();
                this.cancel();
                break;
            case R.id.ll_share_pyq:
                clickPyq();
                this.cancel();
                break;
        }
    }

    public abstract void clickWx();

    public abstract void clickPyq();

}
