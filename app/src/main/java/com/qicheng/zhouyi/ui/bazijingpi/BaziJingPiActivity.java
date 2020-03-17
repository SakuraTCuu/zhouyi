package com.qicheng.zhouyi.ui.bazijingpi;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.annotation.RequestType;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.qicheng.zhouyi.ui.webView.NamePayActivity;
import com.qicheng.zhouyi.utils.DataCheck;
import com.qicheng.zhouyi.utils.LogUtils;
import com.qicheng.zhouyi.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class BaziJingPiActivity extends BaseActivity {

    @BindView(R.id.et_bazijingpi_inputname)
    EditText et_bazijingpi_inputname;

    @BindView(R.id.tv_bazijingpi_date)
    TextView tv_bazijingpi_date;

    @BindView(R.id.iv_bazijingpi_man)
    ImageView iv_bazijingpi_man;

    @BindView(R.id.iv_bazijingpi_women)
    ImageView iv_bazijingpi_women;

    private String input_name;
    private Calendar cDate;
    private String gender = "1";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bazi_jing_pi;
    }

    @Override
    protected void initView() {
        setTitleText("八字精批");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.tv_bazijingpi_date, R.id.iv_bazijingpi_man, R.id.iv_bazijingpi_women, R.id.btn_bazijingpi_cesuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bazijingpi_cesuan:
                onClickBtn();
                break;
            case R.id.iv_bazijingpi_women:
                onClickWomenImg();
                break;
            case R.id.iv_bazijingpi_man:
                onClickManImg();
                break;
            case R.id.tv_bazijingpi_date:
                showDatePicker();
                break;
        }
    }

    private void onClickBtn() {
        String userName = et_bazijingpi_inputname.getText().toString().trim();
        LogUtils.d("Bazijingpi-->>", "");
        LogUtils.d("user_name", userName);
        LogUtils.d("gender", gender);
        if (!DataCheck.isHanzi(userName)) {
            ToastUtils.showShortToast("请输入正确的名字");
            return;
        }
        long dateMillis = cDate.getTimeInMillis();
        if (dateMillis >= new Date().getTime()) {
            ToastUtils.showShortToast("请输入正确的时间");
        }

        int year = cDate.get(Calendar.YEAR);
        int month = cDate.get(Calendar.MONTH);
        int date = cDate.get(Calendar.DATE);

        String dateStr = year + "-" + month + "-" + date;

        Map map = new HashMap<String, String>();
        map.put("user_name", userName);
        map.put("gender", gender);
        map.put("birthday", dateStr);
        map.put("user_id", "48");

        Log.d("birthday", dateStr);
        String url_data = "?user_name="+userName+"&gender="+gender+"&birthday="+dateStr+"&user_id=48";
        Log.d("url_data-------->", url_data);

        this.getDataFromServer(map,url_data);
    }

    private void getDataFromServer(Map params,String urlData) {

        //类型1  八字精批
        Map map = new HashMap<String, String>();
        map.put("type", "1");

//        OkHttpUtil.getDefault().doAsync(HttpInfo.Builder()
//                        .setUrl(Constants.getApi.GETH5URL)
//                        .setRequestType(RequestType.POST)//设置请求方式
//                        .addParam("user_id","48")
//                        .addParams(params)
//                        .addParam("type","1")
//                        .build(),
//                new com.okhttplib.callback.Callback() {
//                    @Override
//                    public void onSuccess(HttpInfo info) throws IOException {
//                        Log.d("onSuccess--->", info.getRetDetail());
////                        requestListener.Success(info);
//                    }
//
//                    @Override
//                    public void onFailure(HttpInfo info) throws IOException {
//                        Log.d("onFailure--->", info.getRetDetail());
////                        requestListener.Fail(info);
//                    }
//                });

        //跳转到webView 界面
        OkHttpManager.request(Constants.getApi.GETH5URL, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                     JSONObject  jsonObject = new JSONObject(info.getRetDetail());
                     Log.d("jsonObject---->>",  jsonObject.toString());
                     String url = jsonObject.getJSONObject("data").getString("url");
                     url +=urlData;
                     Log.d("url---->>", url);

                    Intent intent = new Intent(BaziJingPiActivity.this, NamePayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url",url);
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

    /**
     * 点击女图标
     */
    public void onClickWomenImg() {
        gender = "0";
        this.iv_bazijingpi_man.setImageResource(R.mipmap.character_weixuan);
        this.iv_bazijingpi_women.setImageResource(R.mipmap.character_xuanzhong);
    }

    /**
     * 点击男图标
     */
    public void onClickManImg() {
        gender = "1";
        this.iv_bazijingpi_man.setImageResource(R.mipmap.character_xuanzhong);
        this.iv_bazijingpi_women.setImageResource(R.mipmap.character_weixuan);
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
                tv_bazijingpi_date.setText(dateStr);
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
