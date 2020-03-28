package com.example.qicheng.suanming.ui.jiemeng;

import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;

import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.R;

import butterknife.BindView;

/**
 * Created by Sakura on 2020-03-21 10:57
 */
public class JiemengDetailActivity extends BaseActivity {

//    @BindView(R.id.tv_jieming_detail)
//    TextView tv_jieming_detail;

    @BindView(R.id.webview_jiemeng)
    WebView webview_jiemeng;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_jiemeng_detail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        String bigText = intent.getStringExtra("bigText");
        if (bigText.length() > 10) {
            bigText = bigText.substring(0, 10) + "...";
        }
        setTitleText(bigText);

        Log.d("text-->>", text);
//        tv_jieming_detail.setText(Html.fromHtml(text));
        webview_jiemeng.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);
    }

    private void setData() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
