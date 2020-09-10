package com.example.qicheng.suanming.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.bean.DashiDetailCommentBean;

import java.util.List;

public class DashiDetailCommentAdapter extends BaseAdapter {
    private Context mContext;
    private List<DashiDetailCommentBean.DataBean.ListBean> data;
    private int currentPos = -1;

    public DashiDetailCommentAdapter(Context mContext, List<DashiDetailCommentBean.DataBean.ListBean> data) {
        this.mContext = mContext;
        this.data = data;
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
        DashiDetailCommentAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_dashi_detail_comment_item, parent, false);
            holder = new DashiDetailCommentAdapter.ViewHolder();
            holder.userIcon = (ImageView) convertView.findViewById(R.id.iv_comment_user_icon);
            holder.userName = (TextView) convertView.findViewById(R.id.tv_comment_user_name);
            holder.level = (TextView) convertView.findViewById(R.id.tv_comment_level);
            holder.time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.skill = (LinearLayout) convertView.findViewById(R.id.ll_comment_skill);
            holder.stars = (LinearLayout) convertView.findViewById(R.id.ll_comment_stars);
            holder.concern = (TextView) convertView.findViewById(R.id.tv_comment_concern);

            convertView.setTag(holder);
        } else {
            holder = (DashiDetailCommentAdapter.ViewHolder) convertView.getTag();
        }
        DashiDetailCommentBean.DataBean.ListBean temp = this.data.get(position);
        Glide.with(mContext).load(temp.getAvator()).into(holder.userIcon);
        holder.userName.setText(temp.getName());
        holder.level.setText(temp.getScore());
        holder.time.setText(temp.getComment_time());
        holder.concern.setText(temp.getContent());

        String[] skillList = temp.getLabel().split(",");
        setDashiSkill(holder.skill, skillList);

        return convertView;
    }

    private void setDashiSkill(LinearLayout llStars, String[] skillText) {
        int num = llStars.getChildCount();
        int len = skillText.length;
        num = num > len ? len : num;

        for (int i = 0; i < num; i++) {
            TextView tv = (TextView) llStars.getChildAt(i);
            tv.setText(skillText[i]);
        }
    }

    static class ViewHolder {
        ImageView userIcon;
        TextView userName;
        TextView level;
        TextView time;
        TextView concern;
        LinearLayout skill;
        LinearLayout stars;
    }
}
