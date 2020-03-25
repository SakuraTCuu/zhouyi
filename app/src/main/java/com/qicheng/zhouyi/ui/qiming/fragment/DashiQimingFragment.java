package com.qicheng.zhouyi.ui.qiming.fragment;

import android.widget.TextView;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseFragment;
import com.qicheng.zhouyi.bean.DaShiKeFuBean;
import com.qicheng.zhouyi.common.Constants;

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
