package com.timmy.advance.customViewGroup;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

public class ReboundScrollViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebound_scroll_view);
        initToolBar();
    }
}
