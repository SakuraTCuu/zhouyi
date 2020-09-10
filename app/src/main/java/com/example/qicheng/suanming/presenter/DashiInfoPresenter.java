package com.example.qicheng.suanming.presenter;

import com.example.qicheng.suanming.contract.DashiInfoContract;
import com.example.qicheng.suanming.model.DashiInfoModel;

import java.util.Map;

import static com.bumptech.glide.util.Preconditions.checkNotNull;
//import static com.google.common.base.Preconditions.checkNotNull;

public class DashiInfoPresenter implements DashiInfoContract.Presenter {

    //view
    private DashiInfoContract.View mView;
    //model
    private DashiInfoContract.Model mModel;

    public DashiInfoPresenter(DashiInfoContract.View mView) {
        this.mModel = new DashiInfoModel(this);
        this.mView = checkNotNull(mView, "DashiInfoContract.View cannot be null!");
    }

    @Override
    public void buy(Map map) {
        mModel.buy(map);
    }

    @Override
    public void buySuc(String data) {
        mView.buySuc(data);
    }

    @Override
    public void getDashiInfo(Map map) {
        mModel.getDashiInfo(map);
    }

    @Override
    public void getDashiInfoSuc(String data) {
        mView.getDashiInfoSuc(data);
    }

    @Override
    public void getCommentInfo(Map map) {
        mModel.getCommentInfo(map);
    }

    @Override
    public void getCommentInfoSuc(String data) {
        mView.getCommentInfoSuc(data);
    }

    @Override
    public void setAttention(Map map) {
        mModel.setAttention(map);
    }

    @Override
    public void start() {

    }
}
