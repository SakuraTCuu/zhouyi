package com.example.qicheng.suanming.ui;

import android.content.Intent;
import android.os.Handler;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.common.ActivityManager;
import com.example.qicheng.suanming.common.Constants;

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
//                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                //查看状态
                if(Constants.isLogin){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
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
