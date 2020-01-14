package com.qicheng.zhouyi.ui.bazi;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.adapter.HehunAdapter;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.HehunListBean;
import com.qicheng.zhouyi.utils.AutoVerticalScrollTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Sakura
 * @time 2020/1/14 11:47
 */
public class BaziHehunActivity extends BaseActivity implements AbsListView.OnScrollListener {

//    @BindView(R.id.switcher_text)
//    AutoVerticalScrollTextView textSwitcher;

    @BindView(R.id.lv_switcher)
    ListView lv_switcher;

    @BindView(R.id.et_input_man_name)
    EditText et_input_man_name;

    @BindView(R.id.et_input_women_name)
    EditText et_input_women_name;

    private int tv_TAG = 0;                 //当前数据翻滚位置标记
    private ArrayList<HehunListBean> data;
    private HehunAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bazi_hehun;
    }

    @Override
    protected void initView() {
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

        String monthStr;
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = "" + month;
        }

        String dayStr;
        if (month < 10) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }

        String textStr = year + "-" + monthStr + "-" + dayStr;
        textStr += "订单号:" + year + monthStr + "****";

        String[] words = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] contentList = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        String orderStr = "";
        for (int i = 0; i < 4; i++) {
            Random rand = new Random();
            int number = rand.nextInt(26);
            orderStr += words[number];
        }
        textStr += orderStr;

        data = new ArrayList<HehunListBean>();
        //生成20个无限循环
        for (int i = 1; i <= 20; i++) {
            data.add(new HehunListBean(textStr, contentList[contentList.length % i]));
        }

        Log.d("textStr-->>", textStr);

        adapter = new HehunAdapter(mContext, data);
        lv_switcher.setAdapter(adapter);
        lv_switcher.setOnScrollListener(this);
        lv_switcher.setSelection(data.size());
        new Timer().schedule(new TimeTaskScroll(this, lv_switcher), 0, 10);

//        textSwitcher.setText(textStr);
//        textSwitcher.showTextAnimation();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setTextSwitche();
//            }
//        }, 5000);
    }

    @OnClick({R.id.btn_hehun_cesuan1, R.id.btn_hehun_cesuan2, R.id.iv__man_date, R.id.iv__woman_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_hehun_cesuan1:
            case R.id.btn_hehun_cesuan2:
                onClickCeSuan();
                break;
            case R.id.iv__man_date:
            case R.id.iv__woman_date:
                showDatePacker();
                break;
        }
    }

    public void onClickCeSuan() {

    }

    /**
     * 展示日期
     */
    public void showDatePacker() {

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
