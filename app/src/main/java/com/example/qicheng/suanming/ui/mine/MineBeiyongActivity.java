package com.example.qicheng.suanming.ui.mine;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.qicheng.suanming.R;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.okhttplib.HttpInfo;
import com.okhttplib.annotation.RequestType;
import com.example.qicheng.suanming.adapter.MineBeiyongAdapter;
import com.example.qicheng.suanming.base.BaseActivity;
import com.example.qicheng.suanming.bean.MineBeiyongBean;
import com.example.qicheng.suanming.common.Constants;
import com.example.qicheng.suanming.common.OkHttpManager;
import com.example.qicheng.suanming.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


public class MineBeiyongActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    // 内部接口
    public interface getDataListener {
        public void getListIndex(int position);
    }

    @BindView(R.id.lv_beiyong)
    PullToRefreshListView lv_beiyong;

    @BindView(R.id.tv_noMore)
    TextView tv_noMore;

    private ArrayList<MineBeiyongBean> data = new ArrayList<>();
    private MineBeiyongAdapter adapter;
    private int page = 1;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_beiyong;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        showTitleBar();
        setTitleText("备用姓名");
        this.getDataFromServer();

        lv_beiyong.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        initRefreshListView();
        lv_beiyong.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                   Log.d("PullDown","PullDown");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.d("PullUp","PullUp");
                //加载数据
                page+=1;
                getDataFromServer();
            }
        });
    }

    public void initRefreshListView() {
        ILoadingLayout Labels = lv_beiyong.getLoadingLayoutProxy(true, true);
        Labels.setPullLabel("正在加载");
        Labels.setRefreshingLabel("正在加载");
        Labels.setReleaseLabel("放开刷新");
    }

    public void setData(){
        getDataListener listener = new getDataListener() {
            @Override
            public void getListIndex(int position) {
                //获取position
                Log.d("delete-->>","delete");
                MineBeiyongBean delData = data.get(position);
                Map<String,String> map = new HashMap<>();
                map.put("rname_id",delData.getRname_id()+"");
                delCollectName(map);
                //删除数据
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        };

        adapter = new MineBeiyongAdapter(data, mContext, listener);
        lv_beiyong.setAdapter(adapter);
        lv_beiyong.setOnItemClickListener(this);
        lv_beiyong.onRefreshComplete();
        adapter.notifyDataSetChanged();
    }

    public void delCollectName(Map map){
        OkHttpManager.request(Constants.getApi.NAMECOLLECTDEL, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("info---->>", info.toString());
                try {
                    String result = new JSONObject(info.getRetDetail()).getString("msg");
                    ToastUtils.showShortToast(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public void getDataFromServer() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("page", page + "");
        //获取姓名列表

        OkHttpManager.request(Constants.getApi.GETCOLLECTNAME, RequestType.POST, map, new OkHttpManager.RequestListener() {
            @Override
            public void Success(HttpInfo info) {
                Log.d("info---->>", info.getRetDetail());
                try {
                    JSONObject jsonObject = new JSONObject(info.getRetDetail());
                    Log.d("jsonObject---->>", jsonObject.toString());
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    String msg = jsonObject.getString("msg");
                    int type = jsonData.getInt("types");
                    if (type != 0 && page == 1) {
                        ToastUtils.showShortToast(msg);
                        tv_noMore.setVisibility(View.VISIBLE);
                    }else if(type!=0 && page!=1){
                        ToastUtils.showShortToast(msg);
                        lv_beiyong.onRefreshComplete();
                    }else {
                        tv_noMore.setVisibility(View.GONE);
                       JSONArray jArr =  jsonData.getJSONArray("name_list");
                        for (int i = 0; i < jArr.length(); i++) {
                            JSONObject obj = jArr.getJSONObject(i);
                            Log.d("obj-->>",obj.toString());
                            String xing = obj.getString("xing");
                            String ming = obj.getString("ming");
                            int rid = obj.getInt("rname_id");
                            MineBeiyongBean bean = new MineBeiyongBean(rid,xing,ming);
                            data.add(bean);
                        }
                        Log.d("data-->>",data.toString());
                        setData();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Fail(HttpInfo info) {
                Log.d("info---->>", info.toString());
                try {
                    String result = new JSONObject(info.getRetDetail()).getString("msg");
                    ToastUtils.showShortToast(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(mContext, "你点击了第" + position + "项", Toast.LENGTH_SHORT).show();
    }


    //上拉刷新


}
