package com.qicheng.zhouyi.base;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.common.ActivityManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    private FrameLayout fl_content;
    private RelativeLayout rl_title_bar;
    private TextView text_title;
    private ImageView img_exit;

    protected Context mContext;
    protected Bundle mSavedInstanceState;       //防止出现Fragment重叠时使用

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //添加一个title bar
        // 隐藏状态栏  标题栏
        this.hideActionBar();
        this.setFullScreen();

        setContentView(R.layout.activity_base);
        //绑定View控件
        fl_content = findViewById(R.id.fl_content);
        rl_title_bar = findViewById(R.id.rl_title_bar);
        text_title = findViewById(R.id.tv_title_text);
        img_exit = findViewById(R.id.iv_title_exit);

        img_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityManager.getInstance().pop();
                finish();
            }
        });

        View view = getLayoutInflater().inflate(setLayoutId(), null);
        fl_content.addView(view);

        ButterKnife.bind(this);

        mContext = this;
        this.mSavedInstanceState = savedInstanceState;

        //初始化View控件
        initView();
        //初始化数据
        initData();
        //设置监听
        setListener();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    protected void setTitleText(String str) {
        if (!isShowBar()) {
            showTitleBar();
        }
        text_title.setText(str);
    }

    /**
     * 隐藏整个title
     */
    protected void hideTitleBar() {
        if (rl_title_bar.getVisibility() == View.VISIBLE) {
            rl_title_bar.setVisibility(View.GONE);
        }
    }

    protected boolean isShowBar() {
        return rl_title_bar.getVisibility() == View.VISIBLE;
    }

    /**
     * 展示整个title
     */
    protected void showTitleBar() {
        if (rl_title_bar.getVisibility() != View.VISIBLE) {
            rl_title_bar.setVisibility(View.VISIBLE);
        }
    }

    //隐藏 退出 img
    protected void hideExitImg() {
        img_exit.setVisibility(View.INVISIBLE);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected abstract int setLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();
}
