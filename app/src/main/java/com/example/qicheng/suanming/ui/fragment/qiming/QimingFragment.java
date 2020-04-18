package com.example.qicheng.suanming.ui.fragment.qiming;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.qicheng.suanming.base.BaseFragment;
import com.example.qicheng.suanming.bean.DaShiKeFuBean;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.common.ResourcesManager;
import com.example.qicheng.suanming.ui.qiming.QimingDetailActivity;
import com.example.qicheng.suanming.utils.DataCheck;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.example.qicheng.suanming.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import org.json.JSONException;
import org.json.JSONObject;

public class QimingFragment extends BaseFragment {

    @BindView(R.id.edit_input_name)
    EditText edit_input_name;
    @BindView(R.id.text_birthday)
    TextView text_birthday;
    @BindView(R.id.text_birthday_state)
    TextView text_birthday_state;
    @BindView(R.id.text_live)
    TextView text_live;
    @BindView(R.id.text_noLive)
    TextView text_noLive;
    @BindView(R.id.text_men)
    TextView text_men;
    @BindView(R.id.text_women)
    TextView text_women;
    @BindView(R.id.text_unknow)
    TextView text_unknow;

    @BindView(R.id.tv_dashi_name)
    TextView tv_dashi_name;
    @BindView(R.id.tv_dashi_desc)
    TextView tv_dashi_desc;

    Calendar cDate;  //选中的日期

    String input_name;
    String birthday = new Date().toString();
    int gender = 1;  // 性别 1男    0女
    int birthState = 1;  // 出生状态  1已出生  0未出生

    String date_type = "1"; // 日期类型  公历还是农历

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_qiming;
    }

    @Override
    protected void initView() {
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        cDate = cl;
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        text_birthday.setText(dateStr);
    }

    @Override
    protected void initData() {

        Map<String, String> map = new HashMap<>();
        OkHttpManager.request(Constants.getApi.GETKEFUINFO, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("GETKEFUINFO---->>", info.getRetDetail());
                try {
                    //看文档
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    JSONObject jData = jsonObject.getJSONObject("data");
                    JSONObject kefuData = jData.getJSONObject("kf");
                    JSONObject dashiData = jData.getJSONObject("ds");
                    String kefu_wx = kefuData.getString("wx");
                    String kefu_img = kefuData.getString("wx_qrcode");
                    String ds_name = dashiData.getString("ds_name");
                    String ds_img = dashiData.getString("ds_img");
                    String ds_desc = dashiData.getString("ds_desc");
                    DaShiKeFuBean kefuInfo = new DaShiKeFuBean(kefu_wx, kefu_img, ds_name, ds_img, ds_desc);
                    Constants.kefuInfo = kefuInfo;

                    tv_dashi_name.setText(kefuInfo.getDashi_name());
                    tv_dashi_desc.setText(kefuInfo.getDashi_desc());
// {"code":true,"msg":"操作成功","data":{"kf":{"wx":"aaaa","wx_qrcode":"http:\/\/app.zhouyi999.cn\/static\/common\/image\/kefu.png"},
// "ds":{"ds_name":"苏才谦","ds_img":"http:\/\/app.zhouyi999.cn\/static\/common\/image\/kefu.png",
// "ds_desc":"我是一个大师，大的很。我是一个大师，大的很。我是一个大师，大的很。我是一个大师，大的很。我是一个大师，大的很。我是一个大师，大的很。"}}}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("info---->>", info.toString());
            }
        });
    }

    @OnClick({R.id.click_btn, R.id.text_live, R.id.text_noLive, R.id.text_men, R.id.text_women, R.id.text_birthday, R.id.text_unknow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.click_btn:
                onClickQimingBtn();
                break;
            case R.id.text_birthday:
                showDatePicker();
                break;
            case R.id.text_live:
                onClickLive();
                break;
            case R.id.text_noLive:
                onClickNoLive();
                break;
            case R.id.text_men:
                onClickMen();
                break;
            case R.id.text_women:
                onClickWomen();
                break;
            case R.id.text_unknow:
                onClickUnknow();
                break;
        }
    }

    /**
     * 起名
     */
    public void onClickQimingBtn() {
        input_name = edit_input_name.getText().toString().trim();
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
        if (birthState == 1 && cDate.getTimeInMillis() > curTime) {
            ToastUtils.showShortToast("请选择正确的时间");
            return;
        } else if (birthState == 0 && cDate.getTimeInMillis() < curTime) {
            ToastUtils.showShortToast("请选择正确的时间");
            return;
        }

        String month = String.valueOf(cDate.get(Calendar.MONTH) + 1);
//         年月日   服务器参数定义
        birthday = cDate.get(Calendar.YEAR) + "-" + month + "-" + cDate.get(Calendar.DATE);
//        QIMING
        this.getDataFromServer();
    }

    public void getDataFromServer() {
        Map map = new HashMap<String, String>();
        map.put("user_name", input_name);
        map.put("birthday", birthday);
        map.put("gender", gender + "");
        map.put("date_type", date_type);

        OkHttpManager.request(Constants.getApi.QIMING, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                getMainHandler().sendEmptyMessage(BASE_END);

                try {
                    JSONObject json = new JSONObject(info.getRetDetail());
                    if (json.getBoolean("code")) {
                        //请求成功
                        Intent intent = new Intent(mContext, QimingDetailActivity.class);
                        intent.putExtra("data", json.getJSONObject("data").toString());
                        mContext.startActivity(intent);
                    } else {
                        ToastUtils.showShortToast(json.get("message").toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                String result = info.getRetDetail();
                getMainHandler().sendEmptyMessage(BASE_END);
                ToastUtils.showShortToast(result);
            }
        });
    }

    /**
     * 点击已出生
     * 设置背景
     * 设置状态
     * 设置文本
     */
    public void onClickLive() {
        Drawable select_live_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_shape);
        text_live.setBackground(select_live_bg);

        Drawable unselect_live_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_unshape);
        text_noLive.setBackground(unselect_live_bg);

        birthState = 1;
        text_birthday_state.setText("出生日期");
        text_unknow.setVisibility(View.INVISIBLE);

//         切换回来后默认选择男
        if (gender == -1) {
            gender = 1;
            onClickMen();
        }
    }

    /**
     * 点击未出生
     * 设置背景
     * 设置状态
     * 设置文本
     * 显示 未知性别
     */
    public void onClickNoLive() {
        Drawable select_nolive_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_shape);
        text_noLive.setBackground(select_nolive_bg);

        Drawable unselect_nolive_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_unshape);
        text_live.setBackground(unselect_nolive_bg);

        birthState = 0;
        text_birthday_state.setText("预产日期");
        //TODO 显示未知性别
        text_unknow.setVisibility(View.VISIBLE);
        onClickUnknow();
    }

    /**
     * 点击男
     * 设置背景
     * 设置状态
     */
    public void onClickMen() {
        Drawable select_men_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_shape);
        text_men.setBackground(select_men_bg);

        Drawable unselect_men_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_unshape);
        text_women.setBackground(unselect_men_bg);

        if (birthState != 1) {
            text_unknow.setBackground(unselect_men_bg);
        }

        gender = 1;
    }

    /**
     * 点击女
     * 设置背景
     * 设置状态
     */
    public void onClickWomen() {
        Drawable select_women_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_shape);
        text_women.setBackground(select_women_bg);

        Drawable unselect_women_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_unshape);
        text_men.setBackground(unselect_women_bg);

        if (birthState != 1) {
            text_unknow.setBackground(unselect_women_bg);
        }
        gender = 0;
    }

    /**
     * 点击未知
     * 设置背景
     * 设置状态
     */
    public void onClickUnknow() {
        Drawable select_unknow_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_shape);
        text_unknow.setBackground(select_unknow_bg);

        Drawable unselect_women_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_unshape);
        text_women.setBackground(unselect_women_bg);

        Drawable unselect_men_bg = ResourcesManager.getDrawable(getContext(), R.drawable.qiming_bg_unshape);
        text_men.setBackground(unselect_men_bg);

        gender = -1;
    }


    public void showDatePicker() {
        DatePickDialog dialog = new DatePickDialog(getContext());
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
                text_birthday.setText(dateStr);
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