package com.example.qicheng.suanming.ui.mouseYear;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.common.ResourcesManager;
import com.example.qicheng.suanming.ui.webView.NamePayActivity;
import com.example.qicheng.suanming.utils.DataCheck;
import com.example.qicheng.suanming.utils.MapUtils;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.example.qicheng.suanming.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MouseYearActivity extends BaseActivity {

    @BindView(R.id.tv_shunian_man)
    TextView tv_shunian_man;

    @BindView(R.id.tv_shunian_women)
    TextView tv_shunian_women;

    @BindView(R.id.et_shunian_name)
    EditText et_shunian_name;

    @BindView(R.id.tv_shunian_birthday)
    TextView tv_shunian_birthday;

    private Calendar cDate;
    private String input_name;
    private int gender = 1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mouse_year;
    }

    @Override
    protected void initView() {
        setTitleText("鼠年运程");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.tv_shunian_man, R.id.tv_shunian_women, R.id.tv_shunian_birthday, R.id.btn_shunian_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shunian_man:
                onClickMan();
                break;
            case R.id.tv_shunian_women:
                onClickWomen();
                break;
            case R.id.tv_shunian_birthday:
                showDatePicker();
                break;
            case R.id.btn_shunian_click:
                onClickBtn();
                break;
        }
    }

    private void onClickBtn() {
        input_name = et_shunian_name.getText().toString().trim();
        Log.d("onClickQimingBtn-->>", input_name);
        if (!DataCheck.isHanzi(input_name)) {
//            请输入正确的姓氏
            ToastUtils.showShortToast("请输入正确的姓氏");
            return;
        }
        //计算时间是否正确
        if (cDate == null) {
            ToastUtils.showShortToast("请选择时间");
            return;
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        long curTime = cl.getTimeInMillis();
        if (cDate.getTimeInMillis() > curTime) {
            ToastUtils.showShortToast("请选择正确的时间");
            return;
        }

        String month = String.valueOf(cDate.get(Calendar.MONTH) + 1);
        //  年月日   服务器参数定义
        String birthday = cDate.get(Calendar.YEAR) + "-" + month + "-" + cDate.get(Calendar.DATE);

        //请求服务器
        Map<String, Object> map = new HashMap();
        map.put("birthday", birthday);
        map.put("user_name", input_name);
        map.put("gender", gender);
        map.put("user_id", Constants.userInfo.getUser_id());

        String url_data = MapUtils.Map2String(map);
        Log.d("url_data-------->", url_data);
        this.getDataFromServer(url_data);
    }

    private void getDataFromServer(String urlData) {

        Map<String, String> map = new HashMap();
        map.put("type", "3");

        //跳转到webView 界面
        OkHttpManager.request(Constants.getApi.GETH5URL, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.toString());
                    String url = jsonObject.getJSONObject("data").getString("url");
                    url += urlData;
                    Log.d("url---->>", url);

                    Intent intent = new Intent(MouseYearActivity.this, NamePayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("info---->>", info.toString());
                String result = info.getRetDetail();
                ToastUtils.showShortToast(result);
            }
        });
    }

    private void onClickWomen() {
        if (gender == 0) {
            return;
        }
        gender = 0;
        tv_shunian_women.setTextColor(getResources().getColor(R.color.white));
        tv_shunian_women.setBackground(ResourcesManager.getDrawable(mContext, R.drawable.circlebg_shunian_select));

        tv_shunian_man.setTextColor(getResources().getColor(R.color.black));
//        tv_shunian_man.setBackground(ResourcesManager.getDrawable(mContext, R.drawable.circlebg_shunian_unselect));
        tv_shunian_man.setBackground(null);
    }

    private void onClickMan() {
        if (gender == 1) {
            return;
        }
        gender = 1;
        tv_shunian_man.setTextColor(getResources().getColor(R.color.white));
        tv_shunian_man.setBackground(ResourcesManager.getDrawable(mContext, R.drawable.circlebg_shunian_select));

        tv_shunian_women.setTextColor(getResources().getColor(R.color.black));
//        tv_shunian_women.setBackground(ResourcesManager.getDrawable(mContext, R.drawable.circlebg_shunian_unselect));
        tv_shunian_women.setBackground(null);
    }


    public void showDatePicker() {
        DatePickDialog dialog = new DatePickDialog(mContext);
        //设置上下年分限制
        dialog.setYearLimt(50);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YMDHM);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(new OnChangeLisener() {
            @Override
            public void onChanged(Date date) {
                //日期监听
            }
        });

        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                cDate = calendar;
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;  //月份从0开始算起
                int day = calendar.get(Calendar.DATE);
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);

                //小于10 前边加0   如 9月 会变成09月
                String monthStr = addZero2Date(month);
                String dayStr = addZero2Date(day);
                String hourStr = addZero2Date(hour);
                String minuteStr = addZero2Date(minute);

                String dateStr = year + "年" + monthStr + "月" + dayStr + "    " + hourStr + ":" + minuteStr;
                tv_shunian_birthday.setText(dateStr);
            }
        });
        dialog.show();
    }

    public String addZero2Date(int i) {
        String str;
        if (i < 10) {
            str = "0" + i;
        } else {
            str = "" + i;
        }
        return str;
    }
}