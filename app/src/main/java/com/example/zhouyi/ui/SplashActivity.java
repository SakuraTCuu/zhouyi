package com.example.zhouyi.ui;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.example.zhouyi.R;
import com.example.zhouyi.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        },1000);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
