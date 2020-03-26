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
import com.qicheng.zhouyi.utils.LoadingDialog;
import com.qicheng.zhouyi.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.qicheng.zhouyi.base.BaseFragment.BASE_END;

public class MineOrderActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_order)
    PullToRefreshListView lv_order;

    @BindView(R.id.tv_noOrder)
    TextView tv_noOrder;

    @BindView(R.id.tv_order_no)
    TextView tv_order_no;
    @BindView(R.id.tv_order_all)
    TextView tv_order_all;
    @BindView(R.id.tv_order_over)
    TextView tv_order_over;

    public interface onClickOrderListener {
        public void clickBtn(int position);
    }

    private String TAG = MineOrderActivity.class.getSimpleName();
    private ArrayList<MineOrderBean> data = new ArrayList<>();
    private ArrayList<MineOrderBean> over_data = new ArrayList<>(); //已支付
    private ArrayList<MineOrderBean> noover_data = new ArrayList<>(); //未支付
    private MineOrderAdapter adapter;
    private LoadingDialog dialog;

    private int page = 1;  //更换查询状态从1重新开始
    private int type = -1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_order;
    }

    @OnClick({R.id.tv_order_no, R.id.tv_order_all, R.id.tv_order_over})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_order_no:
                if (type != 0) {
                    type = 0;
                    page = 1;
                    getDataFromServer(type, -1);
                    setChangeStatus(type);
                }
                break;
            case R.id.tv_order_all:
                if (type != -1) {
                    type = -1;
                    page = 1;
                    getDataFromServer(type, -1);
                    setChangeStatus(type);
                }
                break;
            case R.id.tv_order_over:
                if (type != 1) {
                    type = 1;
                    page = 1;
                    getDataFromServer(type, -1);
                    setChangeStatus(type);
                }
                break;
        }
        dialog.showDialog();
    }

    private void setChangeStatus(int type) {
        if (type == -1) {
            tv_order_all.setBackgroundResource(R.drawable.mine_order_bottom_border);
            tv_order_over.setBackground(null);
            tv_order_no.setBackground(null);
        } else if (type == 1) {
            tv_order_all.setBackground(null);
            tv_order_over.setBackgroundResource(R.drawable.mine_order_bottom_border);
            tv_order_no.setBackground(null);
        } else if (type == 0) {
            tv_order_all.setBackground(null);
            tv_order_over.setBackground(null);
            tv_order_no.setBackgroundResource(R.drawable.mine_order_bottom_border);
        }
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
                getDataFromServer(type, 1);
            }
        });

        dialog = new LoadingDialog(this);
        this.getDataFromServer(type, -1);

        onClickOrderListener listener = new onClickOrderListener() {
            @Override
            public void clickBtn(int position) {
                //点击详情页
                MineOrderBean orderBean = data.get(position);
                onClickDetailBtn(orderBean);
            }
        };
        lv_order.onRefreshComplete();
        adapter = new MineOrderAdapter(data, mContext, listener);
        lv_order.setAdapter(adapter);
        lv_order.setOnItemClickListener(this);
    }

    public void initRefreshListView() {
        ILoadingLayout Labels = lv_order.getLoadingLayoutProxy(true, true);
        Labels.setPullLabel("正在加载");
        Labels.setRefreshingLabel("正在加载");
        Labels.setReleaseLabel("放开刷新");
    }

    public void setData() {
        dialog.dismiss();
        if (type == -1) {
            adapter.setData(data);
        } else if (type == 0) {
            adapter.setData(noover_data);
        } else if (type == 1) {
            adapter.setData(over_data);
        }
        adapter.notifyDataSetChanged();

        lv_order.onRefreshComplete();
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

    public void getDataFromServer(int Status, int from) {
        if (from == -1) {
            //清空数据
            //浪费带宽
            over_data.clear();
            data.clear();
            noover_data.clear();
        }
        dialog.showDialog();
        Map<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("pay_status", Status + "");

        OkHttpManager.request(Constants.getApi.GETORDERLIST, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    int type = jsonObject.getJSONObject("data").getInt("type");
                    String msg = jsonObject.getString("msg");
                    if (type == -1) {
                        //暂无更多数据
//                        tv_noOrder.setVisibility(View.VISIBLE);
                        ToastUtils.showShortToast(msg);
                        lv_order.onRefreshComplete();
                        dialog.dismiss();
                    } else if (type == -2) {
                        //暂无数据
                        ToastUtils.showShortToast(msg);
                        tv_noOrder.setVisibility(View.VISIBLE);
                        lv_order.onRefreshComplete();
                        dialog.dismiss();
                    } else {
                        JSONArray orderList = jsonObject.getJSONObject("data").getJSONArray("order_list");
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
                            MineOrderBean bean = new MineOrderBean(key, pay_status, classify_name + bigTitle, order_sn, add_time, userInfo);
                            if (Status == -1) {
                                data.add(bean);
                            } else if (Status == 0) {
                                noover_data.add(bean);
                            } else if (Status == 1) {
                                over_data.add(bean);
                            }
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
