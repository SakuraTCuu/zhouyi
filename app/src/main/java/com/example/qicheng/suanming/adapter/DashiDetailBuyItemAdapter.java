package com.example.qicheng.suanming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.bean.DashiDetailBean;

import java.util.List;

public class DashiDetailBuyItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<DashiDetailBean.DataBean.ProjectBean> data;
    private int currentPos = -1;

    public DashiDetailBuyItemAdapter(Context mContext, List<DashiDetailBean.DataBean.ProjectBean> project) {
        this.mContext = mContext;
        this.data = project;
    }

    public void setSelectImg(int position) {
        currentPos = position;
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int position) {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DashiDetailBuyItemAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_detail_buy_item, parent, false);
            holder = new DashiDetailBuyItemAdapter.ViewHolder();
            holder.itemImg = (ImageView) convertView.findViewById(R.id.iv_item_img);
            holder.itemName = (TextView) convertView.findViewById(R.id.tv_item_name);
            holder.itemPrice = (TextView) convertView.findViewById(R.id.tv_item_price);
            holder.selectImg = (ImageView) convertView.findViewById(R.id.iv_select_img);
            convertView.setTag(holder);
        } else {
            holder = (DashiDetailBuyItemAdapter.ViewHolder) convertView.getTag();
        }
        DashiDetailBean.DataBean.ProjectBean temp = this.data.get(position);
        Glide.with(mContext).load(temp.getThumb()).into(holder.itemImg);
        holder.itemPrice.setText("¥" + temp.getPrice() + "/次");
        holder.itemName.setText(temp.getName());
        if (currentPos != -1 && currentPos == position) {
            holder.selectImg.setImageResource(R.mipmap.confirmorder_gouxuan);
        } else {
            holder.selectImg.setImageResource(R.mipmap.confirmorder_weigouxuan);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView itemImg;
        TextView itemName;
        TextView itemPrice;
        ImageView selectImg;
    }
}
