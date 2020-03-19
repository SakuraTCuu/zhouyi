package com.qicheng.zhouyi.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.adapter.MineOrderAdapter;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.MineOrderBean;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.qicheng.zhouyi.ui.mouseYear.MouseYearActivity;
import com.qicheng.zhouyi.ui.webView.NamePayActivity;
import com.qicheng.zhouyi.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MineOrderActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_order)
    ListView lv_order;

    @BindView(R.id.tv_noOrder)
    TextView tv_noOrder;

    private String TAG = MineOrderActivity.class.getSimpleName();
    private ArrayList<MineOrderBean> data = new ArrayList<>();
    private int page=1;

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

        Map<String,String> map = new HashMap<>();
        map.put("page",page+"");

        Log.d("TAG", "tag-->>"+TAG);
        Log.d(TAG, data.toString());
        this.getDataFromServer(map);

    }

    public void setData(){
        Log.d("setData", data.toString());
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

    public void getDataFromServer(Map map){
        Log.d("map---->>", map.toString());
        OkHttpManager.request(Constants.getApi.GETORDERLIST, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info22---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>",  jsonObject.toString());
                    boolean code = jsonObject.getBoolean("code");
                    String msg = jsonObject.getString("msg");
                    JSONArray data = jsonObject.getJSONArray("data");
                    if(code){
                        ToastUtils.showShortToast(msg);
                        tv_noOrder.setVisibility(View.VISIBLE);
                    }else{
                        tv_noOrder.setVisibility(View.GONE);
                        //处理data
                        setData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("e---->>", e.toString());
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("info---->>", info.toString());
                try {
                    String result = new JSONObject(info.getRetDetail()).getString("msg");
                    ToastUtils.showShortToast(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
