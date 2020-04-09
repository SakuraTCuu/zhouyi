package com.example.qicheng.suanming.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.bean.UserModel;
import com.example.qicheng.suanming.common.ActivityManager;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.ui.LoginActivity;
import com.example.qicheng.suanming.ui.MainActivity;
import com.example.qicheng.suanming.ui.MyApplication;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    public static final String APP_ID = "wx10f0e8af9e8031c3";
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
//        getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        api = MyApplication.getInstance().getWxApi();
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {
        finish();
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//                goToGetMsg();
                finish();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//                goToShowMsg((ShowMessageFromWX.Req) req);
                finish();
                break;
            default:
                break;
        }
        finish();
    }

    public int WX_LOGIN = 1;

    @Override
    public void onResp(BaseResp baseResp) {
        //登录回调

        if (baseResp.getType() == WX_LOGIN) {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = ((SendAuth.Resp) baseResp).code;
                    getDataFromServer(code);
                    Log.d("code--->>", code.toString() + "");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    Toast.makeText(WXEntryActivity.this, "拒绝授权!", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    break;
                default:
                    break;
            }
        } else {
            //分享成功回调
            System.out.println("------------分享回调------------");
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //分享成功
//                    Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //分享取消
//                    Toast.makeText(WXEntryActivity.this, "分享取消", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //分享拒绝
//                    Toast.makeText(WXEntryActivity.this, "分享拒绝", Toast.LENGTH_LONG).show();
                    break;
            }
        }
        finish();
    }

    private void getDataFromServer(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);

        OkHttpManager.request2(Constants.getApi.WXLOGIN, RequestType.POST, map, new OkHttpManager.RequestListener() {

            @Override
            public void Success(HttpInfo info) {
//                String userInfo = info.getRetDetail();
                Log.d("userInfo-->", info.getRetDetail());
                //解析userInfo;
                try {
                    JSONObject jsonData = new JSONObject(info.getRetDetail());
                    boolean code = jsonData.getBoolean("code");
                    if (code) {
                        JSONObject userData = jsonData.getJSONObject("data");
                        String user_id = userData.getString("user_id");
                        String head_img = userData.getString("head_img");
                        String nick_name = userData.getString("nick_name");
                        String gender = userData.getString("gender");
                        // String job =  userData.getString("job");
                        String phone = userData.getString("phone");
                        UserModel uModel = new UserModel(user_id, head_img, nick_name, gender);
                        Constants.userInfo = uModel;
                        Constants.saveData();
                        startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
                        ActivityManager.getInstance().finishActivity(LoginActivity.class);
                        finish();
                    } else {
                        String msg = jsonData.getString("msg");
                        ToastUtils.showShortToast(msg);
                        //返回登录页
                        startActivity(new Intent(WXEntryActivity.this, LoginActivity.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("未知错误！");
                    //返回登录页
                    startActivity(new Intent(WXEntryActivity.this, LoginActivity.class));
                    finish();
                }
                finish();
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("userInfo-->", info.getRetDetail());
                try {
                    JSONObject jsonData = new JSONObject(info.getRetDetail());
                    String msg = jsonData.getString("msg");
                    ToastUtils.showShortToast("msg");
                    //返回登录页
                    startActivity(new Intent(WXEntryActivity.this, LoginActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
