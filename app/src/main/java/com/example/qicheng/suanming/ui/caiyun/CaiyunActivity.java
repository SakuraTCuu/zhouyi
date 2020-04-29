package com.example.qicheng.suanming.ui.caiyun;

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
import com.example.qicheng.suanming.ui.webView.NamePayActivity;
import com.example.qicheng.suanming.utils.CustomDateDialog;
import com.example.qicheng.suanming.utils.GlnlUtils;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.common.ResourcesManager;
import com.example.qicheng.suanming.utils.DataCheck;
import com.example.qicheng.suanming.utils.MapUtils;
import com.example.qicheng.suanming.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CaiyunActivity extends BaseActivity {

    @BindView(R.id.et_caiyun_name)
    EditText et_caiyun_name;

    @BindView(R.id.tv_caiyun_man)
    TextView tv_caiyun_man;

    @BindView(R.id.tv_caiyun_women)
    TextView tv_caiyun_women;

    @BindView(R.id.tv_caiyun_birthday)
    TextView tv_caiyun_birthday;

    private String input_name;
    private Calendar cDate;
    private int gender = 1;

    private GlnlUtils.glnlType nlType;
    private Boolean isGL = true;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_caiyun;
    }

    @Override
    protected void initView() {
        setTitleText("财运分析");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.btn_caiyun_click, R.id.tv_caiyun_man, R.id.tv_caiyun_women, R.id.tv_caiyun_birthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_caiyun_click:
                onClickBtn();
                break;
            case R.id.tv_caiyun_man:
                onClickMan();
                break;
            case R.id.tv_caiyun_women:
                onClickWomen();
                break;
            case R.id.tv_caiyun_birthday:
                showDatePicker();
                break;
        }
    }

    private void onClickBtn() {
        input_name = et_caiyun_name.getText().toString().trim();
        Log.d("onClickBtn-->>", input_name);
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
//         年月日   服务器参数定义
//        String birthday = cDate.get(Calendar.YEAR) + "-" + month + "-" + cDate.get(Calendar.DATE);

        String birthday;

        if (isGL) {
            birthday = cDate.get(Calendar.YEAR) + "-" + month + "-" + cDate.get(Calendar.DATE);
        } else {
            birthday = nlType.getString();
        }

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
        map.put("type", "5");

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

                    Intent intent = new Intent(CaiyunActivity.this, NamePayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    bundle.putString("title", "财运分析");
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

        tv_caiyun_women.setTextColor(getResources().getColor(R.color.white));
        tv_caiyun_women.setBackground(ResourcesManager.getDrawable(mContext, R.drawable.circle_caiyun_select));

        tv_caiyun_man.setTextColor(getResources().getColor(R.color.red));
        tv_caiyun_man.setBackground(ResourcesManager.getDrawable(mContext, R.drawable.circle_caiyun_unselect));
    }

    private void onClickMan() {
        if (gender == 1) {
            return;
        }
        gender = 1;
        tv_caiyun_man.setTextColor(getResources().getColor(R.color.white));
        tv_caiyun_man.setBackground(ResourcesManager.getDrawable(mContext, R.drawable.circle_caiyun_select));

        tv_caiyun_women.setTextColor(getResources().getColor(R.color.red));
        tv_caiyun_women.setBackground(ResourcesManager.getDrawable(mContext, R.drawable.circle_caiyun_unselect));
    }

    public void showDatePicker() {
        CustomDateDialog dialog = new CustomDateDialog(mContext);
        //设置上下年分限制
        dialog.setYearLimt(50);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
//        dialog.setType(DateType.TYPE_YMDHM);
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
        dialog.setOnSureLisener(new CustomDateDialog.OnCustomSureLisener() {

            //            public void onSure(Date date) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                cDate = calendar;
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH) + 1;  //月份从0开始算起
//                int day = calendar.get(Calendar.DATE);
//                int hour = calendar.get(Calendar.HOUR);
//                int minute = calendar.get(Calendar.MINUTE);
//
//                //小于10 前边加0   如 9月 会变成09月
//                String monthStr = addZero2Date(month);
//                String dayStr = addZero2Date(day);
//                String hourStr = addZero2Date(hour);
//                String minuteStr = addZero2Date(minute);
//
//                String dateStr = year + "年" + monthStr + "月" + dayStr + "    " + hourStr + ":" + minuteStr;
//                tv_caiyun_birthday.setText(dateStr);
//            }
            @Override
            public void onSure(Date date, boolean flag) {
                isGL = flag;
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

                if (!flag) { //农历
                    String glDate = year + monthStr + dayStr;
                    String nlDate;
                    try {
                        nlType = GlnlUtils.lunarToSolar(glDate, false);
                        nlDate = nlType.getTypeString();
                    } catch (Exception e) {
                        e.printStackTrace();
                        String dateStr = year + "年" + monthStr + "月" + dayStr;
                        nlType = new GlnlUtils.glnlType(year + "", monthStr, dayStr, dateStr);
                        nlDate = dateStr;
                    }
                    tv_caiyun_birthday.setText(nlDate);
                } else {
                    String dateStr = year + "年" + monthStr + "月" + dayStr + "    " + hourStr + ":" + minuteStr;
                    tv_caiyun_birthday.setText(dateStr);
                }
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
