package com.example.qicheng.suanming.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.adapter.VIPListDataAdapter;
import com.example.qicheng.suanming.base.BaseFragment;
import com.example.qicheng.suanming.bean.VIPHomeDataBean;
import com.example.qicheng.suanming.bean.VIPListDataBean;
import com.example.qicheng.suanming.contract.CommunityContract;
import com.example.qicheng.suanming.presenter.CommunityPresenter;
import com.example.qicheng.suanming.ui.Article.ArticleActivity;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.example.qicheng.suanming.widget.CustomListView;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sakura on 2020-04-15 10:31
 */
public class CommunityFragment extends BaseFragment implements CommunityContract.View, AdapterView.OnItemClickListener {

    @BindView(R.id.lv_vip_list)
    CustomListView lv_vip_list;

    @BindView(R.id.iv_vip_1)
    ImageView iv_vip_1;
    @BindView(R.id.iv_vip_2)
    ImageView iv_vip_2;
    @BindView(R.id.iv_vip_3)
    ImageView iv_vip_3;
    @BindView(R.id.iv_vip_4)
    ImageView iv_vip_4;
    @BindView(R.id.iv_vip_5)
    ImageView iv_vip_5;
    @BindView(R.id.tv_vip_1)
    TextView tv_vip_1;
    @BindView(R.id.tv_vip_2)
    TextView tv_vip_2;
    @BindView(R.id.tv_vip_3)
    TextView tv_vip_3;
    @BindView(R.id.tv_vip_4)
    TextView tv_vip_4;
    @BindView(R.id.tv_vip_5)
    TextView tv_vip_5;

    private CommunityContract.Presenter mPresenter;

    private VIPListDataAdapter adapter;
    private List<VIPListDataBean.DataBean> result;

    private List<VIPHomeDataBean.DataBean> vipListInfo;

    private int clickId = 0;

    @Override
    protected int setLayoutId() {
        return R.layout.fg_community;
    }

    @Override
    protected void initView() {
        mPresenter = new CommunityPresenter(this);
    }

    @Override
    protected void initData() {
        Map<String, String> map = new HashMap<>();
        mPresenter.getListData(map);

        Map newMap = new HashMap();
        mPresenter.getInfoData(newMap);
    }

    @Override
    public void setPresenter(CommunityContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getListDataSuc(String data) {
        Log.d("getListDataSuc-->", data);
        VIPListDataBean bean = new Gson().fromJson(data, VIPListDataBean.class);
        result = bean.getData();
        try {
            Glide.with(mContext).load(result.get(0).getThumb()).into(iv_vip_1);
            Glide.with(mContext).load(result.get(1).getThumb()).into(iv_vip_2);
            Glide.with(mContext).load(result.get(2).getThumb()).into(iv_vip_3);
            Glide.with(mContext).load(result.get(3).getThumb()).into(iv_vip_4);
            Glide.with(mContext).load(result.get(4).getThumb()).into(iv_vip_5);

            tv_vip_1.setText(result.get(0).getName());
            tv_vip_2.setText(result.get(1).getName());
            tv_vip_3.setText(result.get(2).getName());
            tv_vip_4.setText(result.get(3).getName());
            tv_vip_5.setText(result.get(4).getName());
        } catch (Exception e) {
            // 固定好五个
            ToastUtils.showShortToast("服务器错误");
        }
    }

    @Override
    public void getInfoDataSuc(String data) {
        Log.d("getInfoDataSuc-->", data);
        VIPHomeDataBean bean = new Gson().fromJson(data, VIPHomeDataBean.class);
        vipListInfo = bean.getData();

        adapter = new VIPListDataAdapter(mContext, vipListInfo);
        lv_vip_list.setAdapter(adapter);
        lv_vip_list.setOnItemClickListener(this);
    }

    @OnClick({R.id.ll_vip_1, R.id.ll_vip_2, R.id.ll_vip_3, R.id.ll_vip_4, R.id.ll_vip_5})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.ll_vip_1:
                clickId = 0;
                break;
            case R.id.ll_vip_2:
                clickId = 1;
                break;
            case R.id.ll_vip_3:
                clickId = 2;
                break;
            case R.id.ll_vip_4:
                clickId = 3;
                break;
            case R.id.ll_vip_5:
                clickId = 4;
                break;
        }
        gotoDetail();
    }

    public void gotoDetail() {
        Intent i = new Intent(mContext, ArticleActivity.class);
        i.putExtra("clickId", result.get(clickId).getId() + "");
        i.putExtra("title", result.get(clickId).getName());
        Bundle b = new Bundle();
        b.putSerializable("toplist", (Serializable) result);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(mContext, ArticleDetailActivity.class);
//        intent.putExtra("name", result.get(position).getName());
//        intent.putExtra("id", vipListInfo.get(position).getId() + "");
//        startActivity(intent);
    }
}
