package com.qicheng.zhouyi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qicheng.zhouyi.R;
import com.qicheng.zhouyi.bean.MineBeiyongBean;
import com.qicheng.zhouyi.ui.mine.MineBeiyongActivity;

import java.util.ArrayList;

public class MineBeiyongAdapter extends BaseAdapter {
    private ArrayList<MineBeiyongBean> data;
    private Context mContext;
    private MineBeiyongActivity.getDataListener listener;

    public MineBeiyongAdapter(ArrayList<MineBeiyongBean> data, Context mContext, MineBeiyongActivity.getDataListener listener) {
        this.data = data;
        this.mContext = mContext;
        this.listener = listener;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_mine_beiyong_item, parent, false);
            holder = new ViewHolder();
            holder.firstWord = (TextView) convertView.findViewById(R.id.tv_beiyong_first);
            holder.secondWord = (TextView) convertView.findViewById(R.id.tv_beiyong_second);
            holder.ThirdWord = (TextView) convertView.findViewById(R.id.tv_beiyong_third);
            holder.ll_delete = (LinearLayout) convertView.findViewById(R.id.ll_beiyong_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.firstWord.setText(data.get(position).getXing());
        holder.secondWord.setText(String.valueOf(data.get(position).getMing().charAt(0)));
        holder.ThirdWord.setText(String.valueOf(data.get(position).getMing().charAt(1)));
        holder.ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getListIndex(position);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView firstWord;
        TextView secondWord;
        TextView ThirdWord;
        LinearLayout ll_delete;
    }
}
