package com.example.qicheng.suanming.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {

    OnScrollViewListener onScrollViewListener;
    boolean isTop = false;
    boolean isBottom = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    isTop = false;
                    break;
                case 1:
                    isBottom = false;
                    break;
                default:
                    break;
            }
        }
    };

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollViewListener != null) {
            if (getScrollY() == 0) {
                if (!isTop) {
                    isTop = true;
                    mHandler.sendEmptyMessageDelayed(0, 200);
                    onScrollViewListener.onTop();
                }
            } else {
                View contentView = getChildAt(0);
                if (contentView != null && contentView.getMeasuredHeight() == (getScrollY() + getHeight())) {
                    if (!isBottom) {
                        isBottom = true;
                        mHandler.sendEmptyMessageDelayed(1, 200);
                        onScrollViewListener.onBottom();
                    }

                }
            }
        }

    }

    /**
     * 自定义滑动事件的监听接口
     */
    public interface OnScrollViewListener {

        void onTop(); // 滑动到顶部


        void onBottom();// 滑动到底部
    }

    public void setOnScrollViewListener(OnScrollViewListener onScrollViewListener) {
        this.onScrollViewListener = onScrollViewListener;
    }
}
