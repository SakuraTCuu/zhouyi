package com.example.qicheng.suanming.ui.qiming.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qicheng.suanming.ui.webView.NamePayActivity;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.adapter.ChooseNameListAdapter;
import com.example.qicheng.suanming.base.BaseFragment;
import com.example.qicheng.suanming.bean.ChooseNameBean;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.common.ResourcesManager;
import com.example.qicheng.suanming.utils.LoadingDialog;
import com.example.qicheng.suanming.utils.MapUtils;
import com.example.qicheng.suanming.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

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

    private int type = -1;

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
        if (nameList.length() <= 0) {
            ToastUtils.showShortToast("请输入正确姓氏!");
            return;
        }
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
        changeNameList(0);
    }

    @Override
    public void onResume() {
        super.onResume();
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
                type = -1;
                changeNameBar(type);
                getNameList(type);
                break;
            case R.id.tv_namebar_small:
                type = 1;
                changeNameBar(type);
                //是否解锁
                getNameList(type);
                break;
            case R.id.tv_namebar_big:
                type = 2;
                changeNameBar(type);
                //是否解锁
                getNameList(type);
                break;
            case R.id.iv_collect:
                //请求服务器 收藏
                onClickCollect();
                break;
        }
    }

    private void changeNameBar(int type) {
        switch (type) {
            case -1:
                tv_namebar_normal.setBackground(ResourcesManager.getDrawable(mContext, R.color.qiming_select_color));
                tv_namebar_normal.setTextColor(getResources().getColor(R.color.white));
                tv_namebar_small.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_small.setTextColor(getResources().getColor(R.color.black));
                tv_namebar_big.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_big.setTextColor(getResources().getColor(R.color.black));
                break;
            case 1:
                tv_namebar_normal.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_normal.setTextColor(getResources().getColor(R.color.black));
                tv_namebar_small.setBackground(ResourcesManager.getDrawable(mContext, R.color.qiming_select_color));
                tv_namebar_small.setTextColor(getResources().getColor(R.color.white));
                tv_namebar_big.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_big.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                tv_namebar_normal.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_normal.setTextColor(getResources().getColor(R.color.black));
                tv_namebar_small.setBackground(ResourcesManager.getDrawable(mContext, R.color.white));
                tv_namebar_small.setTextColor(getResources().getColor(R.color.black));
                tv_namebar_big.setBackground(ResourcesManager.getDrawable(mContext, R.color.qiming_select_color));
                tv_namebar_big.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    /**
     * 点击收藏/已收藏按钮
     */
    public void onClickCollect() {
        ChooseNameBean NameInfo = data.get(currentPos);
        boolean isCollect = NameInfo.isCollect();
        int isCollect2 = isCollect ? 1 : 0;
        Map<String, String> map = new HashMap();
        map.put("is_collent", String.valueOf(isCollect2));
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
        LoadingDialog loading = new LoadingDialog(mContext);
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

            String date = userInfo.getString("birthdayy");
            date += "-";
            date += userInfo.getString("birthdaym");
            date += "-";
            date += userInfo.getString("birthdayd");

            Map<String, String> map = new HashMap();
            map.put("user_name", surName);
            map.put("gender", gender);
            map.put("name_type", type + "");

            Map<String, Object> map2 = new HashMap();
            map2.put("user_name", surName);
            map2.put("gender", gender);
            map2.put("name_type", type);
            map2.put("user_id", Constants.userInfo.getUser_id());
            map2.put("birthday", date);

            Log.e("map-->>", map2.toString());

            String url_data = MapUtils.Map2String(map2);
            Log.e("url_data-->>", url_data);
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
                            gotoH5(url_data);
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


    //结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult--->>", "onActivityResult");
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            int status = bundle.getInt("status");
            Log.d("status-->>", status + "");
            if (status == 1) {
                Log.d("status-->>", "支付成功");
                //支付成功,重新请求
                changeNameBar(type);
                getNameList(type);
            } else {
                //支付失败 ,返回
                //保持状态
                Log.d("status-->>", "未支付");
                type = -1;
                changeNameBar(type);
                getNameList(type);
            }
        } else {
            type = -1;
            changeNameBar(type);
            getNameList(type);
        }
    }

    private void gotoH5(String url_data) {
        Map<String, String> map = new HashMap();
        map.put("type", "6");

        //跳转到webView 界面
        OkHttpManager.request(Constants.getApi.GETH5URL, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.toString());
                    String url = jsonObject.getJSONObject("data").getString("url");
                    url += url_data;
                    Log.d("url---->>", url);
                    Intent intent = new Intent(mContext, NamePayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", url);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("info---->>", info.toString());
                String result = info.getRetDetail();
                ToastUtils.showShortToast(result);
            }
        });
    }
}
