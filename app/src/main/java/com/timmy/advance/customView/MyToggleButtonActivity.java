package com.timmy.advance.customView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

public class MyToggleButtonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
        setContentView(R.layout.activity_toggle_button_view);
    }
}
