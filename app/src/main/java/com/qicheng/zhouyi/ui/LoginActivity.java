package com.qicheng.zhouyi.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.annotation.RequestType;
import com.okhttplib.callback.Callback;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.qicheng.zhouyi.utils.DataCheck;
import com.qicheng.zhouyi.utils.TimeCount;
import com.qicheng.zhouyi.utils.ToastUtils;

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

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        hideTitleBar();
        time = new TimeCount(60000, 1000);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

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
        if (true) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

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
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(info.getRetDetail());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("jsonObject---->>", jsonObject.toString());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
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

        OkHttpManager.request(Constants.getApi.SYLOGIN, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("jsonObject---->>", "Success");
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(info.getRetDetail());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("jsonObject---->>", jsonObject.toString());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("jsonObject---->>", "Fail");
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

}
