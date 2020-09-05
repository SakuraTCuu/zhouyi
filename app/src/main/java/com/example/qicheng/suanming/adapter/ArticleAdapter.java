package com.example.qicheng.suanming.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.bean.ArticleBean;

import java.util.List;

public class ArticleAdapter extends BaseAdapter {
    private Context mContext;
    private List<ArticleBean.DataBean.ListBean> data;

    public ArticleAdapter(Context mContext, List<ArticleBean.DataBean.ListBean> data) {
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
        ArticleAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_article_item, parent, false);
            holder = new ArticleAdapter.ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_article_icon);
            holder.title = (TextView) convertView.findViewById(R.id.tv_article_title);
            holder.content = (TextView) convertView.findViewById(R.id.tv_article_content);
            holder.haoping = (TextView) convertView.findViewById(R.id.tv_article_haoping);
            holder.comment = (TextView) convertView.findViewById(R.id.tv_article_comment);
            holder.test = (TextView) convertView.findViewById(R.id.tv_article_test);

            convertView.setTag(holder);
        } else {
            holder = (ArticleAdapter.ViewHolder) convertView.getTag();
        }
        ArticleBean.DataBean.ListBean temp = this.data.get(position);
        Glide.with(mContext).load(temp.getThumb()).into(holder.icon);
        holder.title.setText(temp.getName());
        holder.content.setText(Html.fromHtml(temp.getContent()));
        holder.haoping.setText(temp.getGood_rate() + "%好评");
        holder.comment.setText(temp.getRecomment_num() + "评论");
        holder.test.setText(temp.getUse_num() + "人测试");

        return convertView;
    }


    static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView content;
        TextView haoping;
        TextView comment;
        TextView test;
    }
}
