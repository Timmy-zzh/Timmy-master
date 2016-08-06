package com.timmy.advance.customViewGroup;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

/**
 * 自定义ViewGroup实现ViewPager效果
 */
public class CustomViewPagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_pager);
        initToolBar();

    }
}
