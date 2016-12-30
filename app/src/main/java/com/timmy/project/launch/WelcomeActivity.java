package com.timmy.project.launch;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.home.MainActivity;

/**
 * App启动的第一个界面-实现在该界面中倒计时3秒开启或者直接点击跳过开启下一个界面
 * 1.全屏
 * a.在styles文件中设置样式 -- 推荐
 * b.代码添加Flag,在setContentView方法调用之前处理
 * 2.倒计时
 * 使用Handler和Runnable实现
 * 3.跳转
 * 跳转到首页,首页的启动模式为SingleTask
 * 4.应用按Home键,重新启动界面的时候也会有一个界面,这个界面也有三秒倒计时消失
 * 该界面销毁时,需要处理Handler引用
 */
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private int second = 3;
    private TextView time;

    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time.setText(second + "s");
            second--;
            if (second < 0) {
                gotoNextActivity();
            }
            mHandler.postDelayed(runnable, 1000);
        }
    };

    private void gotoNextActivity() {
        Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        WelcomeActivity.this.startActivity(mainIntent);
        WelcomeActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置界面全屏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //显示状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_start);
        time = (TextView) findViewById(R.id.tv_time);
        LinearLayout mTimeContainer = (LinearLayout) findViewById(R.id.ll_time);
        mTimeContainer.setOnClickListener(this);

        mHandler.postDelayed(runnable, 16);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_time) {
            gotoNextActivity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
    }
}
