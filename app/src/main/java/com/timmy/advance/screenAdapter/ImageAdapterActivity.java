package com.timmy.advance.screenAdapter;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.base.BaseActivity;

/**
 * 图片适配问题处理
 */
public class ImageAdapterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_adapter);
        initToolBar();

        AdapterImagerView adapterImagerView = (AdapterImagerView) findViewById(R.id.iv_adapter);
        //设置图片宽高比: 900 / 569
        adapterImagerView.setRatio(1.58f);

    }
}
