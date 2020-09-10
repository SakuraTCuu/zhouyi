package com.example.qicheng.suanming.model;

import android.util.Log;

import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.contract.DashiInfoContract;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import java.util.Map;

//import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class DashiInfoModel implements DashiInfoContract.Model {

    private DashiInfoContract.Presenter mPresenter;

    public DashiInfoModel(DashiInfoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void buy(Map map) {
//        OkHttpManager.request();
    }

    @Override
    public void getDashiInfo(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi2.DASHIDETAIL, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getDashiInfoSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
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
    public void setAttention(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi.DASHIATTENTION, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
//                ToastUtils.showShortToast("关注成功");
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
//                ToastUtils.showShortToast("关注失败");
            }
        });
    }

    @Override
    public void setPresenter(DashiInfoContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
