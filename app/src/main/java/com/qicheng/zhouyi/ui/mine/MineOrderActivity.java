package com.qicheng.zhouyi.ui.mine;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.adapter.MineOrderAdapter;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.MineOrderBean;

import java.util.ArrayList;

import butterknife.BindView;

public class MineOrderActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_order)
    ListView lv_order;

    private ArrayList<MineOrderBean> data = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_order;
    }

    @Override
    protected void initView() {
        showTitleBar();
        setTitleText("我的订单");
        //填充假数据
        for (int i = 0; i < 10; i++) {
            MineOrderBean bean = new MineOrderBean();
            bean.setOrderCode("1234569845120232323");
            bean.setOrderTime("2019-12-21 08:30");
            bean.setOrderTitle("[八字合婚] xxxxx 公历 2019-12-31");
            data.add(bean);
        }

        lv_order.setAdapter(new MineOrderAdapter(data, mContext));
        lv_order.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(mContext, "你点击了第" + position + "项", Toast.LENGTH_SHORT).show();
    }
}
