package com.example.qicheng.suanming.ui.fragment.bazi;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.example.qicheng.suanming.base.BaseFragment;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.common.ResourcesManager;
import com.example.qicheng.suanming.ui.webView.NamePayActivity;
import com.example.qicheng.suanming.utils.CustomDateDialog;
import com.example.qicheng.suanming.utils.CustomDatePicker;
import com.example.qicheng.suanming.utils.DataCheck;
import com.example.qicheng.suanming.utils.GlnlUtils;
import com.example.qicheng.suanming.utils.MapUtils;
import com.example.qicheng.suanming.utils.Nl2gl;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.example.qicheng.suanming.R;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class BaziFragment extends BaseFragment {

    @BindView(R.id.bazi_edit_inputname)
    EditText bazi_edit_inputname;
    @BindView(R.id.bazi_gender_men)
    ImageView bazi_gender_men;
    @BindView(R.id.bazi_gender_women)
    ImageView bazi_gender_women;
    @BindView(R.id.bazi_text_birthday)
    TextView bazi_text_birthday;

    Calendar cDate;  //选中的日期
    String input_name;
    String birthday = new Date().toString();
    int gender = 1;  // 性别 1男    0女

    private GlnlUtils.glnlType nlType;
    private Boolean isGL = true;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_bazi;
    }

    @Override
    protected void initView() {
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        cDate = cl;
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        bazi_text_birthday.setText(dateStr);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.bazi_gender_men, R.id.bazi_gender_women, R.id.bazi_text_birthday, R.id.bazi_click_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bazi_click_btn:
                onClickBaziBtn();
                break;
            case R.id.bazi_text_birthday:
                showDatePicker();
                break;
            case R.id.bazi_gender_women:
                onClickWomen();
                break;
            case R.id.bazi_gender_men:
                onClickMen();
                break;
        }
    }

    public void onClickBaziBtn() {
        input_name = bazi_edit_inputname.getText().toString().trim();
        Log.d("onClickBaziBtn-->>", input_name);
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

        String year = cDate.get(Calendar.YEAR) + "";
        String day = cDate.get(Calendar.DATE) + "";

        if (isGL) {
            birthday = cDate.get(Calendar.YEAR) + "-" + month + "-" + cDate.get(Calendar.DATE);
        } else {
            birthday = nlType.getString();
        }

// 年月日   服务器参数定义
//        QIMING
        this.getDataFromServer();
    }

    private void getDataFromServer() {
        Map<String, Object> map = new HashMap();
        map.put("user_name", input_name);
        map.put("birthday", birthday);
        map.put("gender", gender + "");
        map.put("user_id", Constants.userInfo.getUser_id());

        String urlData = MapUtils.Map2String(map);

        //类型1  八字精批
        Map<String, String> map2 = new HashMap<>();
        map2.put("type", "1");

        //跳转到webView 界面
        OkHttpManager.request(Constants.getApi.GETH5URL, RequestType.POST, map2, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.toString());
                    String url = jsonObject.getJSONObject("data").getString("url");
                    url += urlData;
                    Log.d("url---->>", url);

                    Intent intent = new Intent(mContext, NamePayActivity.class);
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


    public void onClickWomen() {
        Drawable select_bg = ResourcesManager.getDrawable(getContext(), R.mipmap.bazi_yixuan);
        bazi_gender_women.setBackground(select_bg);

        Drawable unselect_bg = ResourcesManager.getDrawable(getContext(), R.mipmap.bazi_weixuan);
        bazi_gender_men.setBackground(unselect_bg);

        gender = 0;
    }

    public void onClickMen() {
        Drawable select_bg = ResourcesManager.getDrawable(getContext(), R.mipmap.bazi_yixuan);
        bazi_gender_men.setBackground(select_bg);

        Drawable unselect_bg = ResourcesManager.getDrawable(getContext(), R.mipmap.bazi_weixuan);
        bazi_gender_women.setBackground(unselect_bg);
        gender = 1;
    }

    public void showDatePicker() {
        CustomDateDialog dialog = new CustomDateDialog(getContext());

//        DatePickDialog dialog = new DatePickDialog(getContext());
        //设置上下年分限制
        dialog.setYearLimt(50);
        //设置标题
//        dialog.setTitle("选择时间");
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
                    bazi_text_birthday.setText(nlDate);
                } else {
                    String dateStr = year + "年" + monthStr + "月" + dayStr + "    " + hourStr + ":" + minuteStr;
                    bazi_text_birthday.setText(dateStr);
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