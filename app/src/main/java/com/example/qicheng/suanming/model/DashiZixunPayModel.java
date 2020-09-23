package com.example.qicheng.suanming.model;

import android.util.Log;

import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.contract.DashiZixunPayContract;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import java.util.Map;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


public class DashiZixunPayModel implements DashiZixunPayContract.Model {

    private DashiZixunPayContract.Presenter mPresenter;

    public DashiZixunPayModel(DashiZixunPayContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setPresenter(DashiZixunPayContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void getOrderInfo(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi.CREATEZIXUNORDER, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getOrderInfoSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }

    @Override
    public void getShopOrderInfo(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi2.SHOPCREATEORDER, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getShopOrderInfoSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }

    @Override
    public void getWxPayInfo(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi.H5WXPAY, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getWxPayInfoSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }

    @Override
    public void getAliPayInfo(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashijiPost(Constants.getApi.APPALPAY, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getAliPayInfoSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }

    @Override
    public void getPayStateInfo(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashijiPost(Constants.getApi.APPPAYSTATE, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getPayStateInfoSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }
}
