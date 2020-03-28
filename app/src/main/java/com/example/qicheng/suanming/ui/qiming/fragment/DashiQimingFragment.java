package com.example.qicheng.suanming.ui.qiming.fragment;

import android.widget.TextView;

import com.example.qicheng.suanming.bean.DaShiKeFuBean;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseFragment;
import com.example.qicheng.suanming.common.Constants;

import butterknife.BindView;

public class DashiQimingFragment extends BaseFragment {

    @BindView(R.id.tv_qiming_dashi_name)
    TextView tv_qiming_dashi_name;
    @BindView(R.id.tv_qiming_dashi_desc)
    TextView tv_qiming_dashi_desc;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_dashi_qiming;
    }

    @Override
    protected void initView() {

        DaShiKeFuBean kefuInfo = Constants.kefuInfo;

        tv_qiming_dashi_name.setText(kefuInfo.getDashi_name());
        tv_qiming_dashi_desc.setText(kefuInfo.getDashi_desc());
    }

    @Override
    protected void initData() {

    }
}
