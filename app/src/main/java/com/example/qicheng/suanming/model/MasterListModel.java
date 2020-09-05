package com.example.qicheng.suanming.model;

import android.util.Log;

import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.contract.MasterListContract;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import java.util.Map;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MasterListModel implements MasterListContract.Model {

    private MasterListContract.Presenter mPresenter;

    public MasterListModel(MasterListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setPresenter(MasterListContract.Presenter presenter) {

    }

    @Override
    public void getDaShiList(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        //网络请求
        OkHttpManager.requestByDashiji(Constants.getApi2.DASHILIST, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getDaShiListSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("Fail--->", info.getRetDetail());
            }
        });
    }

    @Override
    public void getSkillList(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        //网络请求
        OkHttpManager.requestByDashiji(Constants.getApi2.SKILLLIST, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getSkillListSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("Fail--->", info.getRetDetail());
            }
        });
    }
}
