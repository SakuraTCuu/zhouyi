package com.example.qicheng.suanming.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.common.ActivityManager;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.ui.webView.PrivacyActivty;
import com.example.qicheng.suanming.utils.SPUtils;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends BaseActivity implements View.OnClickListener {


    private View popRootView;
    private PopupWindow popupWindow;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        ActivityManager.getInstance().push(this);

        hideTitleBar();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {

    }


    /**
     * Monitoring activity has finished.If it has finished, it's focus will change and call this method.
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            //弹出用户隐私协议
            //检测是否同意过授权
            String isAuth = (String) SPUtils.get(MyApplication.getInstance(), "isAuth", "");
            if (isAuth.equals("succ")) {
                startMain();
            } else {
                //弹出
                popupUserPrivacy();
            }
        }
    }

    private void startMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //查看状态
                if (Constants.isLogin) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                ActivityManager.getInstance().finishActivity(SplashActivity.this);
            }
        }, 1000);
    }

    /**
     * 用户隐私协议
     */
    private void popupUserPrivacy() {
        // 初始化popUpWindow
        popRootView = LayoutInflater.from(this).inflate(R.layout.layout_privacy, null);
        // PopUpWindow 传入 ContentView
        popupWindow = new PopupWindow(popRootView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View rootview = LayoutInflater.from(SplashActivity.this).inflate(R.layout.activity_login, null);
        popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = 0.5f;//设置阴影透明度
        this.getWindow().setAttributes(lp);

        popRootView.findViewById(R.id.tv_privacy).setOnClickListener(this);
        popRootView.findViewById(R.id.tv_agree).setOnClickListener(this);
        popRootView.findViewById(R.id.tv_disagree).setOnClickListener(this);
        ((TextView) popRootView.findViewById(R.id.tv_privacy_text)).setText(" 温馨提示\n" +
                "        感谢您使用周易命理大师，我们依据最新的监管要求更新了用户隐私权政策，特向您说明如下：\n" +
                "        1.为向您提供交易相关基本功能，我们会收集，使用必要的信息\n" +
                "        2.基于您的授权我们可能会获取您的位置等信息，您有权拒绝或取消授权\n" +
                "        3.我们会采取业界先进的安全措施保护您的信息安全。\n" +
                "        4.未经您的同意，我们不会从第三方处获取、共享或向其提供您的信\n" +
                "        5.您可以查询、更正、删除您的个人信息，我们也提供注销的渠道。");

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = SplashActivity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                SplashActivity.this.getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_privacy://查看协议
                //查看协议
                Map map = new HashMap();
                OkHttpManager.request2(Constants.getApi.GETPRIVACYTEXT, RequestType.POST, map, new OkHttpManager.RequestListener() {
                    @Override
                    public void Success(HttpInfo info) {
                        //跳转到privacyActivity
                        try {
                            JSONObject jsonobject = new JSONObject(info.getRetDetail());
                            JSONObject jData = jsonobject.getJSONObject("data");
                            String privacy = jData.getString("privacy_content");
                            Intent intent = new Intent(SplashActivity.this, PrivacyActivty.class);
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
                break;

            case R.id.tv_agree://同意
                boolean isContains = SPUtils.contains(MyApplication.getInstance(), "isAuth");
                if (!isContains) {
                    SPUtils.put(MyApplication.getInstance(), "isAuth", "succ");
                }
                //隐藏
                popupWindow.dismiss();
                //跳转到目标
                startMain();
                break;
            case R.id.tv_disagree://不同意 ,退出
                System.exit(0);
                break;
        }
    }
}
