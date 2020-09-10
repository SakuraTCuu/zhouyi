package com.example.qicheng.suanming.presenter;

import com.example.qicheng.suanming.contract.SelectServerContract;
import com.example.qicheng.suanming.model.SelectServerModel;

import java.util.Map;

import static com.bumptech.glide.util.Preconditions.checkNotNull;
//import static com.google.common.base.Preconditions.checkNotNull;

public class SelectServerPresenter implements SelectServerContract.Presenter {

    //view
    private SelectServerContract.View mView;
    //model
    private SelectServerContract.Model mModel;

    public SelectServerPresenter(SelectServerContract.View mView) {
        this.mModel = new SelectServerModel(this);
        this.mView = checkNotNull(mView, "AddDashiContract.View cannot be null!");
    }

    @Override
    public void start() {

    }

    @Override
    public void putUserInfo(Map map) {
        mModel.putUserInfo(map);
    }

    @Override
    public void putUserInfoSuc(String data) {
        mView.putUserInfoSuc(data);
    }
}
