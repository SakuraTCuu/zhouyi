package com.example.qicheng.suanming.ui.webView;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Sakura on 2020-03-30 14:03
 */
public class PrivacyActivty extends BaseActivity {

    @BindView(R.id.tv_privacy_detail)
    TextView tv_privacy_detail;

    @Override
    protected int setLayoutId() {
        return R.layout.privacy_activity;
    }

    @Override
    protected void initView() {

        hideTitleBar();
        Bundle bundle = this.getIntent().getExtras(); //读取intent的数据给bundle对象
        String text = bundle.getString("text"); //通过key得到value

        tv_privacy_detail.setText(text);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

}
