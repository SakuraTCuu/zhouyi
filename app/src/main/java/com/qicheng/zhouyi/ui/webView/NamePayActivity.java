package com.qicheng.zhouyi.ui.webView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.common.OkHttpManager;

import java.io.IOException;

import butterknife.BindView;

public class NamePayActivity extends BaseActivity {

    @BindView(R.id.webview_order)
    WebView webview_order;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_name_order;
    }

    @Override
    protected void initView() {
        hideTitleBar();
        //请求服务器设置网页
//        OkHttpManager.request();
        Bundle bundle = this.getIntent().getExtras(); //读取intent的数据给bundle对象
        String url = bundle.getString("url"); //通过key得到value
        webview_order.setWebViewClient(new MyWebViewClient());
        webview_order.getSettings().setJavaScriptEnabled(true);
        webview_order.loadUrl(url);
//        webview_order.postUrl(url,params);
//        webview_order.loadDataWithBaseURL(null, Html.fromHtml(htmlData)+"", "text/html","utf-8", null);
        WebSettings settings = webview_order.getSettings();
        settings .setRenderPriority(WebSettings.RenderPriority.HIGH);
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


    class MyWebViewClient extends WebViewClient {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//            if(url.contains("cordova.js")){//加载指定.js时 引导服务端加载本地Assets/www文件夹下的cordova.js
//                try {
//                    return new WebResourceResponse("application/x-javascript","utf-8",getBaseContext().getAssets().open("www/cordova.js"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // 如下方案可在非微信内部WebView的H5页面中调出微信支付

        if (url.startsWith("weixin://wap/pay?")) {
                 Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
          }
           return super.shouldOverrideUrlLoading(view, url);
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
}
