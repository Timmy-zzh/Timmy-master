package com.timmy.library.util;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Created by Administrator on 2016/1/12 0012.
 */
public class Toast {

    public static android.widget.Toast toast;


    private static void toast(int time, String s) {
        if (toast == null) {
//            toast = android.widget.Toast.makeText(TimmyApp.getInstance(), s, time);
        } else {
            toast.setText(s);
        }
        toast.show();
    }

    private static void toast(int time, int resId) {
        if (toast == null) {
//            toast = android.widget.Toast.makeText(TimmyApp.getInstance(), resId, time);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }

    public static void toastLong(String s) {
        toast(1000, s);
    }

    public static void toastShort(String s) {
        toast(500, s);
    }

    public static void toastShort(int resId) {
        toast(500, resId);
    }

    public static void toastLong(int resId) {
        toast(1000, resId);
    }

    public static void toastDuration(String s, int time) {
        toast(time, s);
    }

    public static void toastDuration(int time, int resId) {
        toast(time, resId);
    }

    public static void toastCenter(String s, int time) {
        if (toast == null) {
//            toast = android.widget.Toast.makeText(TimmyApp.getInstance(), s, time);
        } else {
            toast.setText(s);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static void toastCenterWithImg(String s, int time, ImageView view) {
        if (toast == null) {
//            toast = android.widget.Toast.makeText(TimmyApp.getInstance(), s, time);
        } else {
            toast.cancel();
//            toast = android.widget.Toast.makeText(TimmyApp.getInstance(), s, time);
            toast.setText(s);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        ((LinearLayout) toast.getView()).addView(view, 0);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        view.setLayoutParams(lp);

        toast.show();
    }

    public static void toastCenterWithImg(String s, int time, int viewId) {
        if (toast == null) {
//            toast = android.widget.Toast.makeText(TimmyApp.getInstance(), s, time);
        } else {
            toast.cancel();
//            toast = android.widget.Toast.makeText(TimmyApp.getInstance(), s, time);
            toast.setText(s);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
//        ImageView iv = new ImageView(TimmyApp.getInstance());

//        iv.setBackgroundResource(viewId);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.gravity = Gravity.CENTER;
//        iv.setLayoutParams(lp);
//        ((LinearLayout) toast.getView()).addView(iv, 0);

        toast.show();
    }

    public static void toastLongCenterWithImg(String s, int viewId) {
        toastCenterWithImg(s, android.widget.Toast.LENGTH_LONG, viewId);
    }

    public static void toastShortCenterWithImg(String s, int viewId) {
        toastCenterWithImg(s, android.widget.Toast.LENGTH_SHORT, viewId);
    }

}
