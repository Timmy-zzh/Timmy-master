package com.timmy.project.launch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timmy.R;

/**
 * 闪屏页面:该界面停留一秒,之后就会像云散开一样关闭
 * 1.云扩散效果
 * 2.SVG图片效果
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
