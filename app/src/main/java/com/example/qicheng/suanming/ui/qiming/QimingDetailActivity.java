package com.example.qicheng.suanming.ui.qiming;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.qicheng.suanming.ui.qiming.fragment.ChooseNameFragment;
import com.example.qicheng.suanming.ui.qiming.fragment.DashiQimingFragment;
import com.example.qicheng.suanming.ui.qiming.fragment.QimingBaziFragment;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.adapter.QimingDetailAdapter;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.base.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class QimingDetailActivity extends BaseActivity {

    @BindView(R.id.viewpage_qiming)
    ViewPager viewPager;

    @BindView(R.id.tv_qiming_bazi_title)
    TextView tv_qiming_bazi_title;

    @BindView(R.id.tv_qiming_choose_title)
    TextView tv_qiming_choose_title;

    @BindView(R.id.tv_qiming_dashi_title)
    TextView tv_qiming_dashi_title;

    private List<BaseFragment> list;

    private ChooseNameFragment chooseNameFragment;
    private DashiQimingFragment dashiQimingFragment;
    private QimingBaziFragment qimingBaziFragment;
    private FragmentManager fm;

    private JSONArray nameList;
    private JSONObject userInfo;
    private JSONArray qr; //强弱总比值
    private String nongliStr;
    private JSONObject jReturn;
    private JSONObject pp;
    private String zodic;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_qiming_detail;
    }

    @Override
    protected void initView() {
        setTitleText("起名改名");

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Log.d("data-->>", data);
        try {
            JSONObject jsondata = new JSONObject(data);
            nameList = jsondata.getJSONArray("name_list");
            userInfo = jsondata.getJSONObject("info");
            qr = jsondata.getJSONArray("qr");
            jReturn = jsondata.getJSONObject("return");
            pp = jsondata.getJSONObject("pp");

            nongliStr = jsondata.getString("nongli_day");
            zodic = jsondata.getJSONArray("nongli").getString(6);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //将 data 传递给 fragment

        //绑定点击事件
        viewPager.addOnPageChangeListener(new MyPagerChangeListener());
        list = new ArrayList<>();
        dashiQimingFragment = new DashiQimingFragment();
        chooseNameFragment = new ChooseNameFragment(nameList, data);
        qimingBaziFragment = new QimingBaziFragment(userInfo, nongliStr, qr, jReturn, pp,zodic);

        list.add(qimingBaziFragment);
        list.add(chooseNameFragment);
        list.add(dashiQimingFragment);

        fm = getSupportFragmentManager();
        QimingDetailAdapter adater = new QimingDetailAdapter(fm, list);
        viewPager.setAdapter(adater);
        viewPager.setCurrentItem(0);  //初始化显示第一个页面
        //标题默认颜色
        tv_qiming_bazi_title.setTextColor(Color.GREEN);
        tv_qiming_choose_title.setTextColor(Color.BLACK);
        tv_qiming_dashi_title.setTextColor(Color.BLACK);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.tv_qiming_bazi_title, R.id.tv_qiming_choose_title, R.id.tv_qiming_dashi_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_qiming_bazi_title:
                viewPager.setCurrentItem(0);
                onPageChange(0);
                break;
            case R.id.tv_qiming_choose_title:
                viewPager.setCurrentItem(1);
                onPageChange(1);
                break;
            case R.id.tv_qiming_dashi_title:
                viewPager.setCurrentItem(2);
                onPageChange(2);
                break;
        }
    }

    public void onPageChange(int arg0) {
        switch (arg0) {//状态改变时底部对应的字体颜色改变
            case 0:
                tv_qiming_bazi_title.setTextColor(getResources().getColor(R.color.qiming_select_color));
                tv_qiming_choose_title.setTextColor(Color.BLACK);
                tv_qiming_dashi_title.setTextColor(Color.BLACK);
                break;
            case 1:
                tv_qiming_bazi_title.setTextColor(Color.BLACK);
                tv_qiming_choose_title.setTextColor(getResources().getColor(R.color.qiming_select_color));
                tv_qiming_dashi_title.setTextColor(Color.BLACK);
                break;
            case 2:
                tv_qiming_bazi_title.setTextColor(Color.BLACK);
                tv_qiming_choose_title.setTextColor(Color.BLACK);
                tv_qiming_dashi_title.setTextColor(getResources().getColor(R.color.qiming_select_color));
                break;
        }
    }

    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     */
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int arg0) {
            onPageChange(arg0);
        }
    }
}
