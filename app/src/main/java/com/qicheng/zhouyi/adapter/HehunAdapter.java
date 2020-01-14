package com.qicheng.zhouyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.bean.HehunListBean;

import java.util.List;

/**
 * Created by Sakura on 2020-01-14 15:26
 */
public class HehunAdapter extends BaseAdapter {
    private Context mContext;
    private List<HehunListBean> data;

    public HehunAdapter(Context mContext, List<HehunListBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position % data.size());
    }

    @Override
    public long getItemId(int position) {
        return position % data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_hehun_list_item, parent, false);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_item_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_item_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(data.get(position % data.size()).getTitle());
        holder.tv_content.setText(data.get(position % data.size()).getContent());
        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_content;
    }
}
