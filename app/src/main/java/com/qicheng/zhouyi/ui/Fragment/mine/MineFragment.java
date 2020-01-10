package com.qicheng.zhouyi.ui.Fragment.mine;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseFragment;
import com.qicheng.zhouyi.ui.MineUserActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {

    @BindView(R.id.user_view)
    LinearLayout userView;

    @BindView(R.id.order_view)
    LinearLayout orderView;

    @BindView(R.id.feedback_view)
    LinearLayout feedbackView;

    @BindView(R.id.kefu_view)
    LinearLayout kefuView;

    @BindView(R.id.about_view)
    LinearLayout aboutView;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.about_view, R.id.feedback_view, R.id.user_view, R.id.kefu_view, R.id.order_view, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_view:
                startActivity(new Intent(mContext, MineUserActivity.class));
                break;
            case R.id.order_view:
                Log.d(MineFragment.class.getSimpleName(), "order_view");
                break;
            case R.id.feedback_view:
                Log.d(MineFragment.class.getSimpleName(), "feedback_view");
                break;
            case R.id.kefu_view:
                Log.d(MineFragment.class.getSimpleName(), "kefu_view");
                break;
            case R.id.about_view:
                Log.d(MineFragment.class.getSimpleName(), "about_view");
                break;
            case R.id.login_btn:
                Log.d(MineFragment.class.getSimpleName(), "login_btn");
                break;
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}