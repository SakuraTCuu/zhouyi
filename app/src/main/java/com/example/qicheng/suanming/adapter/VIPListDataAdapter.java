package com.example.qicheng.suanming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.bean.VIPHomeDataBean;

import java.util.List;

public class VIPListDataAdapter extends BaseAdapter {
    private Context mContext;
    private List<VIPHomeDataBean.DataBean> data;

    public VIPListDataAdapter(Context mContext, List<VIPHomeDataBean.DataBean> data) {
        this.mContext = mContext;
        this.data = data;
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
        VIPListDataAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_community_item, parent, false);
            holder = new VIPListDataAdapter.ViewHolder();
            holder.vip_haoping = (TextView) convertView.findViewById(R.id.tv_vip_haoping);
            holder.vip_comment = (TextView) convertView.findViewById(R.id.tv_vip_comment);
            holder.vip_test = (TextView) convertView.findViewById(R.id.tv_vip_test);
            holder.iv_banner = (ImageView) convertView.findViewById(R.id.iv_banner);
            convertView.setTag(holder);
        } else {
            holder = (VIPListDataAdapter.ViewHolder) convertView.getTag();
        }
        VIPHomeDataBean.DataBean temp = this.data.get(position);
//        holder.vip_label.setText(temp.getLabel());
        holder.vip_haoping.setText(temp.getGood_rate() + "%好评率");
        holder.vip_test.setText(temp.getUse_num() + "人测试");
        holder.vip_comment.setText(temp.getRecomment_num() + "条评论");
        Glide.with(mContext).load(temp.getIndex_pic()).override(Target.SIZE_ORIGINAL).into(holder.iv_banner);
        return convertView;
    }

    static class ViewHolder {
        //        TextView vip_label;
        TextView vip_haoping;
        TextView vip_comment;
        TextView vip_test;
        //        RelativeLayout banner;
        ImageView iv_banner;
    }
}
