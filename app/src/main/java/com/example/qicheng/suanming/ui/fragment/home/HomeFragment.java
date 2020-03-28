package com.example.qicheng.suanming.ui.fragment.home;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qicheng.suanming.base.BaseFragment;
import com.example.qicheng.suanming.ui.MainActivity;
import com.example.qicheng.suanming.ui.bazi.BaziHehunActivity;
import com.example.qicheng.suanming.ui.bazijingpi.BaziJingPiActivity;
import com.example.qicheng.suanming.ui.caiyun.CaiyunActivity;
import com.example.qicheng.suanming.ui.jiemeng.JiemengActivity;
import com.example.qicheng.suanming.ui.mouseYear.MouseYearActivity;
import com.example.qicheng.suanming.ui.yuelao.YuelaoActivity;
import com.example.qicheng.suanming.R;

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

    @BindView(R.id.iv_home_bazi)
    ImageView iv_home_bazi;

    @BindView(R.id.tv_nongli_date)
    TextView tv_nongli_date;

    @BindView(R.id.tv_gongli_day)
    TextView tv_gongli_day;

    @BindView(R.id.tv_gongli_year)
    TextView tv_gongli_year;

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

    public void setDate(String year, String month, String day, String n_date) {

        tv_nongli_date.setText(n_date);
        tv_gongli_day.setText(day);
        tv_gongli_year.setText(year + "/" + month);

    }

    @OnClick({R.id.ll_top_bazi, R.id.ll_top_qiming, R.id.ll_top_yuelao, R.id.ll_top_zhougong,
            R.id.ll_mid_caiyun,
            R.id.ll_mid_hehun,
            R.id.ll_mid_qiming, R.id.ll_mid_weiji, R.id.ll_mid_yinyuan,
            R.id.ll_mid_yunshi, R.id.ll_bottom_aiqing, R.id.ll_bottom_caifu, R.id.ll_bottom_ganqing,
            R.id.ll_bottom_hehun,
            R.id.ll_bottom_taohua, R.id.ll_bottom_yunshi, R.id.iv_home_bazi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_top_qiming:
                StartQimingFragment();
                break;
            case R.id.ll_top_zhougong:
                StartActivity(JiemengActivity.class);
                break;
            case R.id.ll_mid_caiyun:
                StartActivity(CaiyunActivity.class);
                break;
            case R.id.ll_mid_qiming:
                StartQimingFragment();
                break;
            case R.id.ll_mid_weiji:
                StartActivity(MouseYearActivity.class);
                break;
            case R.id.ll_mid_yinyuan:
            case R.id.ll_bottom_taohua:
            case R.id.ll_bottom_aiqing:
            case R.id.ll_bottom_ganqing:
            case R.id.ll_top_yuelao:
                StartActivity(YuelaoActivity.class);
                break;
            case R.id.ll_mid_yunshi:
            case R.id.ll_bottom_caifu:
                StartActivity(CaiyunActivity.class);
                break;
            case R.id.ll_bottom_hehun:
            case R.id.ll_mid_hehun:
                StartActivity(BaziHehunActivity.class);
                break;
            case R.id.ll_bottom_yunshi:
            case R.id.iv_home_bazi:
            case R.id.ll_top_bazi:
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