package com.timmy.advance.waterRipple;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.base.BaseActivity;

/**
 * 自定义View实现水波纹效果
 */
public class WaterRippleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_ripple);
        initToolBar();

    }
}
