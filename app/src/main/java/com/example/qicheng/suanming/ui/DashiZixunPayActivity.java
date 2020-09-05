package com.example.qicheng.suanming.ui;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.bean.CreateOrderBean;
import com.example.qicheng.suanming.bean.WxPayBean;
import com.example.qicheng.suanming.bean.WxPayExtBean;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.contract.DashiZixunPayContract;
import com.example.qicheng.suanming.presenter.DashiZixunPayPresenter;
import com.example.qicheng.suanming.utils.ToastUtils;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DashiZixunPayActivity extends BaseActivity implements DashiZixunPayContract.View {

    @BindView(R.id.tv_pay)
    TextView tv_pay;

    @BindView(R.id.tv_price)
    TextView tv_price;

    @BindView(R.id.tv_order_sn)
    TextView tv_order_sn;

    @BindView(R.id.tv_order_time)
    TextView tv_order_time;

    @BindView(R.id.iv_wx_pay)
    ImageView iv_wx_pay;

    @BindView(R.id.iv_ali_pay)
    ImageView iv_ali_pay;


    private DashiZixunPayPresenter mPresenter;

    private CreateOrderBean.DataBean result;
    private String payOrder_sn;
    private String selectId;
    private String ds_id;
    private String info_id;
    private String price;
    private String type;
    private String created_at;

    private int payType = 1; // 1 wx 2 ali

    @Override
    protected int setLayoutId() {
        return R.layout.activity_dashi_zixun_pay;
    }

    @Override
    protected void initView() {
        setTitleText("支付");

        type = getIntent().getStringExtra("type");
        payOrder_sn = getIntent().getStringExtra("order_sn");
        created_at = getIntent().getStringExtra("created_at");
        price = getIntent().getStringExtra("price");

        selectId = getIntent().getStringExtra("selectId");
        ds_id = getIntent().getStringExtra("ds_id");
        info_id = getIntent().getStringExtra("info_id");

        mPresenter = new DashiZixunPayPresenter(this);

        tv_pay.setText("¥" + price);
        tv_price.setText("¥" + price);
    }

    @Override
    protected void initData() {
        //从订单列表过来的
        if (type.equals("zixun_pay")) {
            tv_order_sn.setText(payOrder_sn);
            tv_order_time.setText(created_at);
            return;
        }
        //从订单列表过来的
//        if (type.equals("vip_pay")) {
//            tv_order_sn.setText(payOrder_sn);
//            tv_order_time.setText(created_at);
//            return;
//        }

        if (!type.equals("goods")) {
            Map<String, String> map = new HashMap<>();
            map.put("project_id", selectId);
            if (type.equals("vip")) {
                map.put("type", "2");
            } else {
                map.put("type", "1");
                map.put("ds_id", ds_id);
            }
            map.put("info_id", info_id);
            mPresenter.getOrderInfo(map);
            showLoading();
        } else if (type.equals("goods")) {
            tv_order_sn.setText(payOrder_sn);
            tv_order_time.setText(created_at);
        }
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.iv_wx_pay, R.id.iv_ali_pay, R.id.ll_buy})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_wx_pay:
                payType = 1;
                iv_wx_pay.setImageResource(R.mipmap.confirmorder_gouxuan);
                iv_ali_pay.setImageResource(R.mipmap.confirmorder_weigouxuan);
                break;
            case R.id.iv_ali_pay:
                payType = 2;
                iv_wx_pay.setImageResource(R.mipmap.confirmorder_weigouxuan);
                iv_ali_pay.setImageResource(R.mipmap.confirmorder_gouxuan);
                break;
            case R.id.ll_buy:
                clickBuy();
                break;
        }
    }

    public void clickBuy() {
        if (payType == 1) { //wx支付
            Map<String, String> map = new HashMap<>();
            if (!type.equals("goods")) {
                if (type.equals("vip") || type.equals("vip_pay")) { //TODO 服务器没分
                    map.put("order_type", "zixun");
                } else {
                    map.put("order_type", "zixun");
                }
            } else {
                map.put("order_type", "goods");
            }
            map.put("order_sn", payOrder_sn);
            //先调用支付
            mPresenter.getWxPayInfo(map);
            showLoading();
        } else { // 支付宝

        }
    }

    @Override
    public void getOrderInfoSuc(String data) {
        hideLoading();
        CreateOrderBean bean = new Gson().fromJson(data, CreateOrderBean.class);
        if (bean.getCode() == 200) {
            result = bean.getData();
            payOrder_sn = result.getOrder_no();
            tv_order_sn.setText(payOrder_sn);
            tv_order_time.setText(result.getCreated_at());
        } else {
            ToastUtils.showShortToast("错误," + bean.getMsg());
        }
    }

    @Override
    public void getShopOrderInfoSuc(String data) {

    }

    @Override
    public void getWxPayInfoSuc(String data) {
        hideLoading();
        //获取订单成功, 调起微信支付
        Log.d("getWxPayInfoSuc-->>", data);
        WxPayBean bean = new Gson().fromJson(data, WxPayBean.class);

        WxPayBean.DataBean result = bean.getData();
        IWXAPI api = MyApplication.getInstance().getWxApi();
        PayReq request = new PayReq();
        request.appId = result.getAppid();
        request.partnerId = result.getPartnerid();
        request.prepayId = result.getPrepayid();
        request.packageValue = result.getPkg();
        request.nonceStr = result.getNoncestr();
        request.timeStamp = result.getTimestamp();
        request.sign = result.getSign();
        WxPayExtBean payExtBean;
        if (type.equals("goods")) {
            payExtBean = new WxPayExtBean(Constants.payType.goods, payOrder_sn);
        } else {
            payExtBean = new WxPayExtBean(Constants.payType.virtual, payOrder_sn);
        }
        //传入一个标识，以便区分回调
        String ext = new Gson().toJson(payExtBean);
        request.extData = ext;
        api.sendReq(request);
    }

    @Override
    public void setPresenter(DashiZixunPayContract.Presenter presenter) {

    }
}
