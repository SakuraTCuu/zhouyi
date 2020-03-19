package com.qicheng.zhouyi.wxapi;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;


public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    public static final String APP_ID = "wx10f0e8af9e8031c3";
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wxentry);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
//        getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        api = WXAPIFactory.createWXAPI(this, "wx10f0e8af9e8031c3", false);
        api.handleIntent(getIntent(), this);
    }
    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
                //获取accesstoken
//                getAccessToken(code);
                getDataFromServer(code);
                Log.d("code--->>", code.toString()+ "");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    private void getDataFromServer(String code){
        Map<String,String> map = new HashMap<>();
        map.put("code",code);

        OkHttpManager.request(Constants.getApi.WXLOGIN, RequestType.POST, map,new OkHttpManager.RequestListener(){

            @Override
            public void Success(HttpInfo info) {
                String userInfo = info.getRetDetail();
                Log.d("userInfo-->",userInfo);
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("userInfo-->",info.getRetDetail());
            }
        });
    }
}
