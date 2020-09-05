package com.example.qicheng.suanming.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.qicheng.suanming.R;
import com.example.qicheng.suanming.bean.DashiSkillListBean;

import java.util.List;


public class DashiSkillAdapter extends ArrayAdapter {
    private Context mContext;
    private List<DashiSkillListBean.DataBean> mStringArray;

    public DashiSkillAdapter(Context context, List<DashiSkillListBean.DataBean> stringArray) {
        super(context, android.R.layout.simple_spinner_item, stringArray);
        mContext = context;
        mStringArray = stringArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //修改Spinner展开后的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.layout_dashi_skill_item, parent, false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(R.id.tv_item);
        tv.setText(mStringArray.get(position).getName());
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 修改Spinner选择后结果的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.layout_dashi_skill_item, parent, false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(R.id.tv_item);
//        View view_line = convertView.findViewById(R.id.view_line);
//        view_line.setVisibility(View.GONE);
        tv.setText(mStringArray.get(position).getName());
        tv.setTextSize(16f);
        tv.setTextColor(Color.WHITE);
        return convertView;
    }
}
