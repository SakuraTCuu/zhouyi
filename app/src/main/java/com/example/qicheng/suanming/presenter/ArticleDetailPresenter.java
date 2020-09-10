package com.example.qicheng.suanming.presenter;

import com.example.qicheng.suanming.contract.ArticleDetailContract;
import com.example.qicheng.suanming.model.ArticleDetailModel;

import java.util.Map;

import static com.bumptech.glide.util.Preconditions.checkNotNull;


public class ArticleDetailPresenter implements ArticleDetailContract.Presenter {

    //view
    private ArticleDetailContract.View mView;
    //model
    private ArticleDetailContract.Model mModel;

    public ArticleDetailPresenter(ArticleDetailContract.View mView) {
        this.mModel = new ArticleDetailModel(this);
        this.mView = checkNotNull(mView, "QuestionContract.View cannot be null!");
    }

    @Override
    public void start() {

    }

    @Override
    public void addUserInfo(Map map) {
        mModel.addUserInfo(map);
    }

    @Override
    public void getArticleDetail(Map map) {
        mModel.getArticleDetail(map);
    }

    @Override
    public void getArticleUserInfo(Map map) {
        mModel.getArticleUserInfo(map);
    }

    @Override
    public void addUserInfoSuc(String data) {
        mView.addUserInfoSuc(data);
    }

    @Override
    public void getArticleDetailSuc(String data) {
        mView.getArticleDetailSuc(data);
    }

    @Override
    public void getArticleUserInfoSuc(String data) {
        mView.getArticleUserInfoSuc(data);
    }
}
