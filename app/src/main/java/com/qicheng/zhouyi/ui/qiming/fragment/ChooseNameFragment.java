package com.qicheng.zhouyi.ui.qiming.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.adapter.ChooseNameListAdapter;
import com.qicheng.zhouyi.base.BaseFragment;
import com.qicheng.zhouyi.bean.ChooseNameBean;
import com.qicheng.zhouyi.bean.NameListItemBean;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.qicheng.zhouyi.common.ResourcesManager;
import com.qicheng.zhouyi.ui.bazijingpi.BaziJingPiActivity;
import com.qicheng.zhouyi.ui.webView.NamePayActivity;
import com.qicheng.zhouyi.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseNameFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.lv_namelist)
    ListView lv_namelist;

    @BindView(R.id.tv_namebar_normal)
    TextView tv_namebar_normal;
    @BindView(R.id.tv_namebar_small)
    TextView tv_namebar_small;
    @BindView(R.id.tv_namebar_big)
    TextView tv_namebar_big;

    @BindView(R.id.tv_first_word)
    TextView tv_first_word;
    @BindView(R.id.tv_second_word)
    TextView tv_second_word;
    @BindView(R.id.tv_third_word)
    TextView tv_third_word;

    @BindView(R.id.iv_collect)
    ImageView iv_collect;
    @BindView(R.id.tv_collect)
    TextView tv_collect;

    private List<ChooseNameBean> data = new ArrayList<>();
    private String userData;
    private ChooseNameListAdapter adapter;
    private JSONArray nameList;
    private int currentPos;

    public ChooseNameFragment(){
        super();
    }

    public ChooseNameFragment(JSONArray nameList,String data) {
        this.nameList = nameList;
        this.userData = data;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_choosename;
    }

    @Override
    protected void initView() {
        Log.d("nameList-->>",nameList.toString());
        for (int i = 0; i < nameList.length(); i++) {
            try {
                JSONObject json = nameList.getJSONObject(i);
                String name = json.getString("xing") + json.getString("ming");
                boolean isCollect = json.getInt("is_collent") == 1;
                data.add(new ChooseNameBean(name, isCollect));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter = new ChooseNameListAdapter(mContext, data);
        lv_namelist.setAdapter(adapter);
        lv_namelist.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("onItemClick-->>", position + "---" + id);
        currentPos = position;
        adapter.setSelectedPosition(position);
        adapter.notifyDataSetChanged();
        tv_first_word.setText(String.valueOf(data.get(position).getName().charAt(0)));
        tv_second_word.setText(String.valueOf(data.get(position).getName().charAt(1)));
        tv_third_word.setText(String.valueOf(data.get(position).getName().charAt(2)));

        //确定是否标记
        boolean isCollect = data.get(position).isCollect();
        if (isCollect) {
            tv_collect.setText("已标记");
            iv_collect.setImageDrawable(ResourcesManager.getDrawable(mContext, R.mipmap.name_biaoji));
        } else {
            tv_collect.setText("标记");
            iv_collect.setImageDrawable(ResourcesManager.getDrawable(mContext, R.mipmap.name_weibiao));
        }
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_namebar_normal, R.id.tv_namebar_small, R.id.tv_namebar_big, R.id.iv_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_namebar_normal:
                tv_namebar_normal.setBackground(ResourcesManager.getDrawable(mContext, R.color.red));
                tv_namebar_small.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_big.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                break;
            case R.id.tv_namebar_small:
                tv_namebar_normal.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_small.setBackground(ResourcesManager.getDrawable(mContext, R.color.red));
                tv_namebar_big.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                //是否解锁
                getNameList(2);
                break;
            case R.id.tv_namebar_big:
                tv_namebar_normal.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_small.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_big.setBackground(ResourcesManager.getDrawable(mContext, R.color.red));
                //是否解锁
                getNameList(3);
                break;
            case R.id.iv_collect:
                //请求服务器 收藏
                onClickCollect();
                break;
        }
    }

    /**
     * 点击收藏/已收藏按钮
     */
    public void onClickCollect() {
        ChooseNameBean info = data.get(currentPos);
        boolean isCollect = info.isCollect();

        Map map = new HashMap<String, String>();
        map.put("isCollect", String.valueOf(isCollect));

        OkHttpManager.request(Constants.getApi.NAMECOLLECT, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                String str = isCollect ? "取消收藏" : "收藏成功";
                ToastUtils.showShortToast(str);
            }

            @Override
            public void Fail(HttpInfo info) {
                String str = isCollect ? "取消收藏失败" : "收藏失败";
                ToastUtils.showShortToast(str);
            }
        });
    }

    public void getNameList(int type) {
        JSONObject jsondata = null;
        try {
                jsondata = new JSONObject(this.userData);
                JSONObject userInfo = jsondata.getJSONObject("info");
                String genderStr = userInfo.getString("gender");
                String surName = userInfo.getString("name");
                 Log.e("err---->>",  genderStr);
                String gender = genderStr.equals("男") ? "1" : "0";

                Map<String, String> map = new HashMap<String, String>();
                map.put("user_name", surName);
                map.put("gender", gender);
                map.put("name_type", type+"");

        Log.e("map-->>",map.toString());
        //请求数据
        OkHttpManager.request(Constants.getApi.GETJIMING, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>",  jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("err---->>",  e.toString());
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("info---->>", info.toString());
                String result = info.getRetDetail();
                ToastUtils.showShortToast(result);
            }
        });
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("err---->>",  e.toString());
        }
    }

    public void requestOrderUrl() {
//        NamePayActivity
        mContext.startActivity(new Intent(mContext, NamePayActivity.class));
    }
}
