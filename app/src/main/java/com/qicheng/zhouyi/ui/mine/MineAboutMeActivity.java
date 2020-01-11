package com.qicheng.zhouyi.ui.mine;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;

public class MineAboutMeActivity extends BaseActivity {
    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_aboutme;
    }

    @Override
    protected void initView() {

        showTitleBar();
        setTitleText("关于我们");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }
}
