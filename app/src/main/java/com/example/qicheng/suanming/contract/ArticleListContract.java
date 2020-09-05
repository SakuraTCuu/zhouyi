package com.example.qicheng.suanming.contract;

import com.example.qicheng.suanming.base.BaseModel;
import com.example.qicheng.suanming.base.BasePresenter;
import com.example.qicheng.suanming.base.BaseView;

import java.util.Map;

public interface ArticleListContract {
    interface View extends BaseView<Presenter> {
        void getArticleListSuc(String data);
    }

    interface Model extends BaseModel<Presenter> {
        void getArticleList(Map map);
    }

    interface Presenter extends BasePresenter {

        void getArticleList(Map map);

        void getArticleListSuc(String data);

    }
}
