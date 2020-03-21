package com.qicheng.zhouyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.bean.MineOrderBean;
import com.qicheng.zhouyi.ui.mine.MineOrderActivity;
//import

import java.util.ArrayList;

public class MineOrderAdapter<M> extends BaseAdapter {
    private ArrayList<MineOrderBean> data;
    private Context mContext;
    private MineOrderActivity.onClickOrderListener listener;

    public MineOrderAdapter(ArrayList<MineOrderBean> data, Context mContext, MineOrderActivity.onClickOrderListener listener) {
        this.data = data;
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_mine_order_item, parent, false);
            holder = new ViewHolder();
            holder.titleText = (TextView) convertView.findViewById(R.id.tv_order_item_title);
            holder.orderText = (TextView) convertView.findViewById(R.id.tv_order_item_order);
            holder.orderTimeText = (TextView) convertView.findViewById(R.id.tv_order_item_ordertime);
            holder.btn_detail = (Button) convertView.findViewById(R.id.btn_detail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleText.setText(data.get(position).getOrderTitle());
        holder.orderText.setText("订单号：" + data.get(position).getOrderCode());
        holder.orderTimeText.setText("下单时间：" + data.get(position).getOrderTime());
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件
                listener.clickBtn(position);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView titleText;
        TextView orderText;
        TextView orderTimeText;
        Button btn_detail;
    }
}
