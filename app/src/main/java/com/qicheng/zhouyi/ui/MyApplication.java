package com.qicheng.zhouyi.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.GetPhoneInfoListener;
import com.chuanglan.shanyan_sdk.listener.InitListener;
import com.qicheng.zhouyi.common.ActivityManager;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.utils.HttpInterceptor;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.annotation.CacheType;
import com.okhttplib.annotation.Encoding;
import com.okhttplib.cookie.PersistentCookieJar;
import com.okhttplib.cookie.cache.SetCookieCache;
import com.okhttplib.cookie.persistence.SharedPrefsCookiePersistor;
import com.qicheng.zhouyi.utils.LogUtils;
import com.qicheng.zhouyi.utils.SPUtils;
import com.qicheng.zhouyi.utils.ToastUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MyApplication extends Application {

    private static MyApplication instance;
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wx10f0e8af9e8031c3";
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    public void init() {
        initShanyan();
        initUser();
//        initWX();
    }

    /* 未登录 跳转登录界面*/
    public void startActivity() {
        Activity act = ActivityManager.getInstance().getCurrentActivity();
        startActivity(new Intent(act, LoginActivity.class));
    }

    /**
     * 初始化user
     */
    public void initUser() {
        //userid
        String userId = (String) SPUtils.get(instance, "uid", "");
        if (userId != "") {
            Constants.isLogin = true;
            Constants.userId = userId;
        }
    }

    /**
     * 闪验初始化
     */
    public void initShanyan() {
        //闪验SDK配置debug开关 （必须放在初始化之前，开启后可打印闪验SDK更加详细日志信息）
        OneKeyLoginManager.getInstance().setDebug(true);

        OneKeyLoginManager.getInstance().init(getApplicationContext(), Constants.shanyanAppId, new InitListener() {
            @Override
            public void getInitStatus(int code, String result) {
                Log.e("shanyanInit-->>", "初始化： code==" + code + "   result==" + result);
                if (code == 1022) {
                    Log.d("shanyanInit-->>", "闪验初始化成功");
                    //预取号
                    getPhoneInfo();
                } else {
                    Log.d("shanyanInit-->>", "闪验初始化失败" + result);
                    ToastUtils.showShortToast(result);
                }
            }
        });
    }


    public void getPhoneInfo() {
        OneKeyLoginManager.getInstance().getPhoneInfo(new GetPhoneInfoListener() {
            @Override
            public void getPhoneInfoStatus(int code, String result) {
                if (code == 1022) {
                    Log.d("getPhoneInfo-->>", "预取号成功");
                    //预取号
                    getPhoneInfo();
                } else {
                    Log.d("getPhoneInfo-->>", "预取号失败--" + result);
//                    ToastUtils.showShortToast(result);
                }
            }
        });
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /*初始化网路请求*/
    private void setData() {
        String downloadFileDir = Environment.getExternalStorageDirectory().getPath() + "/okHttp_download/";
//        String cacheDir = Environment.getExternalStorageDirectory().getPath()+"/okHttp_cache";
        OkHttpUtil.init(this)
                .setConnectTimeout(15)//连接超时时间
                .setWriteTimeout(15)//写超时时间
                .setReadTimeout(15)//读超时时间
                .setMaxCacheSize(10 * 1024 * 1024)//缓存空间大小
                .setCacheType(CacheType.FORCE_NETWORK)//缓存类型
                .setHttpLogTAG("HttpLog")//设置请求日志标识
                .setIsGzip(false)//Gzip压缩，需要服务端支持
                .setShowHttpLog(true)//显示请求日志
                .setShowLifecycleLog(true)//显示Activity销毁日志
                .setRetryOnConnectionFailure(false)//失败后不自动重连
//                .setCachedDir(new File(cacheDir))//设置缓存目录
                .setDownloadFileDir(downloadFileDir)//文件下载保存目录
                .setResponseEncoding(Encoding.UTF_8)//设置全局的服务器响应编码
                .setRequestEncoding(Encoding.UTF_8)//设置全局的请求参数编码
                .setHttpsCertificate("12306.cer")//设置全局Https证书
                .addResultInterceptor(HttpInterceptor.ResultInterceptor)//请求结果拦截器
                .addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this)))//持久化cookie
                .build();
    }

    public void initWX(){
// 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
    }
}
