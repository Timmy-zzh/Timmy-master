package com.timmy.advance.customViewGroup;

import android.os.Bundle;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

/**
 * 自定义ViewGroup实现ViewPager效果
 * 使用ViewGroup类的addView()方法,往里面添加子View,
 */
public class CustomViewPagerActivity extends BaseActivity {

    private int[] images = new int[]{R.mipmap.ic_5, R.mipmap.ic_7,
            R.mipmap.ic_10, R.mipmap.img_beauti, R.mipmap.ic_17};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_pager);
        initToolBar();

        MyViewPager myViewPager = (MyViewPager) findViewById(R.id.vp_viewPage);

        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(images[i]);
            myViewPager.addView(imageView);
        }

    }
}
