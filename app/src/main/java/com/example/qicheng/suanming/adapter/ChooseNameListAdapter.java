package com.example.qicheng.suanming.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.bean.ChooseNameBean;

import java.util.List;

public class ChooseNameListAdapter extends BaseAdapter {

    private List<ChooseNameBean> data;
    private Context mContext;

    private int selectedPosition = 0;// 选中的位置

    public ChooseNameListAdapter(Context context, List<ChooseNameBean> data) {
        this.data = data;
        this.mContext = context;
    }

    public void setSelectedPosition(int pos) {
        selectedPosition = pos;
        if (pos == selectedPosition) {
            return;
        }
        selectedPosition = pos;
        notifyDataSetChanged();
    }

    public void setNewData(List<ChooseNameBean> data) {
        this.data = data;
//        this.print();
    }

    public void print() {
        Log.d("data地址值-->>", System.identityHashCode(this.data) + "");
        Log.d("data对象值-->>", data.toString());
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_qiming_name_item, parent, false);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_namelist_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(data.get(position).getName());

        if (selectedPosition == position) {
            holder.tv_name.setBackgroundResource(R.color.qiming_select_color);
        } else {
            holder.tv_name.setBackgroundResource(R.color.white);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
    }
}
