package com.example.qicheng.suanming.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.adapter.DashiListAdapter;
import com.example.qicheng.suanming.adapter.DashiSkillAdapter;
import com.example.qicheng.suanming.base.BaseFragment;
import com.example.qicheng.suanming.bean.DashiInfoBean;
import com.example.qicheng.suanming.bean.DashiListBean;
import com.example.qicheng.suanming.bean.DashiSkillListBean;
import com.example.qicheng.suanming.contract.MasterListContract;
import com.example.qicheng.suanming.presenter.MasterListPresenter;
import com.example.qicheng.suanming.widget.CustomListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sakura on 2020-04-15 10:29
 */
public class MasterListFragment extends BaseFragment implements MasterListContract.View, AdapterView.OnItemClickListener {

    @BindView(R.id.lv_dashi)
    CustomListView lv_dashi;

//    @BindView(R.id.tv_normal)
//    TextView tv_normal;

    @BindView(R.id.sp_xiala)
    Spinner sp_xiala;

    @BindView(R.id.tv_attention)
    TextView tv_attention;

    @BindView(R.id.tv_order)
    TextView tv_order;

    private DashiListAdapter adapter;
    private MasterListContract.Presenter mPresenter;

    private List<DashiInfoBean.DataBean.DashiBean> dashiList;

    private int type = 1; //1 默认排序 2 订单排序 3 关注排序 4擅长技能
    private int page = 1;
    private int per_page = 15;
    private int skill_pos = 1;
    private boolean isKeyword = false;
    private String keyword = "";

    private List<DashiSkillListBean.DataBean> skillList;

    @Override
    protected int setLayoutId() {
        return R.layout.fg_master;
    }

    @Override
    protected void initView() {
        mPresenter = new MasterListPresenter(this);
    }

    @Override
    protected void initData() {
        //获取技能
        mPresenter.getSkillList(new HashMap());
        getDashiList();
    }

    public void getDashiList() {
        //get  大师 list
        Map<String, String> map = new HashMap<>();
        map.put("type", type + "");
        map.put("page", page + "");
        map.put("per_page", per_page + "");
        if (isKeyword) {
            map.put("keyword", keyword);
            isKeyword = false;
        }
        if (type == 4) {
            map.put("skill", skill_pos + "");
        }
        mPresenter.getDaShiList(map);
        loadingDialog.showDialog();
    }

    /**
     * 展示搜索列表
     */
    public void showSearchList(String str) {
        isKeyword = true;
        keyword = str;
    }

    public void initSpinner() {
        DashiSkillAdapter skillAdapter = new DashiSkillAdapter(mContext, skillList);
        sp_xiala.setAdapter(skillAdapter);
        sp_xiala.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = 4;
                skill_pos = skillList.get(position).getId();
                getDashiList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void updateIndex() {
        switch (type) {
            case 1:
//                tv_normal.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                tv_attention.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tv_order.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                break;
            case 2:
//                tv_normal.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tv_attention.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                tv_order.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                break;
            case 3:
//                tv_normal.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tv_attention.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tv_order.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                break;
        }
    }

    @OnClick({R.id.tv_attention, R.id.tv_order})
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_normal:
//                type = 1;
//                break;
            case R.id.tv_attention:
                type = 3;
                break;
            case R.id.tv_order:
                type = 2;
                break;
        }
        this.initData();
    }

    @Override
    public void setPresenter(MasterListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getDaShiListSuc(String data) {
        Log.d("getDaShiListSuc-->>", data);
        DashiListBean dashiInfo = new Gson().fromJson(data, DashiListBean.class);
        DashiListBean.DataBean result = dashiInfo.getData();

        if (dashiInfo.getCode().equals("200")) {
            loadingDialog.dismiss();
            dashiList = result.getList();

            adapter = new DashiListAdapter(mContext, dashiList);
            lv_dashi.setAdapter(adapter);
            lv_dashi.setOnItemClickListener(this);
        } else {
            loadingDialog.failed(dashiInfo.getMsg());
//            ToastUtils.showShortToast("加载失败");
        }
    }

    @Override
    public void getSkillListSuc(String data) {
        DashiSkillListBean bean = new Gson().fromJson(data, DashiSkillListBean.class);
        skillList = bean.getData();
        initSpinner();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(mContext, DashiInfoActivity.class);
//        intent.putExtra("id", dashiList.get(position).getId() + "");
//        startActivity(intent);
    }
}
