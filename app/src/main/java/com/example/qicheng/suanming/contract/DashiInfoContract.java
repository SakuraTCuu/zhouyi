package com.example.qicheng.suanming.contract;

import com.example.qicheng.suanming.base.BaseModel;
import com.example.qicheng.suanming.base.BasePresenter;
import com.example.qicheng.suanming.base.BaseView;

import java.util.Map;

public interface DashiInfoContract {
    interface View extends BaseView<Presenter> {
        void buySuc(String data);

        void getDashiInfoSuc(String data);

        void getCommentInfoSuc(String data);
    }

    interface Model extends BaseModel<Presenter> {
        void buy(Map map);

        void getDashiInfo(Map map);

        void getCommentInfo(Map map);

        void setAttention(Map map);
    }

    interface Presenter extends BasePresenter {
        void buy(Map map);

        void buySuc(String data);

        void getDashiInfo(Map map);

        void getDashiInfoSuc(String data);

        void getCommentInfo(Map map);

        void getCommentInfoSuc(String data);

        void setAttention(Map map);
    }
}
