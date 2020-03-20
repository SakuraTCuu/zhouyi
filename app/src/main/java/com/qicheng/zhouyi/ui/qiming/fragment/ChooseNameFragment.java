package com.qicheng.zhouyi.ui.qiming.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.qicheng.zhouyi.utils.LoadingDialog;
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


    @BindView(R.id.ll_root)
    LinearLayout ll_root;

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
    private List<ChooseNameBean> normalData = new ArrayList<>();
    private List<ChooseNameBean> bigData = new ArrayList<>();
    private List<ChooseNameBean> smallData = new ArrayList<>();

    private String userData;
    private ChooseNameListAdapter adapter;
    private JSONArray nameList;
    private int currentPos;

    public ChooseNameFragment() {
        super();
    }

    public ChooseNameFragment(JSONArray nameList, String data) {
        this.nameList = nameList;
        this.userData = data;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_choosename;
    }

    @Override
    protected void initView() {
        Log.d("nameList-->>", nameList.toString());
        for (int i = 0; i < nameList.length(); i++) {
            try {
                JSONObject json = nameList.getJSONObject(i);
                String name = json.getString("xing") + json.getString("ming");
                boolean isCollect = json.getInt("is_collent") == 1;
                normalData.add(new ChooseNameBean(name, isCollect));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        data = normalData;
        adapter = new ChooseNameListAdapter(mContext, data);
        lv_namelist.setAdapter(adapter);
        lv_namelist.setOnItemClickListener(this);

        //确定第一个是否标记
        initSignName(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("onItemClick-->>", position + "---" + id);
        changeNameList(position);
    }

    //点击大小吉名后 列表的效果变化及 数据展示
    public void changeNameList(int position) {
        //切换后默认到第一位
        currentPos = position;
        adapter.setSelectedPosition(position);
        adapter.notifyDataSetChanged();
        tv_first_word.setText(String.valueOf(data.get(position).getName().charAt(0)));
        tv_second_word.setText(String.valueOf(data.get(position).getName().charAt(1)));
        tv_third_word.setText(String.valueOf(data.get(position).getName().charAt(2)));
        initSignName(position);
    }

    @Override
    protected void initData() {

    }

    public void initSignName(int position) {
        boolean isCollect = data.get(position).isCollect();
        if (isCollect) {
            tv_collect.setText("已标记");
            iv_collect.setImageDrawable(ResourcesManager.getDrawable(mContext, R.mipmap.name_biaoji));
        } else {
            tv_collect.setText("标记");
            iv_collect.setImageDrawable(ResourcesManager.getDrawable(mContext, R.mipmap.name_weibiao));
        }
    }

    @OnClick({R.id.tv_namebar_normal, R.id.tv_namebar_small, R.id.tv_namebar_big, R.id.iv_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_namebar_normal:
                tv_namebar_normal.setBackground(ResourcesManager.getDrawable(mContext, R.color.red));
                tv_namebar_small.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_big.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                getNameList(-1);
                break;
            case R.id.tv_namebar_small:
                tv_namebar_normal.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_small.setBackground(ResourcesManager.getDrawable(mContext, R.color.red));
                tv_namebar_big.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                //是否解锁
                getNameList(1);
                break;
            case R.id.tv_namebar_big:
                tv_namebar_normal.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_small.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_big.setBackground(ResourcesManager.getDrawable(mContext, R.color.red));
                //是否解锁
                getNameList(2);
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
        ChooseNameBean NameInfo = data.get(currentPos);
        boolean isCollect = NameInfo.isCollect();

        Map<String, String> map = new HashMap();
        map.put("is_collent", String.valueOf(isCollect));
        map.put("xing", NameInfo.getName().substring(0, 1));
        map.put("ming", NameInfo.getName().substring(1));

        OkHttpManager.request(Constants.getApi.NAMECOLLECT, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                String str = isCollect ? "取消收藏" : "收藏成功";
                ToastUtils.showShortToast(str);
                //收藏成功后更改状态
                NameInfo.setCollect(!isCollect);
                initSignName(currentPos);
            }

            @Override
            public void Fail(HttpInfo info) {
                String str = isCollect ? "取消收藏失败" : "收藏失败";
                ToastUtils.showShortToast(str);
            }
        });
    }

    public void getNameList(int type) {
//         显示loading 加载框
        LoadingDialog loading =   new LoadingDialog(mContext);
        loading.showDialog();
//        ll_root.addView(loading);
        if (type == -1) {
            data = normalData;
            adapter.setNewData(data);
            adapter.notifyDataSetChanged();
            changeNameList(0);
            loading.dismiss();
            return;
        }
        JSONObject jsondata = null;
        try {
            jsondata = new JSONObject(this.userData);
            JSONObject userInfo = jsondata.getJSONObject("info");
            String genderStr = userInfo.getString("gender");
            String surName = userInfo.getString("name");
            Log.e("err---->>", genderStr);
            String gender = genderStr.equals("男") ? "1" : "0";

            Map<String, String> map = new HashMap<String, String>();
            map.put("user_name", surName);
            map.put("gender", gender);
            map.put("name_type", type + "");

            Log.e("map-->>", map.toString());
//            {"code":true,"msg":"未名支付","data":{"big_ji":{"classify_id":9,"classify_name":"大吉名","classify_key":"djm","price":"0.01"},
////            "small_ji":{"classify_id":10,"classify_name":"小吉","classify_key":"xjm","price":"0.01"},"types":-1}}
            //请求数据
            OkHttpManager.request(Constants.getApi.GETJIMING, RequestType.POST, map, new OkHttpManager.RequestListener() {
                @Override
                public void Success(HttpInfo info) {
                    Log.d("info---->>", info.getRetDetail());
                    try {
                        JSONObject jsonObject = new JSONObject(info.getRetDetail());
                        JSONObject jsonData = jsonObject.getJSONObject("data");
                        int types = jsonData.getInt("types");
                        if (types == -1) { //未支付，跳转到支付
                            //请求h5界面 跳转到webview,
                            loading.dismiss();
                        } else {
                            JSONArray jar = jsonData.getJSONArray("name_list");
                            for (int i = 0; i < jar.length(); i++) {
                                JSONObject jData = jar.getJSONObject(i);
                                String xing = jData.getString("xing");
                                String ming = jData.getString("ming");
                                String name = xing + ming;
                                boolean isCollect = jData.getInt("is_collent") == 1;
                                if (type == 2) {
                                    smallData.add(new ChooseNameBean(name, isCollect));
                                } else {
                                    bigData.add(new ChooseNameBean(name, isCollect));
                                }
                            }
                            //赋值
                            if (type == 2) {
                                data = smallData;
                            } else {
                                data = bigData;
                            }
                            adapter.setNewData(data);
                            adapter.notifyDataSetChanged();
                            loading.dismiss();
                            changeNameList(0);
                        }
                        Log.d("jsonObject---->>", jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("err---->>", e.toString());
                    }
                }

                @Override
                public void Fail(HttpInfo info) {
                    Log.d("info---->>", info.toString());
                    String result = info.getRetDetail();
                    loading.dismiss();
                    ToastUtils.showShortToast(result);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            loading.dismiss();
            Log.e("err---->>", e.toString());
        }
    }

    public void requestOrderUrl() {
//        NamePayActivity
        mContext.startActivity(new Intent(mContext, NamePayActivity.class));
    }
}
