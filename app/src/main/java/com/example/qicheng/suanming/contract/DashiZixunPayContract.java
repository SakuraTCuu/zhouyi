package com.example.qicheng.suanming.contract;

import com.example.qicheng.suanming.base.BaseModel;
import com.example.qicheng.suanming.base.BasePresenter;
import com.example.qicheng.suanming.base.BaseView;

import java.util.Map;

public interface DashiZixunPayContract {
    interface View extends BaseView<Presenter> {
        void getOrderInfoSuc(String data);

        void getShopOrderInfoSuc(String data);

        void getWxPayInfoSuc(String data);
    }

    interface Model extends BaseModel<Presenter> {
        void getOrderInfo(Map map);

        void getShopOrderInfo(Map map);

        void getWxPayInfo(Map map);
    }

    interface Presenter extends BasePresenter {
        void getWxPayInfo(Map map);

        void getOrderInfo(Map map);

        void getShopOrderInfo(Map map);

        void getShopOrderInfoSuc(String data);

        void getWxPayInfoSuc(String data);

        void getOrderInfoSuc(String data);

    }
}
