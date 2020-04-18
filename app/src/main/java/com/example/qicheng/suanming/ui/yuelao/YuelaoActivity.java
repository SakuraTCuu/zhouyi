package com.example.qicheng.suanming.ui.yuelao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.example.qicheng.suanming.ui.webView.NamePayActivity;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.adapter.HehunAdapter;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.bean.HehunListBean;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.utils.DataCheck;
import com.example.qicheng.suanming.utils.MapUtils;
import com.example.qicheng.suanming.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class YuelaoActivity extends BaseActivity implements AbsListView.OnScrollListener {

    @BindView(R.id.lv_yuelao_switcher)
    ListView lv_yuelao_switcher;

    @BindView(R.id.et_yuelao_inputname)
    EditText et_yuelao_inputname;

    @BindView(R.id.tv_yuelao_date)
    TextView tv_yuelao_date;

    @BindView(R.id.iv_yuelao_man)
    ImageView iv_yuelao_man;

    @BindView(R.id.iv_yuelao_women)
    ImageView iv_yuelao_women;

    @BindView(R.id.iv_yuelao_date)
    ImageView iv_yuelao_date;

    private int gender = 1;// 1 男性  2 女性
    private Calendar cDate;
    private HehunAdapter adapter;
    private List data;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_yuelao;
    }

    @Override
    protected void initView() {
        setTitleText("月老姻缘");
        setTextSwitche();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.btn_yuelao_cesuan, R.id.iv_yuelao_date, R.id.iv_yuelao_women, R.id.iv_yuelao_man, R.id.tv_yuelao_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_yuelao_cesuan:
                onClickBtn();
                break;
            case R.id.tv_yuelao_date:
            case R.id.iv_yuelao_date:
                showDatePicker();
                break;
            case R.id.iv_yuelao_women:
                onClickWomenImg();
                break;
            case R.id.iv_yuelao_man:
                onClickManImg();
                break;
        }
    }

    //设置tv翻滚数据
    private void setTextSwitche() {
        Calendar calender = Calendar.getInstance();
        calender.setTime(new Date());
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH) + 1;
        int day = calender.get(Calendar.DATE);

        String monthStr = addZero2Date(month);
        String dayStr = addZero2Date(day);

        String textStr = year + "-" + monthStr + "-" + dayStr;
        textStr += "订单号:" + year + monthStr + "****";

        //随机订单号
        String[] words = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        // 开吹
        String[] contentList = new String[]{"这个网站可以信赖 挺好的!",
                "会推荐给其他人,我觉得挺准的",
                "看着同龄人小孩都可以打酱油了,家里人也开始催了,我会听大师建议好好抓住机会的,谢谢指导",
                "里面说的情况和我现在的真像!"
        };

        String orderStr = "";
        for (int i = 0; i < 4; i++) {
            Random rand = new Random();
            int number = rand.nextInt(26);
            orderStr += words[number];
        }
        textStr += orderStr;

        data = new ArrayList<HehunListBean>();
        //生成20个无限循环
        for (int i = 1; i < 20; i++) {
            data.add(new HehunListBean(textStr, contentList[i % contentList.length]));
        }

        Log.d("textStr-->>", textStr);

        adapter = new HehunAdapter(mContext, data);
        lv_yuelao_switcher.setAdapter(adapter);
        lv_yuelao_switcher.setOnScrollListener(this);
        lv_yuelao_switcher.setSelection(data.size());
        new Timer().schedule(new TimeTaskScroll(this, lv_yuelao_switcher), 0, 10);
    }

    /**
     * 测算
     */
    public void onClickBtn() {
        String userName = et_yuelao_inputname.getText().toString().trim();
        if (!DataCheck.isHanzi(userName)) {
            ToastUtils.showShortToast("请输入正确的名字");
            return;
        }
        if (cDate == null) {
            ToastUtils.showShortToast("请输入时间");
            return;
        }
        long dateMillis = cDate.getTimeInMillis();
        if (dateMillis >= new Date().getTime()) {
            ToastUtils.showShortToast("请输入正确的时间");
            return;
        }

        int year = cDate.get(Calendar.YEAR);
        int month = cDate.get(Calendar.MONTH) + 1;
        int date = cDate.get(Calendar.DATE);

        String dateStr = year + "-" + month + "-" + date;

        Map<String, Object> map = new HashMap();
        map.put("user_name", userName);
        map.put("gender", gender + "");
        map.put("birthday", dateStr);
        map.put("user_id", Constants.userInfo.getUser_id());

        String url_data = MapUtils.Map2String(map);
        Log.d("url_data-------->", url_data);
        this.getDataFromServer(url_data);
    }

    private void getDataFromServer(String urlData) {
        //类型1  八字精批
        Map<String, String> map = new HashMap();
        map.put("type", "4");

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

                    Intent intent = new Intent(YuelaoActivity.this, NamePayActivity.class);
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

    /**
     * 点击女图标
     */
    public void onClickWomenImg() {
        gender = 0;
        this.iv_yuelao_man.setImageResource(R.mipmap.yuelao_weixuan);
        this.iv_yuelao_women.setImageResource(R.mipmap.yuelao_xuanzhong);
    }

    /**
     * 点击男图标
     */
    public void onClickManImg() {
        gender = 1;
        this.iv_yuelao_man.setImageResource(R.mipmap.yuelao_xuanzhong);
        this.iv_yuelao_women.setImageResource(R.mipmap.yuelao_weixuan);
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
                tv_yuelao_date.setText(dateStr);
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    /**
     * 设置滚动监听，当滚动到第二个时，跳到地list.size()+2个，滚动到倒数第二个时，跳到中间第二个，setSelection时，
     * 由于listView滚动并未停止，所以setSelection后会继续滚动，不会出现突然停止的现象
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
/**到顶部添加数据关键代码*/
        if (firstVisibleItem <= 2) {
            lv_yuelao_switcher.setSelection(data.size() + 2);
        } else if (firstVisibleItem + visibleItemCount > adapter.getCount() - 2) {//到底部添加数据
            lv_yuelao_switcher.setSelection(firstVisibleItem - data.size());
        }
    }

    class TimeTaskScroll extends TimerTask {
        private ListView listView;

        public TimeTaskScroll(Context context, ListView listView) {
            this.listView = listView;
        }

        private Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                //  控制速度
                listView.smoothScrollBy(3, 10);
            }
        };

        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);
        }
    }
}


