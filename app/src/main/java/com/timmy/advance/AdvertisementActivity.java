package com.timmy.advance;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.base.BaseActivity;

/**
 * 3秒倒计时广告页面,可以点击跳过
 * 点击图片进入webView广告页面
 * 不点击3s倒计时后进入下一个界面
 */
public class AdvertisementActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        initToolBar();


    }
}
