package com.timmy.highUI.snackBar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.library.util.Logger;

public class SnackBarActivity extends BaseActivity {

    private final String TAG = this.getClass().getSimpleName();
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_bar);
        initToolBar();

        mContent = (TextView) findViewById(R.id.tv_content);

    }

    public void toastClick(View view) {
//        Toast.makeText(this,"Toast000",Toast.LENGTH_SHORT).show();

        String text = "Custom Toast";
        Toast result = new Toast(this);

        LayoutInflater inflate = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.view_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_content);
        tv.setText(text);

        result.setView(v);
        result.setDuration(Toast.LENGTH_SHORT);
        result.show();
    }

    public void dialogClick(View view) {

    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Logger.d(TAG, "handleMessage" + System.currentTimeMillis());

            Logger.d(TAG, "msg:" + msg.what);
            mContent.setText("handleMessage");
        }
    };

    Handler handler1;

    public void handlerClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                handler1 = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Logger.d(TAG, "handler1" + System.currentTimeMillis());
                        Logger.d(TAG, "msg:" + msg.what);
                        com.timmy.library.util.Toast.toastShort("handler1");
                    }
                };
//                Looper.loop();
                Logger.d(TAG, "start" + System.currentTimeMillis());
                try {
                    Thread.sleep(3000);
//                    mHandler.sendMessage(Message.obtain(mHandler, 333));
                    handler1.sendMessage(Message.obtain(handler1, 333));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();
    }

    public void snackClick(View view) {
        Snackbar snackbar = Snackbar.make(view, "SnackBar解析", Snackbar.LENGTH_SHORT);
        snackbar.show();
        snackbar.setActionTextColor(Color.GREEN);
        snackbar.setAction("确定11", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastClick(null);
                Log.d(TAG, "snackbar -onClick");
            }
        });

        setSnackBarColor(snackbar, Color.BLUE, Color.WHITE);
        snackbarAddView(snackbar,R.layout.view_add,0);
//        snackbarAddView(snackbar,R.layout.view_delete_btn,2);

        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                Logger.d("onDismissed");
            }

            @Override
            public void onShown(Snackbar snackbar) {
                super.onShown(snackbar);
                Logger.d("onShown");
            }
        });
    }

    /**
     * 设置SnackBar颜色
     *
     * @param snackbar
     * @param backgroupColor
     * @param messageColor
     */
    private void setSnackBarColor(Snackbar snackbar, int backgroupColor, int messageColor) {
        View snackbarView = snackbar.getView();
        //设置背景色
        if (snackbarView != null) {
            snackbarView.setBackgroundColor(backgroupColor);
            ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }

    /**
     * 为SnackBar添加布局
     *
     * @param snackbar
     * @param layoutId xml文件id
     * @param index    添加的布局在那个位置
     */
    public static void snackbarAddView(Snackbar snackbar, int layoutId, int index) {
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        View addView = LayoutInflater.from(snackbarLayout.getContext()).inflate(layoutId, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        snackbarLayout.addView(addView, index, lp);
    }

    public void FloatActBtnClick(View view) {
        snackClick(view);
    }
}
