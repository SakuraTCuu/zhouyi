package com.qicheng.zhouyi.ui.bazi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.adapter.HehunAdapter;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.HehunListBean;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.qicheng.zhouyi.ui.bazijingpi.BaziJingPiActivity;
import com.qicheng.zhouyi.ui.webView.NamePayActivity;
import com.qicheng.zhouyi.utils.DataCheck;
import com.qicheng.zhouyi.utils.MapUtils;
import com.qicheng.zhouyi.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static com.sdk.base.module.manager.SDKManager.getContext;

/**
 * @author Sakura
 * @time 2020/1/14 11:47
 */
public class BaziHehunActivity extends BaseActivity implements AbsListView.OnScrollListener {

    @BindView(R.id.lv_switcher)
    ListView lv_switcher;

    @BindView(R.id.et_input_man_name)
    EditText et_input_man_name;

    @BindView(R.id.et_input_women_name)
    EditText et_input_women_name;

    @BindView(R.id.tv_women_date)
    TextView tv_women_date;

    @BindView(R.id.tv_man_date)
    TextView tv_man_date;

    private int clickDateId = 0;                 //当前数据翻滚位置标记
    private Calendar manDate;              //男日期
    private Calendar womenDate;            //女日期

    private ArrayList<HehunListBean> data;
    private HehunAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bazi_hehun;
    }

    @Override
    protected void initView() {
        setTitleText("八字合婚");
        //设置switcher_text中的TextVie
        setTextSwitche();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

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
        //  开吹
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
        lv_switcher.setAdapter(adapter);
        lv_switcher.setOnScrollListener(this);
        lv_switcher.setSelection(data.size());
        new Timer().schedule(new TimeTaskScroll(this, lv_switcher), 0, 10);
    }

    @OnClick({R.id.btn_hehun_cesuan1, R.id.btn_hehun_cesuan2, R.id.iv__man_date, R.id.iv__woman_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_hehun_cesuan1:
            case R.id.btn_hehun_cesuan2:
                onClickCeSuan();
                break;
            case R.id.iv__man_date:
                clickDateId = 0;
                showDatePicker();
                break;
            case R.id.iv__woman_date:
                clickDateId = 1;
                showDatePicker();
                break;
        }
    }

    public void onClickCeSuan() {
        //judge the input name is right or no;
        String manName = et_input_man_name.getText().toString().trim();
        String womenName = et_input_women_name.getText().toString().trim();
        if (!DataCheck.isHanzi(manName)) {
            ToastUtils.showShortToast("请输入正确的男方姓名");
            return;
        } else if (!DataCheck.isHanzi(womenName)) {
            ToastUtils.showShortToast("请输入正确的女方姓名");
            return;
        } else if (manDate == null) {
            ToastUtils.showShortToast("请选择男方出生日期");
            return;
        } else if (womenDate == null) {
            ToastUtils.showShortToast("请选择女方出生日期");
            return;
        }
        //检查出生日期

        if (new Date().getTime() < manDate.getTimeInMillis()) {
            ToastUtils.showShortToast("请选择正确的男方出生日期");
            return;
        }
        if (new Date().getTime() < womenDate.getTimeInMillis()) {
            ToastUtils.showShortToast("请选择正确女方出生日期");
            return;
        }

        int manYear = manDate.get(Calendar.YEAR); ;
        int manMonth = manDate.get(Calendar.MONTH);
        int manDay = manDate.get(Calendar.DAY_OF_MONTH);
        int manHour = manDate.get(Calendar.HOUR_OF_DAY);

        int womanYear = womenDate.get(Calendar.YEAR); ;
        int womanMonth = womenDate.get(Calendar.MONTH);
        int womanDay = womenDate.get(Calendar.DAY_OF_MONTH);
        int womanHour = womenDate.get(Calendar.HOUR_OF_DAY);

        Map<String, Object> map = new HashMap();
        map.put("user_id", Constants.userId);
        map.put("user_name", manName);
        map.put("y", manYear);
        map.put("m", manMonth);
        map.put("d", manDay);
        map.put("h", manHour);

        map.put("girl_username", womenName);
        map.put("y1", womanYear);
        map.put("m1", womanMonth);
        map.put("d1", womanDay);
        map.put("h1", womanHour);

        String url_data = MapUtils.Map2String(map);
        Log.d("url_data-------->", url_data);
        this.getDataFromServer(url_data);
    }

    private void getDataFromServer(String urlData) {

        Map<String, String> map = new HashMap();
        map.put("type", "2");

        //跳转到webView 界面
        OkHttpManager.request(Constants.getApi.GETH5URL, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>",  jsonObject.toString());
                    String url = jsonObject.getJSONObject("data").getString("url");
                    url +=urlData;
                    Log.d("url---->>", url);

                    Intent intent = new Intent(BaziHehunActivity.this, NamePayActivity.class);
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
     * 展示日期
     */
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
                if (clickDateId == 0) {
                    manDate = calendar;
                    tv_man_date.setText(dateStr);
                } else if (clickDateId == 1) {
                    womenDate = calendar;
                    tv_women_date.setText(dateStr);
                }
            }
        });
        dialog.show();
    }

    /**
     * add a zero to any number less than 10 and greater than 0;
     *
     * @param i 转换日期
     * @return
     */
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
            lv_switcher.setSelection(data.size() + 2);
        } else if (firstVisibleItem + visibleItemCount > adapter.getCount() - 2) {//到底部添加数据
            lv_switcher.setSelection(firstVisibleItem - data.size());
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

