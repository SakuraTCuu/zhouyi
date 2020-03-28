package com.example.qicheng.suanming.adapter;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.qicheng.suanming.base.BaseFragment;

import java.util.List;

public class QimingDetailAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mlist;


    public QimingDetailAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.mlist = list;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int arg0) {
        return mlist.get(arg0);//显示第几个页面
    }

    @Override
    public int getCount() {
        return mlist.size();//有几个页面
    }
}
