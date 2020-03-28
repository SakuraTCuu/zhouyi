package com.example.qicheng.suanming.ui.mine;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.utils.DataCheck;
import com.example.qicheng.suanming.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFeedbackActivity extends BaseActivity {

    @BindView(R.id.et_input_content)
    EditText et_input_content;

    @BindView(R.id.et_input_phone)
    EditText et_input_phone;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_feedback;
    }

    @Override
    protected void initView() {
        showTitleBar();
        setTitleText("用户反馈");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.btn_input})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_input:
                sendData2Server();
                break;
        }
    }

    public void sendData2Server() {
        //提交
        String content = et_input_content.getText().toString().trim();
        String phone = et_input_phone.getText().toString().trim();
        if (content.equals("")) {
            ToastUtils.showShortToast("请输入反馈信息");
            return;
        } else if (!DataCheck.isCellphone(phone)) {
            ToastUtils.showShortToast("请输入联系方式");
            return;
        }

        Map map = new HashMap<String, String>();
        map.put("content", content);
        map.put("phone", phone);

        //提交信息
        OkHttpManager.request(Constants.getApi.USERREPORT, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("Success-->>", info.getRetDetail());
                ToastUtils.showShortToast("提交成功");
                finish();
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("Fail-->>", info.getRetDetail());
            }
        });

    }
}
