package com.example.qicheng.suanming.ui;

import android.content.Intent;
import android.util.Log;
import android.util.Size;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.qicheng.suanming.bean.DaShiKeFuBean;
import com.example.qicheng.suanming.ui.fragment.CommunityFragment;
import com.example.qicheng.suanming.ui.fragment.MasterListFragment;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.common.ActivityManager;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.ui.fragment.bazi.BaziFragment;
import com.example.qicheng.suanming.ui.fragment.home.HomeFragment;
import com.example.qicheng.suanming.ui.fragment.mine.MineFragment;
import com.example.qicheng.suanming.ui.fragment.qiming.QimingFragment;

import butterknife.BindView;

import com.example.qicheng.suanming.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

//    @BindView(R.id.scroll_root)
//    ScrollView scroll_root;

    public static MainActivity instances;
    private FragmentManager manager;

    // 当前选中的fragment
    private int pos = 0;

    HomeFragment homeFragment;  //首页
    BaziFragment baziFragment;//八字
    QimingFragment qimingFragment;//起名
    MineFragment mineFragment;//起名
    CommunityFragment communityFragment;//vip
    MasterListFragment masterListFragment;//大师列表

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
                .addItem(new BottomNavigationItem(R.mipmap.home, "首页").setInActiveColor(R.color.black).setActiveColorResource(R.color.qiming_select_color).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.home1)))
                .addItem(new BottomNavigationItem(R.mipmap.index_dashiliebiao, "大师列表").setInActiveColor(R.color.black).setActiveColorResource(R.color.qiming_select_color).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.index_dashiliebiaohuise)))
                .addItem(new BottomNavigationItem(R.mipmap.bazi, "八字排盘").setInActiveColor(R.color.black).setActiveColorResource(R.color.qiming_select_color).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.bazi1)))
                .addItem(new BottomNavigationItem(R.mipmap.qiming, "起名").setInActiveColor(R.color.black).setActiveColorResource(R.color.qiming_select_color).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.qiming1)))
                .addItem(new BottomNavigationItem(R.mipmap.index_shequ, "VIP").setInActiveColor(R.color.black).setActiveColorResource(R.color.qiming_select_color).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.index_shequhuise)))
                .addItem(new BottomNavigationItem(R.mipmap.mine, "个人中心").setInActiveColor(R.color.black).setActiveColorResource(R.color.qiming_select_color).setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.mine1)))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setAutoHideEnabled(true);
        manager = getSupportFragmentManager();

        homeFragment = new HomeFragment(new skipFragment() {
            @Override
            public void skipQiming() {
                hideAllFragment();
                manager.beginTransaction().show(qimingFragment).commit();
                bottomNavigationBar.selectTab(2);
            }

            @Override
            public void skipBazi() {
                hideAllFragment();
                manager.beginTransaction().show(baziFragment).commit();
                bottomNavigationBar.selectTab(1);
            }

            @Override
            public void skipMine() {
                hideAllFragment();
                manager.beginTransaction().show(mineFragment).commit();
            }
        });
        baziFragment = new BaziFragment();
        qimingFragment = new QimingFragment();
        mineFragment = new MineFragment();
        communityFragment = new CommunityFragment();
        masterListFragment = new MasterListFragment();

        manager.beginTransaction()
                .add(R.id.container, homeFragment, HomeFragment.class.getName())
                .add(R.id.container, baziFragment, BaziFragment.class.getName())
                .add(R.id.container, communityFragment, CommunityFragment.class.getName())
                .add(R.id.container, masterListFragment, MasterListFragment.class.getName())
                .add(R.id.container, qimingFragment, QimingFragment.class.getName())
                .add(R.id.container, mineFragment, MineFragment.class.getName())
                .hide(baziFragment)
                .hide(qimingFragment)
                .hide(mineFragment)
                .hide(communityFragment)
                .hide(masterListFragment)
                .commit();
    }

    @Override
    protected void initData() {

        setDate();
        //获取大师的信息

        getDaShiInfo();
    }

    private void getDaShiInfo() {
        Map<String, String> map = new HashMap<>();
        OkHttpManager.request(Constants.getApi.GETKEFUINFO, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("GETKEFUINFO---->>", info.getRetDetail());
                try {
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

    private void setDate() {
        Map map = new HashMap();
        OkHttpManager.request(Constants.getApi.GETNONGLIDATE, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("GETNONGLIDATE---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.toString());
                    JSONObject jar = jsonObject.getJSONObject("data");
                    String year = jar.getString("year");
                    String month = jar.getString("month");
                    String day = jar.getString("day");
                    String n_year = jar.getString("n_year");
                    String n_month = jar.getString("n_month");
                    String n_day = jar.getString("n_day");
                    homeFragment.setDate(year, month, day, n_year + n_month + n_day);
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

    @Override
    protected void setListener() {
        bottomNavigationBar.setTabSelectedListener(this);
    }

    private void hideAllFragment() {
        manager.beginTransaction().hide(qimingFragment)
                .hide(baziFragment)
                .hide(mineFragment)
                .hide(homeFragment)
                .hide(communityFragment)
                .hide(masterListFragment)
                .commit();
    }

    @Override
    public void onTabSelected(int position) {
        //被选中时
        Log.e("onTabSelected", String.valueOf(position));

        hideAllFragment();
//        UpdataokHttop();
        //被选中时
        LogUtils.e("onTabSelected", String.valueOf(position));
        pos = position;
        FragmentTransaction transaction = manager.beginTransaction();
        switch (position) {
            case 0:
                hideTitleBar();
                transaction.show(homeFragment);
                break;
            case 1:
                hideTitleBar();
                transaction.show(masterListFragment);
                break;
            case 2:
                hideTitleBar();
                transaction.show(baziFragment);
//                setStatusBarColor(ContextCompat.getColor(this, R.color.dashi_info_statusbar));
                break;
            case 3:
                hideTitleBar();
                transaction.show(qimingFragment);
                break;
            case 4:
//                hideTitleBar();
                transaction.show(communityFragment);
                setTitleText("VIP服务");
                hideExitImg();
                break;
            case 5:
                hideTitleBar();
                transaction.show(mineFragment);
//                setStatusBarColor(ContextCompat.getColor(this, R.color.white));
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
                transaction.hide(masterListFragment);
                break;
            case 2:
                transaction.hide(baziFragment);
                break;
            case 3:
                transaction.hide(qimingFragment);
                break;
            case 5:
                transaction.hide(mineFragment);
                break;
            case 4:
                transaction.hide(communityFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabReselected(int position) {
        //重复选中
        Log.e("onTabReselected", String.valueOf(position));
    }

    public interface skipFragment {
        void skipQiming();

        void skipBazi();

        void skipMine();
    }

    public interface hideBottom {
        void hide();

        void show();

        Size getWH();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        bottomNavigationBar.selectTab(1);
    }

    private long clickTime = 0; // 第一次点击的时间

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            MobclickAgent.onKillProcess(this);
            this.finish();
        }
    }
}
