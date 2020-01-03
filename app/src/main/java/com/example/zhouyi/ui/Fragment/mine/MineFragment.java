package com.example.zhouyi.ui.Fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.zhouyi.R;
import com.example.zhouyi.base.BaseFragment;

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

    @OnClick({R.id.about_view, R.id.feedback_view, R.id.user_view, R.id.kefu_view, R.id.order_view,R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_view:
                Log.d(MineFragment.class.getSimpleName(), "user_view");
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