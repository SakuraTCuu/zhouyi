package com.qicheng.zhouyi.ui.jiemeng;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.library.flowlayout.FlowAdapter;
import com.library.flowlayout.FlowLayout;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.JieMengBean;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.qicheng.zhouyi.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

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
