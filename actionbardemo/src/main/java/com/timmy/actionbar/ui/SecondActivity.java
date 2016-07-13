package com.timmy.actionbar.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.timmy.actionbar.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
