package com.example.qicheng.suanming.contract;

import com.example.qicheng.suanming.base.BaseModel;
import com.example.qicheng.suanming.base.BasePresenter;
import com.example.qicheng.suanming.base.BaseView;

import java.util.Map;

public interface MoreEvaluateContract {
    interface View extends BaseView<Presenter> {

        void getCommentInfoSuc(String data);
    }

    interface Model extends BaseModel<Presenter> {
        void getCommentInfo(Map map);

    }

    interface Presenter extends BasePresenter {

        void getCommentInfo(Map map);

        void getCommentInfoSuc(String data);
    }
}
