package com.qicheng.zhouyi.ui.mine;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.adapter.MineBeiyongAdapter;
import com.qicheng.zhouyi.base.BaseActivity;
import com.qicheng.zhouyi.bean.MineBeiyongBean;

import java.util.ArrayList;

import butterknife.BindView;


public class MineBeiyongActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    // 内部接口
    public interface getDataListener {
        public void getListIndex(int position);
    }

    @BindView(R.id.lv_beiyong)
    ListView lv_beiyong;

    private ArrayList<MineBeiyongBean> data = new ArrayList<>();
    private MineBeiyongAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_mine_beiyong;
    }

    @Override
    protected void initView() {
        showTitleBar();
        setTitleText("备用姓名");
        //填充假数据
        for (int i = 0; i < 10; i++) {
            MineBeiyongBean bean = new MineBeiyongBean();
            bean.setUserName("李中堂");
            data.add(bean);
        }

        getDataListener listener = new getDataListener() {

            @Override
            public void getListIndex(int position) {
                //获取position
                //删除数据
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        };

        adapter = new MineBeiyongAdapter(data, mContext, listener);
        lv_beiyong.setAdapter(adapter);
        lv_beiyong.setOnItemClickListener(this);
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


}
