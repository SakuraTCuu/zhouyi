package com.qicheng.zhouyi.ui.jiemeng;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
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
public class JiemengListActivity extends BaseActivity {

    @BindView(R.id.fl_list)
    FlowLayout fl_list;

    @BindView(R.id.tv_small_text)
    TextView tv_small_text;

    private final List<JieMengListBean> smallTitleList = new ArrayList<>();
    private String bigText;
    private String targetText;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_jiemenglist;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        bigText = intent.getStringExtra("bigText");
        tv_small_text.setText(bigText);
        Log.d("data-->>", data);
        try {
            JSONObject jsondata = new JSONObject(data);
            JSONArray jar = jsondata.getJSONArray("data");
            for (int i = 0; i < jar.length(); i++) {
                JSONObject jdata = jar.getJSONObject(i);
                JieMengListBean bean = new JieMengListBean(jdata.getString("title"), jdata.getInt("id"));
                smallTitleList.add(bean);
            }
            setData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setData() {
        final MyFlowAdapter myFlowAdapter = new MyFlowAdapter(this, smallTitleList);
        fl_list.setAdapter(myFlowAdapter);
    }

    class MyFlowAdapter extends FlowAdapter<JieMengListBean> {

        public MyFlowAdapter(Context context, List<JieMengListBean> list) {
            super(context, list);
        }

        @Override
        protected int generateLayout(int position) {
            return R.layout.flow_small_item;
        }

        @Override
        protected void getView(final JieMengListBean o, View parent) {
            TextView text = (TextView) parent.findViewById(R.id.flow_small_text);
            text.setText(o.getTitle());
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取详情信息
                    int id = o.getId();
                    String title = o.getTitle();
                    Map<String, String> map = new HashMap();
                    map.put("id", id + "");
                    map.put("title", title);

                    targetText = bigText + "-" + title;

                    getDataFromServer(map);
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

    private void getDataFromServer(Map map) {

        OkHttpManager.request(Constants.getApi.JIEMENGDETAIL, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.toString());
                    boolean code = jsonObject.getBoolean("code");
                    if (code) {
                        JSONObject jdata = jsonObject.getJSONObject("data");
                        String text = jdata.getString("newstext");
                        Intent intent = new Intent(JiemengListActivity.this, JiemengDetailActivity.class);
                        intent.putExtra("text", text);
                        intent.putExtra("bigText", targetText);
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
