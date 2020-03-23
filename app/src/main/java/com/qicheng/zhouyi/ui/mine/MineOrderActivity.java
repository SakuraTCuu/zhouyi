package com.qicheng.zhouyi.ui.mine;

import android.content.Intent;
import android.os.Bundle;
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
import com.qicheng.zhouyi.ui.bazi.BaziHehunActivity;
import com.qicheng.zhouyi.ui.qiming.QimingDetailActivity;
import com.qicheng.zhouyi.ui.webView.NamePayActivity;
import com.qicheng.zhouyi.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.qicheng.zhouyi.base.BaseFragment.BASE_END;

public class MineOrderActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_order)
    PullToRefreshListView lv_order;

    @BindView(R.id.tv_noOrder)
    TextView tv_noOrder;

    public interface onClickOrderListener {
        public void clickBtn(int position);
    }

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
        onClickOrderListener listener = new onClickOrderListener() {
            @Override
            public void clickBtn(int position) {
                //点击详情页
                MineOrderBean orderBean = data.get(position);
                onClickDetailBtn(orderBean);
            }
        };
        lv_order.onRefreshComplete();
        lv_order.setAdapter(new MineOrderAdapter(data, mContext, listener));
        lv_order.setOnItemClickListener(this);
    }

    private void onClickDetailBtn(MineOrderBean orderBean) {
        switch (orderBean.getType()) {
            case Constants.getClassifyKey.DJM:
            case Constants.getClassifyKey.XJM:
//                    跳转到自选姓名界面  需要先请求
                getNameDataFromServer(orderBean);

                break;
            case Constants.getClassifyKey.BZJP:
                //h5url
            case Constants.getClassifyKey.BZHH:
            case Constants.getClassifyKey.YLYY:
            case Constants.getClassifyKey.CYFX:
            case Constants.getClassifyKey.CSFX:
            case Constants.getClassifyKey.HYCS:
            case Constants.getClassifyKey.WLYS:
//                index/order/orderInfo?order_sn=2020032109135060032
                String url = Constants.getApi.URL + "index/order/orderInfo?order_sn=" + orderBean.getOrderCode();
                Intent intent = new Intent(MineOrderActivity.this, NamePayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    private void getNameDataFromServer(MineOrderBean orderBean) {
        JSONObject userData = orderBean.getUserInfo();
        try {
            Log.d("userData---->", userData.getString("birthday"));
            String user_name = userData.getString("user_name");
            String birthday = userData.getString("birthday");
            String gender = userData.getString("gender");
//    String date_type = "1"; // 日期类型  公历还是农历
            Map map = new HashMap<String, String>();
            map.put("user_name", user_name);
            map.put("birthday", birthday);
            map.put("gender", gender + "");
            map.put("date_type", "1");

            OkHttpManager.request(Constants.getApi.QIMING, RequestType.POST, map, new OkHttpManager.RequestListener() {
                @Override
                public void Success(HttpInfo info) {
                    try {
                        JSONObject json = new JSONObject(info.getRetDetail());
                        if (json.getBoolean("code")) {
                            //请求成功
                            Intent intent = new Intent(mContext, QimingDetailActivity.class);
                            intent.putExtra("data", json.get("data").toString());
                            mContext.startActivity(intent);
                        } else {
                            ToastUtils.showShortToast(json.get("message").toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void Fail(HttpInfo info) {
                    String result = info.getRetDetail();
                    ToastUtils.showShortToast(result);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

                            JSONObject userInfo = jdata.getJSONObject("data");
                            String key = jdata.getString("classify_key");
                            String classify_name = jdata.getString("classify_name");
                            String title = jdata.getString("title");
                            String order_sn = jdata.getString("order_sn");
                            String add_time = jdata.getString("add_time");
                            int pay_status = jdata.getInt("pay_status");
                            String bigTitle = "[" + classify_name + "]" + title;
                            MineOrderBean bean = null;
//                            跳转到不同的页面
                            if (key.equals(Constants.getClassifyKey.DJM)) {
                                bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time, userInfo);
                            } else if (key.equals(Constants.getClassifyKey.XJM)) {
                                bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time, userInfo);
                            } else if (key.equals(Constants.getClassifyKey.CYFX)) {
                                bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time, userInfo);
                            } else if (key.equals(Constants.getClassifyKey.BZHH)) {
                                bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time, userInfo);
                            } else if (key.equals(Constants.getClassifyKey.YLYY)) {
                                bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time, userInfo);
                            } else if (key.equals(Constants.getClassifyKey.WLYS)) {
                                bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time, userInfo);
                            } else if (key.equals(Constants.getClassifyKey.HYCS)) {
                                bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time, userInfo);
                            } else if (key.equals(Constants.getClassifyKey.BZJP)) {
                                bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time, userInfo);
                            } else {
                                Log.d("key--->>", key);
                            }
                            data.add(bean);
                            if (i == orderList.length() - 1) {
                                setData();
                            }
                        }
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
