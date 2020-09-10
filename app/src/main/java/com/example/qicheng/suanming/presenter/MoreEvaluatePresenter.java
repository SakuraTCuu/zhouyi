package com.example.qicheng.suanming.presenter;

import com.example.qicheng.suanming.contract.MoreEvaluateContract;
import com.example.qicheng.suanming.model.MoreEvaluateModel;

import java.util.Map;

import static com.bumptech.glide.util.Preconditions.checkNotNull;
//import static com.google.common.base.Preconditions.checkNotNull;

public class MoreEvaluatePresenter implements MoreEvaluateContract.Presenter {

    //view
    private MoreEvaluateContract.View mView;
    //model
    private MoreEvaluateContract.Model mModel;

    public MoreEvaluatePresenter(MoreEvaluateContract.View mView) {
        this.mModel = new MoreEvaluateModel(this);
        this.mView = checkNotNull(mView, "MoreEvaluateContract.View cannot be null!");
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
    public void start() {

    }
}
