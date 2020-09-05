package com.example.qicheng.suanming.presenter;

import com.example.qicheng.suanming.contract.ArticleListContract;
import com.example.qicheng.suanming.model.ArticleListModel;

import java.util.Map;

import static com.bumptech.glide.util.Preconditions.checkNotNull;

public class ArticleListPresenter implements ArticleListContract.Presenter {

    //view
    private ArticleListContract.View mView;
    //model
    private ArticleListContract.Model mModel;

    public ArticleListPresenter(ArticleListContract.View mView) {
        this.mModel = new ArticleListModel(this);
        this.mView = checkNotNull(mView, "QuestionContract.View cannot be null!");
    }

    @Override
    public void start() {

    }

    @Override
    public void getArticleList(Map map) {
        mModel.getArticleList(map);
    }

    @Override
    public void getArticleListSuc(String data) {
        mView.getArticleListSuc(data);
    }
}
