package com.timmy.customeView.myIndicator;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;

import com.timmy.R;
import com.timmy.home.adapter.SimpleFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyIndicatorActivity extends AppCompatActivity {

    private static String[] titleStrArr = {"天空之城","音乐魅力","生命意义","树木成荫","深圳多少","卡农上课"};
    private static List<String> mData = Arrays.asList(titleStrArr);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_indicator);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyIndicatorLayout indicatorLayout = (MyIndicatorLayout) findViewById(R.id.indicator_layout);
        SimpleFragmentPagerAdapter pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),mData);
        viewPager.setAdapter(pagerAdapter);

        indicatorLayout.setViewPager(viewPager);
    }
}
