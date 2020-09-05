package com.example.qicheng.suanming.presenter;


import com.example.qicheng.suanming.contract.CommunityContract;
import com.example.qicheng.suanming.model.CommunityModel;

import java.util.Map;

import static com.bumptech.glide.util.Preconditions.checkNotNull;

public class CommunityPresenter implements CommunityContract.Presenter {

    //登录界面
    private CommunityContract.View communityView;
    //登录model
    private CommunityContract.Model communityModel;

    public CommunityPresenter(CommunityContract.View communityView) {
        this.communityModel = new CommunityModel(this);
        this.communityView = checkNotNull(communityView, "CommunityContract.View cannot be null!");
        this.communityView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getListData(Map map) {
        communityModel.getListData(map);
    }

    @Override
    public void getInfoData(Map map) {
        communityModel.getInfoData(map);
    }

    @Override
    public void getInfoDataSuc(String data) {
        communityView.getInfoDataSuc(data);
    }

    @Override
    public void getListDataSuc(String data) {
        communityView.getListDataSuc(data);
    }
}
