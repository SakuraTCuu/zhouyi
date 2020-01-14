package com.qicheng.zhouyi.ui;

import android.content.Intent;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.common.ActivityManager;
import com.qicheng.zhouyi.ui.fragment.bazi.BaziFragment;
import com.qicheng.zhouyi.ui.fragment.home.HomeFragment;
import com.qicheng.zhouyi.ui.fragment.mine.MineFragment;
import com.qicheng.zhouyi.ui.fragment.qiming.QimingFragment;

import butterknife.BindView;

import com.qicheng.zhouyi.utils.LogUtils;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    public static MainActivity instances;
    private FragmentManager manager;

    // 当前选中的fragment
    private int pos = 0;

    HomeFragment homeFragment;  //首页
    BaziFragment baziFragment;//八字
    QimingFragment qimingFragment;//起名
    MineFragment mineFragment;//起名

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ActivityManager.getInstance().push(this);

        hideTitleBar();

        instances = this;
        //设置Mode
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        //设置背景效果
////        215,230,239
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor("#D7E6EF");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.home, "首页").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.home1)))
                .addItem(new BottomNavigationItem(R.mipmap.bazi, "八字排盘").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.bazi1)))
                .addItem(new BottomNavigationItem(R.mipmap.qiming, "大师起名").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.qiming1)))
                .addItem(new BottomNavigationItem(R.mipmap.mine, "个人中心").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.mine1)))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setAutoHideEnabled(true);
        manager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        baziFragment = new BaziFragment();
        qimingFragment = new QimingFragment();
        mineFragment = new MineFragment();

        manager.beginTransaction().add(R.id.container, homeFragment, HomeFragment.class.getName())
                .add(R.id.container, baziFragment, BaziFragment.class.getName())
                .add(R.id.container, qimingFragment, QimingFragment.class.getName())
                .add(R.id.container, mineFragment, MineFragment.class.getName())
                .hide(baziFragment)
                .hide(qimingFragment)
                .hide(mineFragment)
                .commit();
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        //被选中时
        Log.e("onTabSelected", String.valueOf(position));

//        UpdataokHttop();
        //被选中时
        LogUtils.e("onTabSelected", String.valueOf(position));
        pos = position;
        FragmentTransaction transaction = manager.beginTransaction();
        switch (position) {
            case 0:
                transaction.show(homeFragment);
                break;
            case 1:
                transaction.show(baziFragment);
                break;
            case 2:
                transaction.show(qimingFragment);
                break;
            case 3:
                transaction.show(mineFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {
        Log.e("onTabUnselected", String.valueOf(position));
        FragmentTransaction transaction = manager.beginTransaction();
        switch (position) {
            case 0:
                transaction.hide(homeFragment);
                break;
            case 1:
                transaction.hide(baziFragment);
                break;
            case 2:
                transaction.hide(qimingFragment);
                break;
            case 3:
                transaction.hide(mineFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabReselected(int position) {
        //重复选中
        Log.e("onTabReselected", String.valueOf(position));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        bottomNavigationBar.selectTab(1);
    }
}
