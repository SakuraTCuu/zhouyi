package com.qicheng.zhouyi.ui.jiemeng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.library.flowlayout.FlowAdapter;
import com.library.flowlayout.FlowLayout;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.JieMengBean;
import com.qicheng.zhouyi.bean.JieMengListBean;
import com.qicheng.zhouyi.common.Constants;
import com.qicheng.zhouyi.common.OkHttpManager;
import com.qicheng.zhouyi.ui.bazijingpi.BaziJingPiActivity;
import com.qicheng.zhouyi.ui.webView.NamePayActivity;
import com.qicheng.zhouyi.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sakura on 2020-03-21 10:57
 */
public class JiemengActivity extends BaseActivity {

    @BindView(R.id.et_input_jiemeng)
    EditText et_input_jiemeng;

    @BindView(R.id.flow)
    FlowLayout flow;

    private String input;
    private int[] tempidList;
    private final List<JieMengBean> itemList = new ArrayList<>();
    private int searchPage = 1;
    private int clickPage = 1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_jiemeng;
    }

    @Override
    protected void initView() {
        //进入就请求
        this.getJieDataFromServer();
    }

    private void setData() {
        final MyFlowAdapter myFlowAdapter = new MyFlowAdapter(this, itemList);
        flow.setAdapter(myFlowAdapter);
    }

    class MyFlowAdapter extends FlowAdapter<JieMengBean> {

        public MyFlowAdapter(Context context, List<JieMengBean> list) {
            super(context, list);
        }

        @Override
        protected int generateLayout(int position) {
            return R.layout.flow_item;
        }

        @Override
        protected void getView(final JieMengBean o, View parent) {
            TextView text = (TextView) parent.findViewById(R.id.flow_text);
            text.setText(o.getTempName());
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDataFromServer(o.getTempid(), o.getTempName());
                }
            });
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
    }

    @OnClick({R.id.btn_jiemeng})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_jiemeng:
                this.getDataFromServer(-1,"");
                break;
        }
    }

    private void getJieDataFromServer() {
        //空
        Map<String, String> map = new HashMap<>();
//        {"code":true,"msg":"操作成功","data":[{"tempid":1,"tempname":"分类解梦"},{"tempid":2,"tempname":"人物类"},
//        {"tempid":3,"tempname":"情爱类"},{"tempid":4,"tempname":"生活类"},{"tempid":5,"tempname":"物品类"},
//        {"tempid":6,"tempname":"身体类"},{"tempid":7,"tempname":"动物类"},{"tempid":8,"tempname":"植物类"},
//        {"tempid":9,"tempname":"鬼神类"},{"tempid":10,"tempname":"建筑类"},{"tempid":11,"tempname":"自然类"}]}
        OkHttpManager.request(Constants.getApi.JIEMENGLIST, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    JSONArray jar = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jar.length(); i++) {
                        JSONObject jdata = jar.getJSONObject(i);
                        JieMengBean bean = new JieMengBean(jdata.getString("tempname"), jdata.getInt("tempid"));
                        itemList.add(bean);
                    }
                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("info---->>", info.toString());
                String result = info.getRetDetail();
                ToastUtils.showShortToast(result);
            }
        });
    }

    private void getDataFromServer(int type, String bigText) {

        Map<String, String> map = new HashMap<>();
        if (type == -1) {
            //点击事件和输入框
            input = et_input_jiemeng.getText().toString().trim();
            map.put("search_key", input);
            map.put("page", searchPage + "");
        } else {
            map.put("tempid", type + "");
            map.put("page", clickPage + "");
        }

        OkHttpManager.request(Constants.getApi.JIEMENGLISTSMALL, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.toString());
                    boolean code = jsonObject.getBoolean("code");
                    if (code) {
                        Intent intent = new Intent(mContext, JiemengListActivity.class);
                        intent.putExtra("data", jsonObject.toString());
                        intent.putExtra("bigText", bigText);
                        startActivity(intent);
                    } else {
                        String msg = jsonObject.getString("msg");
                        ToastUtils.showShortToast(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("info---->>", info.toString());
                String result = info.getRetDetail();
                ToastUtils.showShortToast(result);
            }
        });
    }
}
