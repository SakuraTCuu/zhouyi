package com.qicheng.zhouyi.ui.mine;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.adapter.MineOrderAdapter;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.MineOrderBean;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
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
    PullToRefreshListView lv_order;

    @BindView(R.id.tv_noOrder)
    TextView tv_noOrder;

    private final String DJM = "djm";
    private final String BZJP = "bzjp";

    private String TAG = MineOrderActivity.class.getSimpleName();
    private ArrayList<MineOrderBean> data = new ArrayList<>();
    private int page = 1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_order;
    }

    @Override
    protected void initView() {
        showTitleBar();
        setTitleText("我的订单");
        lv_order.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        initRefreshListView();
        lv_order.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.d("PullDown", "PullDown");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.d("PullUp", "PullUp");
                //加载数据
                page += 1;
                getDataFromServer();
            }
        });
        this.getDataFromServer();
    }

    public void initRefreshListView() {
        ILoadingLayout Labels = lv_order.getLoadingLayoutProxy(true, true);
        Labels.setPullLabel("正在加载");
        Labels.setRefreshingLabel("正在加载");
        Labels.setReleaseLabel("放开刷新");
    }

    public void setData() {
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
//        Toast.makeText(mContext, "你点击了第" + position + "项", Toast.LENGTH_SHORT).show();
    }

    public void getDataFromServer() {
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");

        OkHttpManager.request(Constants.getApi.GETORDERLIST, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    JSONArray orderList = jsonObject.getJSONObject("data").getJSONArray("order_list");
                    int type = jsonObject.getJSONObject("data").getInt("type");
                    String msg = jsonObject.getString("msg");
                    if (type == -1) {
                        //暂无更多数据
//                        tv_noOrder.setVisibility(View.VISIBLE);
                        ToastUtils.showShortToast(msg);
                        lv_order.onRefreshComplete();
                    } else if (type == -2) {
                        //暂无数据
                        ToastUtils.showShortToast(msg);
                        tv_noOrder.setVisibility(View.VISIBLE);
                        lv_order.onRefreshComplete();
                    } else {
                        tv_noOrder.setVisibility(View.GONE);
                        for (int i = 0; i < orderList.length(); i++) {
                            JSONObject jdata = orderList.getJSONObject(i);
                            String key = jdata.getString("classify_key");
                            String classify_name = jdata.getString("classify_name");
                            String title = jdata.getString("title");
                            String order_sn = jdata.getString("order_sn");
                            String add_time = jdata.getString("add_time");
                            int pay_status = jdata.getInt("pay_status");
//                            String user_name = jdata.getString("user_name");
                            String bigTitle = "[" + classify_name + "]" + title;
                            MineOrderBean bean;
                            if (key.equals(DJM)) {
                                bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time);
                            } else {
                                bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time);
                            }
                            data.add(bean);
                        }
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
