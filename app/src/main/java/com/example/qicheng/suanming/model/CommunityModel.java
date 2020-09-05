package com.example.qicheng.suanming.model;

import android.util.Log;


import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.contract.CommunityContract;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import java.util.Map;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class CommunityModel implements CommunityContract.Model {

    private CommunityContract.Presenter mPresenter;

    public CommunityModel(CommunityContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setPresenter(CommunityContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void getListData(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi2.VIPCATEGORY, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getListDataSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }

    @Override
    public void getInfoData(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi2.VIPHOME, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getInfoDataSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }
}
