package com.example.qicheng.suanming.contract;

import com.example.qicheng.suanming.base.BaseModel;
import com.example.qicheng.suanming.base.BasePresenter;
import com.example.qicheng.suanming.base.BaseView;

import java.util.Map;

public interface ArticleDetailContract {
    interface View extends BaseView<Presenter> {
        void getArticleDetailSuc(String data);

        void addUserInfoSuc(String data);
    }

    interface Model extends BaseModel<Presenter> {
        void getArticleDetail(Map map);

        void addUserInfo(Map map);
    }

    interface Presenter extends BasePresenter {
        void addUserInfo(Map map);

        void getArticleDetail(Map map);

        void addUserInfoSuc(String data);

        void getArticleDetailSuc(String data);

    }
}
