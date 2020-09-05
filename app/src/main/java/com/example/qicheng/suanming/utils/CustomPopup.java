package com.example.qicheng.suanming.utils;

import android.view.ViewGroup;
import android.widget.PopupWindow;

public class CustomPopup extends PopupWindow {
    public boolean isDismiss = true;


    public CustomPopup(ViewGroup vg, int w, int h) {
        super(vg, w, h);
    }

    @Override
    public void dismiss() {
        if (isDismiss) {
            super.dismiss();
        }
    }

    public void close() {
        super.dismiss();
    }

    public void setEditDismiss(boolean is) {
        isDismiss = is;
    }
}
