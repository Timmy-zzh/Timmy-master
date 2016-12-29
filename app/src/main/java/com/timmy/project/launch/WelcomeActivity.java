package com.timmy.project.launch;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.home.MainActivity;

/**
 * App启动的第一个界面-实现在该界面中倒计时3秒开启或者直接点击跳过开启下一个界面
 * 1.全屏
 *      a.在styles文件中设置样式
 *      b.代码添加Flag,在setContentView方法调用之前处理
 *
 * 2.倒计时
 * 3.跳转
 * 4.应用按Home键,重新启动界面的时候也会有一个界面,这个界面也有三秒倒计时消失
 */
public class WelcomeActivity extends AppCompatActivity {

    private int second = 3;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            time.setText("倒计时"+second+"s");
            second--;
            if (second ==0){
               gotoNextActivity();
            }

            mHandler.sendMessageDelayed(Message.obtain(),1000);
        }
    };
    private TextView time;

    private void gotoNextActivity(){
        Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        WelcomeActivity.this.startActivity(mainIntent);
//        WelcomeActivity.this.finish();
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

        mHandler.handleMessage(Message.obtain());

//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
//                WelcomeActivity.this.startActivity(mainIntent);
//                WelcomeActivity.this.finish();
//            }
//        }, 2900);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mHandler.removeCallbacks();
    }
}
