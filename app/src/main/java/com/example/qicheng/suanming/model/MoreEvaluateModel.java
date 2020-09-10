package com.example.qicheng.suanming.model;

import android.util.Log;

import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.contract.MoreEvaluateContract;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import java.util.Map;

//import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MoreEvaluateModel implements MoreEvaluateContract.Model {

    private MoreEvaluateContract.Presenter mPresenter;

    public MoreEvaluateModel(MoreEvaluateContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void getCommentInfo(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi2.DASHIDETAILCOMMENT, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getCommentInfoSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }

    @Override
    public void setPresenter(MoreEvaluateContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
