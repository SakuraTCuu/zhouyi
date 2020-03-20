package com.qicheng.zhouyi.ui.fragment.mine;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseFragment;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.ui.LoginActivity;
import com.qicheng.zhouyi.ui.mine.MineAboutMeActivity;
import com.qicheng.zhouyi.ui.mine.MineBeiyongActivity;
import com.qicheng.zhouyi.ui.mine.MineFeedbackActivity;
import com.qicheng.zhouyi.ui.mine.MineKeFuActivity;
import com.qicheng.zhouyi.ui.mine.MineOrderActivity;
import com.qicheng.zhouyi.ui.mine.MineUserActivity;

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

    @OnClick({R.id.about_view, R.id.feedback_view, R.id.user_view, R.id.kefu_view, R.id.order_view, R.id.logout_btn, R.id.user_beiyong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_view:
                gotoMineUserAcitivty();
                break;
            case R.id.user_beiyong:
                gotoMineBeiyongAcitivty();
                break;
            case R.id.order_view:
                gotoMineOrderAcitivty();
                break;
            case R.id.feedback_view:
                gotoFeedbackAcitivty();
                break;
            case R.id.kefu_view:
                gotoKeFuAcitivty();
                break;
            case R.id.about_view:
                gotoAboutMeAcitivty();
                break;
            case R.id.logout_btn:
                //退出
                logoutBtn();
                break;
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void logoutBtn() {
        //清除存储的数据
        //跳转到登录界面
        Constants.removeUserInfo();
        Constants.isLogin = false;
        startActivity(new Intent(mContext, LoginActivity.class));
        getActivity().finish();
    }

    public void gotoKeFuAcitivty() {
        startActivity(new Intent(mContext, MineKeFuActivity.class));
    }

    public void gotoFeedbackAcitivty() {
        startActivity(new Intent(mContext, MineFeedbackActivity.class));
    }

    public void gotoAboutMeAcitivty() {
        startActivity(new Intent(mContext, MineAboutMeActivity.class));
    }

    public void gotoMineOrderAcitivty() {
        startActivity(new Intent(mContext, MineOrderActivity.class));
    }

    public void gotoMineUserAcitivty() {
        startActivity(new Intent(mContext, MineUserActivity.class));
    }

    public void gotoMineBeiyongAcitivty() {
        startActivity(new Intent(mContext, MineBeiyongActivity.class));
    }

}