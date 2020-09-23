package com.example.qicheng.suanming.presenter;

import com.example.qicheng.suanming.contract.DashiZixunPayContract;
import com.example.qicheng.suanming.model.DashiZixunPayModel;

import java.util.Map;

import static com.bumptech.glide.util.Preconditions.checkNotNull;


public class DashiZixunPayPresenter implements DashiZixunPayContract.Presenter {

    //view
    private DashiZixunPayContract.View mView;
    //model
    private DashiZixunPayContract.Model mModel;

    public DashiZixunPayPresenter(DashiZixunPayContract.View mView) {
        this.mModel = new DashiZixunPayModel(this);
        this.mView = checkNotNull(mView, "DashiZixunPayContract.View cannot be null!");
    }

    @Override
    public void start() {

    }

    @Override
    public void getWxPayInfo(Map map) {
        mModel.getWxPayInfo(map);
    }

    @Override
    public void getOrderInfo(Map map) {
        mModel.getOrderInfo(map);
    }

    @Override
    public void getShopOrderInfo(Map map) {
        mModel.getShopOrderInfo(map);
    }

    @Override
    public void getShopOrderInfoSuc(String data) {
        mView.getShopOrderInfoSuc(data);
    }

    @Override
    public void getWxPayInfoSuc(String data) {
        mView.getWxPayInfoSuc(data);
    }

    @Override
    public void getOrderInfoSuc(String data) {
        mView.getOrderInfoSuc(data);
    }

    @Override
    public void getAliPayInfo(Map map) {
        mModel.getAliPayInfo(map);
    }

    @Override
    public void getAliPayInfoSuc(String data) {
        mView.getAliPayInfoSuc(data);
    }

    @Override
    public void getPayStateInfo(Map map) {
        mModel.getPayStateInfo(map);
    }

    @Override
    public void getPayStateInfoSuc(String data) {
        mView.getPayStateInfoSuc(data);
    }
}
