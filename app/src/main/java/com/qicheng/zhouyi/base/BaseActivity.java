package com.qicheng.zhouyi.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected Bundle mSavedInstanceState;       //防止出现Fragment重叠时使用

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏  标题栏
        this.hideActionBar();
        this.setFullScreen();
        setContentView(setLayoutId());

        //绑定View控件
        ButterKnife.bind(this);

        mContext = this;
        this.mSavedInstanceState = savedInstanceState;

        //初始化View控件
        initView();
        //初始化数据
        initData();
        //设置监听
        setListener();
        setStatusBar();
    }

    /**
     * hide action bar
     */
    private void hideActionBar() {
        // Hide UI
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * set the activity display in full screen
     */
    private void setFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected abstract int setLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

    protected void setStatusBar() {
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_zhuce));

//        StatusBarUtil.setTranslucent(this);

//        String ss =  SystemUtils.getSystemVersion();
//        String i =  ss.substring(0,1);
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_zhuce));
//        StatusBarUtil.setTranslucent(this,50);
//        if(Integer.valueOf(i)>6){
//            StatusBarUtil.setTranslucent(this, 0);
//
////            StatusUtils.setLightStatusBar(this,true);
//        }else {
//
//            StatusBarUtil.setTranslucent(this, 50);
//        }
    }

    protected void setStatusBar2() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//隐藏状态栏但不隐藏状态栏字体
        }

    }
}
