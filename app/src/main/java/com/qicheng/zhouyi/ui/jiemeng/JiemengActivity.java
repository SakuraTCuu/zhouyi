package com.qicheng.zhouyi.ui.jiemeng;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

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

import static androidx.recyclerview.widget.OrientationHelper.VERTICAL;

/**
 * Created by Sakura on 2020-03-21 10:57
 */
public class JiemengActivity extends BaseActivity {

    @BindView(R.id.et_input_jiemeng)
    EditText et_input_jiemeng;

    @BindView(R.id.flow)
    RecyclerView flow;

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

        initRecycleView();
    }

    private void initRecycleView() {
        //谷歌提供了一个默认的item删除添加的动画
        flow.setItemAnimator(new DefaultItemAnimator());
        //谷歌提供了一个DividerItemDecoration的实现类来实现分割线
        //往往我们需要自定义分割线的效果，需要自己实现ItemDecoration接口
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        flow.addItemDecoration(dividerItemDecoration);

        //当item改变不会重新计算item的宽高
        //调用adapter的增删改差方法的时候就不会重新计算，但是调用nofityDataSetChange的时候还是会
        //所以往往是直接先设置这个为true，当需要布局重新计算宽高的时候才调用nofityDataSetChange
        flow.setHasFixedSize(true);
        //一行显示4个
        GridLayoutManager mamager = new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                //禁止滑动
                return false;
            }
        };
        flow.setLayoutManager(mamager);
    }

    private void setData() {
        JieMengAdapter newsAdapter = new JieMengAdapter(itemList);
        flow.setAdapter(newsAdapter);
    }

    class JieMengAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<JieMengBean> list;

        public JieMengAdapter(List<JieMengBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;
//            v = getLayoutInflater().inflate(R.layout.flow_item, null, false);
            v = getLayoutInflater().from(JiemengActivity.this).inflate(R.layout.flow_item, flow, false);
            RecyclerView.ViewHolder holder = null;
            holder = new MyViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder) holder).flow_text.setText(list.get(position).getTempName());
            ((MyViewHolder) holder).bg_item_jiemeng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDataFromServer(list.get(position).getTempid(), list.get(position).getTempName());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView flow_text;
        public LinearLayout bg_item_jiemeng;

        public MyViewHolder(View itemView) {
            super(itemView);
            flow_text = itemView.findViewById(R.id.flow_text);
            bg_item_jiemeng = itemView.findViewById(R.id.bg_item_jiemeng);
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
                this.getDataFromServer(-1, "");
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
