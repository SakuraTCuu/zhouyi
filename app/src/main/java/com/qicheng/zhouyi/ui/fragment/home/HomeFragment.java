package com.qicheng.zhouyi.ui.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.base.BaseFragment;
import com.qicheng.zhouyi.ui.MainActivity;
import com.qicheng.zhouyi.ui.bazi.BaziHehunActivity;
import com.qicheng.zhouyi.ui.bazijingpi.BaziJingPiActivity;
import com.qicheng.zhouyi.ui.caiyun.CaiyunActivity;
import com.qicheng.zhouyi.ui.mouseYear.MouseYearActivity;
import com.qicheng.zhouyi.ui.yuelao.YuelaoActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.ll_top_bazi)
    LinearLayout ll_top_bazi;
    @BindView(R.id.ll_top_qiming)
    LinearLayout ll_top_qiming;
    @BindView(R.id.ll_top_yuelao)
    LinearLayout ll_top_yuelao;
    @BindView(R.id.ll_top_zhougong)
    LinearLayout ll_top_zhougong;

    @BindView(R.id.ll_mid_caiyun)
    LinearLayout ll_mid_caiyun;
    @BindView(R.id.ll_mid_hehun)
    LinearLayout ll_mid_hehun;
    @BindView(R.id.ll_mid_qiming)
    LinearLayout ll_mid_qiming;
    @BindView(R.id.ll_mid_weiji)
    LinearLayout ll_mid_weiji;
    @BindView(R.id.ll_mid_yinyuan)
    LinearLayout ll_mid_yinyuan;
    @BindView(R.id.ll_mid_yunshi)
    LinearLayout ll_mid_yunshi;

    @BindView(R.id.ll_bottom_aiqing)
    LinearLayout ll_bottom_aiqing;
    @BindView(R.id.ll_bottom_caifu)
    LinearLayout ll_bottom_caifu;
    @BindView(R.id.ll_bottom_ganqing)
    LinearLayout ll_bottom_ganqing;
    @BindView(R.id.ll_bottom_hehun)
    LinearLayout ll_bottom_hehun;
    @BindView(R.id.ll_bottom_taohua)
    LinearLayout ll_bottom_taohua;
    @BindView(R.id.ll_bottom_yunshi)
    LinearLayout ll_bottom_yunshi;


    public HomeFragment() {
        super();
    }

    private MainActivity.skipFragment listener;


    public HomeFragment(MainActivity.skipFragment skipFragment) {
        this.listener = skipFragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_top_bazi, R.id.ll_top_qiming, R.id.ll_top_yuelao, R.id.ll_top_zhougong,
            R.id.ll_mid_caiyun,
            R.id.ll_mid_hehun,
            R.id.ll_mid_qiming, R.id.ll_mid_weiji, R.id.ll_mid_yinyuan,
            R.id.ll_mid_yunshi, R.id.ll_bottom_aiqing, R.id.ll_bottom_caifu, R.id.ll_bottom_ganqing,
            R.id.ll_bottom_hehun,
            R.id.ll_bottom_taohua, R.id.ll_bottom_yunshi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_top_bazi:
                StartActivity(BaziJingPiActivity.class);
                break;
            case R.id.ll_top_qiming:
                StartQimingFragment();
                break;
            case R.id.ll_top_yuelao:
                StartActivity(YuelaoActivity.class);
                break;
            case R.id.ll_top_zhougong:
                StartActivity(YuelaoActivity.class);
                break;
            case R.id.ll_mid_caiyun:
                StartActivity(CaiyunActivity.class);
                break;
            case R.id.ll_mid_hehun:
                StartActivity(BaziHehunActivity.class);
                break;
            case R.id.ll_mid_qiming:
                StartQimingFragment();
                break;
            case R.id.ll_mid_weiji:
                StartActivity(MouseYearActivity.class);
                break;
            case R.id.ll_mid_yinyuan:
                StartActivity(YuelaoActivity.class);
                break;
            case R.id.ll_mid_yunshi:
                StartActivity(CaiyunActivity.class);
                break;
            case R.id.ll_bottom_aiqing:
                StartActivity(YuelaoActivity.class);
                break;
            case R.id.ll_bottom_caifu:
                StartActivity(CaiyunActivity.class);
                break;
            case R.id.ll_bottom_ganqing:
                StartActivity(YuelaoActivity.class);
                break;
            case R.id.ll_bottom_hehun:
                StartActivity(BaziHehunActivity.class);
                break;
            case R.id.ll_bottom_taohua:
                StartActivity(YuelaoActivity.class);
                break;
            case R.id.ll_bottom_yunshi:
                StartActivity(BaziJingPiActivity.class);
                break;
        }
    }

    private void StartActivity(Class cls) {
        startActivity(new Intent(mContext, cls));
    }

    private void StartBaziFragment() {
        listener.skipBazi();
    }

    private void StartQimingFragment() {
        listener.skipQiming();
    }
}