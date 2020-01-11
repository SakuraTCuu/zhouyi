package com.qicheng.zhouyi.ui.qiming;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.adapter.QimingDetailAdapter;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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

    @Override
    protected int setLayoutId() {
        return R.layout.activity_qiming_detail;
    }

    @Override
    protected void initView() {

        setTitleText("起名改名");
        //设置ViewPager中两页之间的距离
        //绑定点击事件
        viewPager.setOnPageChangeListener(new MyPagerChangeListener());
        //把Fragment添加到List集合里面
        list = new ArrayList<>();
        dashiQimingFragment = new DashiQimingFragment();
        chooseNameFragment = new ChooseNameFragment();
        qimingBaziFragment = new QimingBaziFragment();

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
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        Log.d("name-->>", name);
    }

    @Override
    protected void setListener() {

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
            switch (arg0) {//状态改变时底部对应的字体颜色改变
                case 0:
                    tv_qiming_bazi_title.setTextColor(Color.GREEN);
                    tv_qiming_choose_title.setTextColor(Color.BLACK);
                    tv_qiming_dashi_title.setTextColor(Color.BLACK);
                    break;
                case 1:
                    tv_qiming_bazi_title.setTextColor(Color.BLACK);
                    tv_qiming_choose_title.setTextColor(Color.GREEN);
                    tv_qiming_dashi_title.setTextColor(Color.BLACK);
                    break;
                case 2:
                    tv_qiming_bazi_title.setTextColor(Color.BLACK);
                    tv_qiming_choose_title.setTextColor(Color.BLACK);
                    tv_qiming_dashi_title.setTextColor(Color.GREEN);
                    break;
            }

        }
    }
}
