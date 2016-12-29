package com.timmy.highUI.linearLayoutCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class LinearLayoutCompatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout_compat);
        initToolBar();

        LinearLayoutCompat layoutCompat = (LinearLayoutCompat) findViewById(R.id.linear_layout_compat);
        layoutCompat.setDividerDrawable(getResources().getDrawable(R.drawable.item_divide_decora));


    }
}
