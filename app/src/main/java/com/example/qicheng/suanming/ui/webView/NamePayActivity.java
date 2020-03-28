package com.example.qicheng.suanming.ui.webView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class NamePayActivity extends BaseActivity {

    //    @BindView(R.id.webview_order)
//    WebView webview_order;
    private List<String> urlList = new ArrayList<>(); // 记录访问的URL
    private Map<String, String> headers = new HashMap<>();
    @BindView(R.id.fl_view)
    FrameLayout fl_view;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_name_order;
    }

    @Override
    protected void initView() {
        hideTitleBar();
        Bundle bundle = this.getIntent().getExtras(); //读取intent的数据给bundle对象
        String url = bundle.getString("url"); //通过key得到value
//        headers.put("Referer", "http://app.zhouyi999.cn");
        addWeb(url);
    }

    public void addWeb(String url) {
        WebView webview_order = new WebView(NamePayActivity.this);
        fl_view.addView(webview_order);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        webview_order.setLayoutParams(params);

        webview_order.setWebViewClient(new MyWebViewClient());
        webview_order.setWebChromeClient(new WebChromeClient());
        headers.put("Referer", "http://app.zhouyi999.cn");
        webview_order.loadUrl(url, headers);
        WebSettings settings = webview_order.getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //开启本地DOM存储
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 是否可访问Content Provider的资源，默认值 true
        settings.setAllowContentAccess(true);
        // 是否可访问本地文件，默认值 true
        settings.setAllowFileAccess(true);
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        settings.setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    private boolean flag = false;

    class MyWebViewClient extends WebViewClient {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("onPageStarted-->>", url);
            if (url.startsWith("https://wx.tenpay.com")) {
                flag = true;
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url == null) return false;
            //?pay_status=1
            if (url.startsWith("js://webview")) {
                //大小吉名支付成功;
                //返回到选名界面,然后
                Log.d("url--->>", url);
                String status = Uri.parse(url).getQueryParameter("back_status");
                Intent intent = new Intent();
                if (status.equals("1")) {
                    intent.putExtra("status", 1);
                } else {
                    intent.putExtra("status", -1);
                }
                setResult(RESULT_OK, intent);
                finish();
                return false;
            }
            try {
                if (url.startsWith("weixin://") || url.startsWith("alipays://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else {
                    if (!urlList.contains(url)) {
                        if (!url.startsWith("http://app.zhouyi999.cn/index/order")) {
                            addWeb(url);
                        } else {
                            //如果支付成功就不重定向了直接刷新;
                            Log.d("ordder-->", "order");
                            view.loadUrl(url);
                            return true;
                        }
                        urlList.add(url);
                        return true;
                    }
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return false;
            }
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                if(request.getUrl().toString().contains("cordova.js")){//加载指定.js时 引导服务端加载本地Assets/www文件夹下的cordova.js
//                    try {
//                        return new WebResourceResponse("application/x-javascript","utf-8",getBaseContext().getAssets().open("www/cordova.js"));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
            return super.shouldInterceptRequest(view, request);
        }
    }

    @Override
    protected void onResume() {
        Log.d("onResume", "onResume");
        super.onResume();
        if (flag) {
            flag = false;
            Log.d("remove--->", "removeView");
            //保留第一个webview
            fl_view.removeViewAt(1);
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("urllist-->>", urlList.toString());
        int childCount = fl_view.getChildCount();
        Log.d("childCount-->>", childCount + "");
        if (childCount > 1) {
            fl_view.removeViewAt(childCount - 1);
            urlList.remove(urlList.size() - 1);
        } else {
            super.onBackPressed();
        }
    }
}
