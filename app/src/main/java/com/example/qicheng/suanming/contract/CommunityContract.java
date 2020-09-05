package com.example.qicheng.suanming.contract;

import com.example.qicheng.suanming.base.BaseModel;
import com.example.qicheng.suanming.base.BasePresenter;
import com.example.qicheng.suanming.base.BaseView;

import java.util.Map;

public interface CommunityContract {

    interface View extends BaseView<Presenter> {
        void getListDataSuc(String data);

        void getInfoDataSuc(String data);
    }

    interface Model extends BaseModel<Presenter> {
        void getListData(Map map);

        void getInfoData(Map map);

    }

    interface Presenter extends BasePresenter {
        void getListData(Map map);

        void getInfoData(Map map);

        void getInfoDataSuc(String data);

        void getListDataSuc(String data);
    }
}
