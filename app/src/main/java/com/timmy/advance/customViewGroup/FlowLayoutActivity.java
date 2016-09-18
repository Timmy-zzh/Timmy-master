package com.timmy.advance.customViewGroup;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

/**
 * 流式布局实现:用于热门标签的展示
 */
public class FlowLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        initToolBar();

    }
}
