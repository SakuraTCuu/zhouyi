package com.example.qicheng.suanming.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.adapter.DashiDetailCommentAdapter;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.bean.DashiDetailCommentBean;
import com.example.qicheng.suanming.contract.MoreEvaluateContract;
import com.example.qicheng.suanming.presenter.MoreEvaluatePresenter;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MoreEvaluateActivity extends BaseActivity implements MoreEvaluateContract.View, AdapterView.OnItemClickListener {

    @BindView(R.id.lv_user_evaluate)
    ListView lv_user_evaluate;

    private String ds_id;

    private MoreEvaluatePresenter mPresenter;

    private DashiDetailCommentAdapter commentAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_user_evaluate;
    }

    @Override
    protected void initView() {

        setTitleText("用户评价");

        mPresenter = new MoreEvaluatePresenter(this);
        mPresenter.start();

        ds_id = getIntent().getStringExtra("id");

        Map<String, String> commonMap = new HashMap();
        commonMap.put("type", "1");
        commonMap.put("releate_id", ds_id);
//        commonMap.put("per_page", "3");//只去三条
        mPresenter.getCommentInfo(commonMap);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void getCommentInfoSuc(String data) {

        DashiDetailCommentBean bean = new Gson().fromJson(data, DashiDetailCommentBean.class);
        List<DashiDetailCommentBean.DataBean.ListBean> dashiInfo = bean.getData().getList();
        commentAdapter = new DashiDetailCommentAdapter(mContext, dashiInfo);
        lv_user_evaluate.setAdapter(commentAdapter);
        lv_user_evaluate.setOnItemClickListener(this);
    }

    @Override
    public void setPresenter(MoreEvaluateContract.Presenter presenter) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
