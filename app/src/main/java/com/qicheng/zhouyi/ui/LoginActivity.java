package com.qicheng.zhouyi.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.annotation.RequestType;
import com.okhttplib.callback.Callback;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.UserModel;
import com.qicheng.zhouyi.common.ActivityManager;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.qicheng.zhouyi.utils.DataCheck;
import com.qicheng.zhouyi.utils.TimeCount;
import com.qicheng.zhouyi.utils.ToastUtils;
import com.qicheng.zhouyi.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.edit_text_phone)
    EditText edit_text_phone;

    @BindView(R.id.edit_text_code)
    EditText edit_text_code;

    @BindView(R.id.text_login_getcode)
    TextView text_login_getcode;

    private String input_phone;
    private String code; //验证码
    private TimeCount time;                     // 定时器
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String APP_ID = "wx10f0e8af9e8031c3";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        ActivityManager.getInstance().push(this);
        hideTitleBar();
        time = new TimeCount(60000, 1000);
        initWX();
    }

    public void initWX() {
// 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        api.registerApp(APP_ID);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    public void startActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        ActivityManager.getInstance().finishActivity(this);
    }

    @OnClick({R.id.btn_code_login, R.id.ll_sylogin, R.id.ll_wxlogin, R.id.text_login_getcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_login_getcode:
                clickGetCode();
                break;
            case R.id.btn_code_login:
                codeLogin();
                break;
            case R.id.ll_sylogin:
                syLogin();
                break;
            case R.id.ll_wxlogin:
                wxLogin();
                break;
        }
    }

    /**
     * 获取验证码
     */
    public void clickGetCode() {

        //TODO 设置倒计时
        input_phone = edit_text_phone.getText().toString().trim();
        if (!DataCheck.isCellphone(input_phone)) {
            ToastUtils.showShortToast("请输入正确的手机号");
            return;
        }
        time.setTextview(text_login_getcode);
        time.start();
        //获取验证码
        doGetCodeHttp();
    }

    /**
     * 请求验证码
     */
    public void doGetCodeHttp() {
        Map map = new HashMap<String, String>();
        map.put("phone", input_phone);

        OkHttpManager.request(Constants.getApi.GETCODE, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("Success-->>", info.toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(info.getRetDetail());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("Fail-->>", info.toString());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(info.getRetDetail());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 验证码一键登录
     */
    public void codeLogin() {
        // debug测试
//        if (true) {
//            startActivity();
//            finish();
//            return;
//        }
        input_phone = edit_text_phone.getEditableText().toString().trim();
        code = edit_text_code.getEditableText().toString().trim();

        if (!DataCheck.isCellphone(input_phone)) {
            ToastUtils.showShortToast("请输入正确的手机号");
        } else if (TextUtils.isEmpty(code)) {
            ToastUtils.showShortToast("请输入验证码!");
        } else {
            doLoginokhttp();
        }
    }

    /**
     * 微信一键登录
     */
    public void wxLogin() {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(LoginActivity.this, "您的设备未安装微信客户端", Toast.LENGTH_SHORT).show();
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
        }
    }

    /**
     * 闪验一键登录
     */
    public void syLogin() {
        //授权页配置
//        OneKeyLoginManager.getInstance().setAuthThemeConfig(ConfigUtils.getDialogUiConfig(getApplicationContext()), ConfigUtils.getLandscapeUiConfig(getApplicationContext()));
        //拉授权页
        OneKeyLoginManager.getInstance().openLoginAuth(true, new OpenLoginAuthListener() {
            @Override
            public void getOpenLoginAuthStatus(int code, String result) {
                if (1000 == code) {
                    //拉起授权页成功
                    Log.e("shanyanLogin--->>", "拉起授权页成功： _code==" + code + "   _result==" + result);
                } else {
                    //拉起授权页失败
                    Log.e("shanyanLogin--->>", "拉起授权页失败： _code==" + code + "   _result==" + result);
                    ToastUtils.showShortToast(result);
                }
            }
        }, new OneKeyLoginListener() {
            @Override
            public void getOneKeyLoginStatus(int code, String result) {
                if (1011 == code) {
                    Log.d("VVV", "用户点击授权页返回： _code==" + code + "   _result==" + result);
                    return;
                } else if (1000 == code) {
                    Log.d("VVV", "用户点击登录获取token成功： _code==" + code + "   _result==" + result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String token = (String) jsonObject.get("token");
                        Log.e("VVV", "token-->>" + token);
                        doSYLoginokhttp(token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("VVV", "用户点击登录获取token失败： _code==" + code + "   _result==" + result);
                }
            }
        });
    }

    /**
     * 请求服务器 验证码登录
     */
    public void doLoginokhttp() {
        Map map = new HashMap<String, String>();
        map.put("phone", input_phone);
        map.put("code", code);

        OkHttpManager.request(Constants.getApi.CODELOGIN, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                try {
                    JSONObject jsonData = new JSONObject(info.getRetDetail());
                    boolean code = jsonData.getBoolean("code");
                    if (code) {
                        JSONObject userData1 = jsonData.getJSONObject("data");
                        JSONObject userData = userData1.getJSONObject("user_info");
                        String user_id = userData.getString("user_id");
                        String head_img = userData.getString("head_img");
                        String nick_name = userData.getString("nick_name");
                        String gender = userData.getString("gender");
                        // String job =  userData.getString("job");
                        String phone = userData.getString("phone");
                        UserModel uModel = new UserModel(user_id, head_img, nick_name, gender);
                        Constants.userInfo = uModel;
                        Constants.saveData();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        String msg = jsonData.getString("msg");
                        ToastUtils.showShortToast(msg);
                        //返回登录页
//                        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("未知错误!");
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(info.getRetDetail());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("jsonObject---->>", jsonObject.toString());
            }
        });
    }

    /**
     * 请求服务器 验证码登录
     */
    public void doSYLoginokhttp(String token) {
        Map map = new HashMap<String, String>();
        map.put("token", token);
        map.put("app_type", "0"); //0 表示android

        OkHttpManager.request2(Constants.getApi.SYLOGIN, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("jsonObject---->>", "Success");
                try {
                    JSONObject jsonData = new JSONObject(info.getRetDetail());
                    boolean code = jsonData.getBoolean("code");
                    if (code) {
                        JSONObject userData = jsonData.getJSONObject("data").getJSONObject("user_info");
                        String user_id = userData.getString("user_id");
                        String head_img = userData.getString("head_img");
                        String nick_name = userData.getString("nick_name");
                        String gender = userData.getString("gender");
                        // String job =  userData.getString("job");
                        String phone = userData.getString("phone");
                        UserModel uModel = new UserModel(user_id, head_img, nick_name, gender);
                        Constants.userInfo = uModel;
                        Constants.saveData();
                        startActivity();
                    } else {
                        String msg = jsonData.getString("msg");
                        ToastUtils.showShortToast(msg);
                        //返回登录页
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("未知错误！");
                }
//                finish();
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("userInfo-->", info.getRetDetail());
                try {
                    JSONObject jsonData = new JSONObject(info.getRetDetail());
                    String msg = jsonData.getString("msg");
                    ToastUtils.showShortToast("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
