package com.example.qicheng.suanming.model;

import android.util.Log;

import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.contract.ArticleListContract;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;

import java.util.Map;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


public class ArticleListModel implements ArticleListContract.Model {

    private ArticleListContract.Presenter mPresenter;

    public ArticleListModel(ArticleListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setPresenter(ArticleListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void getArticleList(Map map) {
//        String sign = Constants.joinStrParams(map);
//        map.put("sign", sign);
        OkHttpManager.requestByDashiji(Constants.getApi2.ARTICLELIST, RequestType.GET, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                mPresenter.getArticleListSuc(info.getRetDetail());
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.e("DashiList.err->", info.getRetDetail());
            }
        });
    }
}
