package com.example.qicheng.suanming.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.bean.DashiInfoBean;

import java.util.List;

public class DashiListAdapter extends BaseAdapter {

    private Context mContext;
    private List<DashiInfoBean.DataBean.DashiBean> data;
    private int type = 0;

    public DashiListAdapter(Context mContext, List<DashiInfoBean.DataBean.DashiBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public void setType(int type) {
        this.type = type;
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_dashi_list_item, parent, false);
            holder = new ViewHolder();
            holder.ll_stars = (LinearLayout) convertView.findViewById(R.id.ll_stars);
            holder.ll_skill = (LinearLayout) convertView.findViewById(R.id.ll_Skill);
            holder.userImg = (ImageView) convertView.findViewById(R.id.iv_userIcon);
            holder.userName = (TextView) convertView.findViewById(R.id.tv_userName);
            holder.level1 = (TextView) convertView.findViewById(R.id.tv_level_1);
            holder.level2 = (TextView) convertView.findViewById(R.id.tv_level_2);
            holder.conentText = (TextView) convertView.findViewById(R.id.tv_contentText);
            holder.orderNum = (TextView) convertView.findViewById(R.id.tv_orderNum);
            holder.attention = (TextView) convertView.findViewById(R.id.tv_attention);
            holder.replyTime = (TextView) convertView.findViewById(R.id.tv_replyTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DashiInfoBean.DataBean.DashiBean temp = this.data.get(position);

        Glide.with(mContext).load(temp.getAvator()).into(holder.userImg);
        holder.userName.setText(temp.getName());
        holder.level1.setText(temp.getScore());
        holder.level2.setText(temp.getLevel());
        holder.conentText.setText(Html.fromHtml(temp.getIntroduce()));
        if (this.type != 0) {//展示4行
            holder.conentText.setMaxLines(4);
        }
        holder.orderNum.setText(temp.getOrder_num() + "单");
        holder.attention.setText(temp.getConcern() + "关注");
        holder.replyTime.setText(temp.getReply_time() + "分钟回复");

        //设置星星
        setStars(holder.ll_stars, 5);
        //设置大师 技能标签

        String[] skillList = temp.getSkill().split(",");

        setDashiSkill(holder.ll_skill, skillList);

        return convertView;
    }

    private void setDashiSkill(LinearLayout llStars, String[] skillText) {
        int num = llStars.getChildCount();
        //TODO
        // 目前写死 三个 后边可以根据skillText 数组自定义个数
        for (int i = 0; i < num; i++) {
            TextView tv = (TextView) llStars.getChildAt(i);
            if (i < skillText.length) {
                tv.setText(skillText[i]);
            }
        }
    }

    private void setStars(LinearLayout llStars, int star) {
        if (star < 0) {
            return;
        }
        int num = llStars.getChildCount();
        if (star > num) {
            Log.d("TAG", "star set err ");
            return;
        }
        for (int i = 0; i < num; i++) {
            if (i < star) {
                ImageView iv = (ImageView) llStars.getChildAt(i);
                //设置星星图片
                iv.setImageResource(R.mipmap.wujiaoxingliang);
            }
        }
    }

    static class ViewHolder {
        LinearLayout ll_skill;
        LinearLayout ll_stars;
        ImageView userImg;
        TextView userName;
        TextView level1;
        TextView level2;
        TextView conentText;
        TextView orderNum;
        TextView attention;
        TextView replyTime;
    }
}
