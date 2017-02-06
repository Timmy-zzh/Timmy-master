package com.timmy.technologypoint;

import android.os.Bundle;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class Picture9Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture9);
        initToolBar();
        ImageView imageView = (ImageView) findViewById(R.id.iv_pic1);

    }
}
