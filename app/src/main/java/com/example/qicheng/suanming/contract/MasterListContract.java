package com.example.qicheng.suanming.contract;
import com.example.qicheng.suanming.base.BaseModel;
import com.example.qicheng.suanming.base.BasePresenter;
import com.example.qicheng.suanming.base.BaseView;

import java.util.Map;

public interface MasterListContract {
    interface View extends BaseView<Presenter> {
        void getDaShiListSuc(String data);
        void getSkillListSuc(String data);
    }

    interface Model extends BaseModel<Presenter> {
        void getDaShiList(Map map);
        void getSkillList(Map map);
    }

    interface Presenter extends BasePresenter {
        void getDaShiList(Map map);
        void getSkillList(Map map);
        void getDaShiListSuc(String data);
        void getSkillListSuc(String data);
    }
}
