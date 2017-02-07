package com.timmy.customeView.clockView;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class ClockViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_view);
        initToolBar();

    }
}
