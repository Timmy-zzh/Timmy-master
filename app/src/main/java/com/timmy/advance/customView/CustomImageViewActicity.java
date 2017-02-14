package com.timmy.advance.customView;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class CustomImageViewActicity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_image_view);
        initToolBar();
    }
}
