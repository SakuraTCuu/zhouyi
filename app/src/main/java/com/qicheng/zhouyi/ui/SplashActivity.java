package com.qicheng.zhouyi.ui;

import android.content.Intent;
import android.os.Handler;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.common.ActivityManager;
import com.qicheng.zhouyi.ui.bazi.BaziHehunActivity;

public class SplashActivity extends BaseActivity {
    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        ActivityManager.getInstance().push(this);

        hideTitleBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, BaziHehunActivity.class));
                ActivityManager.getInstance().finishActivity(SplashActivity.this);
            }
        }, 1000);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {

    }
}
