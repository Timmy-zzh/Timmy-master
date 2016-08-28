package com.timmy.advance.customView;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

public class RingViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_view);
        initToolBar();
    }
}
