package com.timmy.highUI.coordinatorLayout.behavior;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class CustomeBehaviorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome_behavior);
        initToolBar();
    }

    public void rotate(View view) {
        Snackbar.make(view, "Snack:", 500).show();
    }
}
