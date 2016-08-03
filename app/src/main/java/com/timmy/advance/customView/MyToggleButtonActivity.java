package com.timmy.advance.customView;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

public class MyToggleButtonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_button_view);
        initToolBar();

        MyToggleButton myToggleButton = (MyToggleButton) findViewById(R.id.tbtn_toggle);
    }
}
