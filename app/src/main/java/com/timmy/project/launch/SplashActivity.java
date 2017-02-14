package com.timmy.project.launch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timmy.R;
import com.timmy.Util;

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

        WowSplashView wowSplashView = (WowSplashView) findViewById(R.id.wowSplash);
        wowSplashView.startAnimate();
        wowSplashView.setOnEndListener(new WowSplashView.OnEndListener() {
            @Override
            public void onEnd(WowSplashView wowSplashView) {
                Util.gotoNextActivity(SplashActivity.this, WelcomeActivity.class);
                overridePendingTransition(R.anim.anim_enter, R.anim.fade_out);
                SplashActivity.this.finish();
            }
        });
    }
}
