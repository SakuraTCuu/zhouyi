package com.example.qicheng.suanming.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.bean.UserModel;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.ui.webView.PrivacyActivty;
import com.example.qicheng.suanming.utils.DataCheck;
import com.example.qicheng.suanming.utils.TimeCount;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sakura on 2020-04-17 10:24
 */
public class BindPhoneActivity extends BaseActivity {

    @BindView(R.id.et_bind_phone)
    EditText et_bind_phone;

    @BindView(R.id.et_bind_code)
    EditText et_bind_code;

    @BindView(R.id.tv_btn_code)
    TextView tv_btn_code;


    private TimeCount time;  // 定时器

    private String input_phone;
    private String input_code;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_bindphone;
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

    @OnClick({R.id.tv_skip, R.id.btn_bind_phone, R.id.tv_btn_code, R.id.tv_user_protocol, R.id.tv_user_privacy})
    public void onViewClicked(View view) {
        Log.d("onViewClicked-->>", "onViewClicked");
        switch (view.getId()) {
            case R.id.tv_skip:
                clickSkip();
                break;
            case R.id.btn_bind_phone:
                bindPhone();
                break;
            case R.id.tv_btn_code:

                bindCode();
                break;
            case R.id.tv_user_protocol:
            case R.id.tv_user_privacy:
                // 查看 用户协议
                lookUserProtocol();
                break;
        }
    }

    private void clickSkip() {
        startActivity(new Intent(BindPhoneActivity.this, MainActivity.class));
        finish();
    }

    private void bindCode() {
        input_phone = et_bind_phone.getText().toString().trim();
        Log.d("phone-->>", input_phone);
        if (!DataCheck.isCellphone(input_phone)) {
            ToastUtils.showShortToast("请输入正确的手机号");
            return;
        }
        time.setTextview(tv_btn_code);
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

        OkHttpManager.request(Constants.getApi.GETBINDCODE, RequestType.POST, map, new OkHttpManager.RequestListener() {
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

    private void bindPhone() {
        input_phone = et_bind_phone.getText().toString().trim();
        input_code = et_bind_code.getText().toString().trim();
        Log.d("phone-->>", input_phone);
        Log.d("input_code-->>", input_code);
        if (!DataCheck.isCellphone(input_phone)) {
            ToastUtils.showShortToast("请输入正确的手机号");
            return;
        }
//        ToastUtils.showShortToast("绑定成功!");
//        startActivity(new Intent(BindPhoneActivity.this, MainActivity.class));
//        finish();

        //发送服务端
        Map map = new HashMap<String, String>();
        map.put("phone", input_phone);
        map.put("code", input_code);

        OkHttpManager.request(Constants.getApi.BINDPHONE, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("Success-->>", info.toString());
                try {
                    JSONObject jdata = new JSONObject(info.getRetDetail());
//                    {"code":false,"msg":"验证码已过期，请重新获取","data":[]}
//                    {"code":true,"msg":"合并成功","data":{"user_info":{"user_id":49,"head_img":"","nick_name":"王","gender":2,"phone":"13073701204","birthday":"2010-3-28","status":0}}}
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
                        UserModel uModel = new UserModel(user_id, head_img, nick_name, gender, phone);
                        Constants.userInfo = uModel;
                        Constants.saveData();
                        startActivity(new Intent(BindPhoneActivity.this, MainActivity.class));
                    } else {
                        String msg = jsonData.getString("msg");
                        ToastUtils.showShortToast(msg);
                    }
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

    private void lookUserProtocol() {
        startPrivacyActivity();
    }

    private void startPrivacyActivity() {
        Map map = new HashMap();
        OkHttpManager.request(Constants.getApi.GETPRIVACYTEXT, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                //跳转到privacyActivity
                try {
                    JSONObject jsonobject = new JSONObject(info.getRetDetail());
                    JSONObject jData = jsonobject.getJSONObject("data");
                    String privacy = jData.getString("privacy_content");
                    Intent intent = new Intent(mContext, PrivacyActivty.class);
                    intent.putExtra("text", privacy);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.showShortToast("解析失败!");
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                ToastUtils.showShortToast("网络错误!");
            }
        });
    }
}
