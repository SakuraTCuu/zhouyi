package com.example.qicheng.suanming.presenter;


import com.example.qicheng.suanming.contract.MasterListContract;
import com.example.qicheng.suanming.model.MasterListModel;

import java.util.Map;

import static com.bumptech.glide.util.Preconditions.checkNotNull;

public class MasterListPresenter implements MasterListContract.Presenter {
    //登录界面
    private MasterListContract.View masterListView;
    //登录model
    private MasterListContract.Model masterListModel;

    public MasterListPresenter(MasterListContract.View masterListView) {
        this.masterListModel = new MasterListModel(this);
        this.masterListView = checkNotNull(masterListView, "MainContract.View cannot be null!");
        this.masterListView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getDaShiList(Map map) {
        this.masterListModel.getDaShiList(map);
    }

    @Override
    public void getSkillList(Map map) {
        this.masterListModel.getSkillList(map);
    }

    @Override
    public void getDaShiListSuc(String data) {
        this.masterListView.getDaShiListSuc(data);
    }

    @Override
    public void getSkillListSuc(String data) {
        this.masterListView.getSkillListSuc(data);
    }
}
