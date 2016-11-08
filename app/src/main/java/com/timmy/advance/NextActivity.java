package com.timmy.advance;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class NextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        initToolBar();
    }
}
