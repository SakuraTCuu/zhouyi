package com.qicheng.zhouyi.ui;

import android.content.Intent;
import android.os.Handler;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.common.ActivityManager;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.ui.bazi.BaziHehunActivity;
import com.qicheng.zhouyi.ui.caiyun.CaiyunActivity;
import com.qicheng.zhouyi.ui.mouseYear.MouseYearActivity;
import com.qicheng.zhouyi.ui.yuelao.YuelaoActivity;

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
