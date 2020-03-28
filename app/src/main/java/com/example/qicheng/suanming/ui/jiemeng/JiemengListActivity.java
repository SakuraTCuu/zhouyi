package com.example.qicheng.suanming.ui.jiemeng;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qicheng.suanming.bean.JieMengListBean;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.utils.ToastUtils;

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
    RecyclerView fl_list;

    @BindView(R.id.tv_small_text)
    TextView tv_small_text;

    @BindView(R.id.et_input_jiemenglist)
    EditText et_input_jiemenglist;

    private final List<JieMengListBean> smallTitleList = new ArrayList<>();
    private String bigText;
    private String targetText;
    private String input;

    private JieMengListAdapter newsAdapter;

    private int searchPage = 1;

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
        boolean flag = initJsonData(data);
        if (flag) {
            setData();
            initRecycleView();
        } else {
            ToastUtils.showShortToast("数据解析失败!");
        }
    }

    private boolean initJsonData(String data) {
        try {
            JSONObject jsondata = new JSONObject(data);
            JSONArray jar = jsondata.getJSONArray("data");
            for (int i = 0; i < jar.length(); i++) {
                JSONObject jdata = jar.getJSONObject(i);
                JieMengListBean bean = new JieMengListBean(jdata.getString("title"), jdata.getInt("id"));
                smallTitleList.add(bean);
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @OnClick({R.id.btn_jiemenglist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_jiemenglist:
                //获取信息
                this.getDataListFromServer();
                break;
        }
    }

    private void getDataListFromServer() {

        Map<String, String> map = new HashMap<>();
        //点击事件和输入框
        input = et_input_jiemenglist.getText().toString().trim();
        map.put("search_key", input);
        map.put("page", searchPage + "");

        OkHttpManager.request(Constants.getApi.JIEMENGLISTSMALL, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.toString());
                    boolean code = jsonObject.getBoolean("code");
                    if (code) {
//                        Intent intent = new Intent(mContext, JiemengListActivity.class);
//                        intent.putExtra("data", jsonObject.toString());
//                        intent.putExtra("bigText", bigText);
//                        startActivity(intent);
                        smallTitleList.clear();
                        initJsonData(jsonObject.toString());
                        newsAdapter.setData(smallTitleList);
                        newsAdapter.notifyDataSetChanged();
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

    private void initRecycleView() {
        //谷歌提供了一个默认的item删除添加的动画
        fl_list.setItemAnimator(new DefaultItemAnimator());
        //谷歌提供了一个DividerItemDecoration的实现类来实现分割线
        //往往我们需要自定义分割线的效果，需要自己实现ItemDecoration接口
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        flow.addItemDecoration(dividerItemDecoration);

        //当item改变不会重新计算item的宽高
        //调用adapter的增删改差方法的时候就不会重新计算，但是调用nofityDataSetChange的时候还是会
        //所以往往是直接先设置这个为true，当需要布局重新计算宽高的时候才调用nofityDataSetChange
        fl_list.setHasFixedSize(true);
        //一行显示4个
        GridLayoutManager mamager = new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                //禁止滑动
                return false;
            }
        };
        fl_list.setLayoutManager(mamager);
    }

    private void setData() {
        newsAdapter = new JieMengListAdapter(smallTitleList);
        fl_list.setAdapter(newsAdapter);
    }

    class JieMengListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<JieMengListBean> list;

        public JieMengListAdapter(List<JieMengListBean> list) {
            this.list = list;
        }

        public void setData(List<JieMengListBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;
            v = getLayoutInflater().from(JiemengListActivity.this).inflate(R.layout.flow_small_item, fl_list, false);
            RecyclerView.ViewHolder holder = null;
            holder = new JiemengListActivity.MyViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String title = list.get(position).getTitle();
            ((MyViewHolder) holder).flow_small_text.setText(title);
            ((MyViewHolder) holder).ll_bg_jiemenglist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取详情信息
                    int id = list.get(position).getId();
                    String title = list.get(position).getTitle();
                    Map<String, String> map = new HashMap();
                    map.put("id", id + "");
                    map.put("title", title);
                    targetText = bigText + "-" + title;
                    getDataFromServer(map);
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
        public TextView flow_small_text;
        public LinearLayout ll_bg_jiemenglist;

        public MyViewHolder(View itemView) {
            super(itemView);
            flow_small_text = itemView.findViewById(R.id.flow_small_text);
            ll_bg_jiemenglist = itemView.findViewById(R.id.ll_bg_jiemenglist);
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
//                try {
//                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                String str = info.getRetDetail();
                Intent intent = new Intent(JiemengListActivity.this, JiemengDetailActivity.class);
                intent.putExtra("text", str);
                intent.putExtra("bigText", targetText);
                startActivity(intent);

//                    Log.d("jsonObject---->>", jsonObject.toString());
//                    boolean code = jsonObject.getBoolean("code");
//                    if (code) {
////                        JSONObject jdata = jsonObject.getJSONObject("data");
////                        String text = jdata.getString("newstext");
////                        Intent intent = new Intent(JiemengListActivity.this, JiemengDetailActivity.class);
////                        intent.putExtra("text", text);
////                        intent.putExtra("bigText", targetText);
////                        startActivity(intent);
//                    } else {
//                        String msg = jsonObject.getString("msg");
//                        ToastUtils.showShortToast(msg);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
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
