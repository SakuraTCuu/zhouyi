package com.example.zhouyi.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    public static final String PRV_SELINDEX = "PREV_SELINDEX";
    public final static int TASK_START = 0;
    public final static int TASK_1 = 1;
    public final static int TASK_2 = 2;
    public final static int TASK_END   = 3;

    public static final int BASE_STATIC = 0x0000;
    public static final int BASE_STATIC_1 = 0x0001;
    public static final int BASE_STATIC_2 = 0x0002;
    public static final int BASE_STATIC_3 = 0x0003;
    public static final int BASE_STATIC_4 = 0x0004;
    public static final int BASE_END = 0x0005;

    public Context mContext;
//    private BaseHandler mainHandler;
//    private LoadingDialog loadingDialog;//加载提示框
    private Unbinder unbinder;
    public Bundle savedInstanceState;       //MainActivity防止出现fragment重叠时使用

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(setLayoutId(),container,false);
        //绑定控件
        unbinder = ButterKnife.bind(this,view);
        mContext = getActivity();
        initView();
        initData();
        return view;
    }

    protected abstract int setLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
//        EventBus.getDefault().unregister(this);
    }
}
