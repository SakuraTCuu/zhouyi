package com.example.qicheng.suanming.model;

import android.util.Log;

import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.contract.ArticleDetailContract;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import java.util.Map;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


public class ArticleDetailModel implements ArticleDetailContract.Model {

    private ArticleDetailContract.Presenter mPresenter;

    public ArticleDetailModel(ArticleDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setPresenter(ArticleDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void getArticleDetail(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi2.ARTICLEDETAIL, RequestType.GET, map, new OkHttpManager.RequestListener() {

            @Override
            public void Success(HttpInfo info) {
                mPresenter.getArticleDetailSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }

    @Override
    public void addUserInfo(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi2.USERCESUANINFO, RequestType.GET, map, new OkHttpManager.RequestListener() {

            @Override
            public void Success(HttpInfo info) {
                mPresenter.addUserInfoSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }
}
