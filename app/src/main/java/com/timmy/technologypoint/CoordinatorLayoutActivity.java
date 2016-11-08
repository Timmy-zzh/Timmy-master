package com.timmy.technologypoint;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.base.BaseActivity;

import butterknife.ButterKnife;

public class CoordinatorLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);
        initToolBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
