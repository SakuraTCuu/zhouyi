package com.example.qicheng.suanming.contract;

import com.example.qicheng.suanming.base.BaseModel;
import com.example.qicheng.suanming.base.BasePresenter;
import com.example.qicheng.suanming.base.BaseView;

import java.util.Map;

public interface SelectServerContract {
    interface View extends BaseView<Presenter> {
        void putUserInfoSuc(String data);
    }

    interface Model extends BaseModel<Presenter> {
        void putUserInfo(Map map);
    }

    interface Presenter extends BasePresenter {

        void putUserInfo(Map map);

        void putUserInfoSuc(String data);

    }
}
