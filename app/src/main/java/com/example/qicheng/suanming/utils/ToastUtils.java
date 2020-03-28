package com.example.qicheng.suanming.utils;

import android.widget.Toast;

import androidx.annotation.StringRes;

import com.example.qicheng.suanming.ui.MyApplication;
import com.example.qicheng.suanming.widget.CustomToast;

/**
 * Created by Rei on 2018/2/2.
 */

public class ToastUtils {

    private static CustomToast toast;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

    /**
     * 短时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToast(String msg) {
        if (MyApplication.getInstance() != null) {
            if (toast == null) {
                toast = CustomToast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    /**
     * 短时间显示Toast【居下】
     *
     * @param stringId 显示的内容-从资源ID中取字符串
     */
    public static void showShortToast(@StringRes int stringId) {
        if (MyApplication.getInstance() != null) {
            CharSequence msg = MyApplication.getInstance().getText(stringId);
            if (toast == null) {
                toast = CustomToast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }
//
//    /**
//     * 短时间显示Toast【居中】
//     *
//     * @param msg 显示的内容-字符串
//     */
//    public static void showShortToastCenter(String msg) {
//        if (MyApplication.getInstance() != null) {
//            if (toast == null) {
//                toast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//            } else {
//                toast.setText(msg);
//            }
//            toast.show();
//        }
//    }
//
//    /**
//     * 短时间显示Toast【居上】
//     *
//     * @param msg 显示的内容-字符串
//     */
//    public static void showShortToastTop(String msg) {
//        if (MyApplication.getInstance() != null) {
//            if (toast == null) {
//                toast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.TOP, 0, 0);
//            } else {
//                toast.setText(msg);
//            }
//            toast.show();
//        }
//    }
//
//    /**
//     * 长时间显示Toast【居下】
//     *
//     * @param msg 显示的内容-字符串
//     */
//    public static void showLongToast(String msg) {
//        if (MyApplication.getInstance() != null) {
//            if (toast == null) {
//                toast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_LONG);
//            } else {
//                toast.setText(msg);
//            }
//            toast.show();
//        }
//    }
//
//    /**
//     * 长时间显示Toast【居中】
//     *
//     * @param msg 显示的内容-字符串
//     */
//    public static void showLongToastCenter(String msg) {
//        if (MyApplication.getInstance() != null) {
//            if (toast == null) {
//                toast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//            } else {
//                toast.setText(msg);
//            }
//            toast.show();
//        }
//    }
//
//    /**
//     * 长时间显示Toast【居上】
//     *
//     * @param msg 显示的内容-字符串
//     */
//    public static void showLongToastTop(String msg) {
//        if (MyApplication.getInstance() != null) {
//            if (toast == null) {
//                toast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.TOP, 0, 0);
//            } else {
//                toast.setText(msg);
//            }
//            toast.show();
//        }
//    }
}
