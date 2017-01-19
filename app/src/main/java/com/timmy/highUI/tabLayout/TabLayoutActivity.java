package com.timmy.highUI.tabLayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class TabLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initToolBar();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

//        tabLayout.addTab(tabLayout.newTab().setText("TAB1"));
//        tabLayout.addTab(tabLayout.newTab().setText("TAB2"));
//        tabLayout.addTab(tabLayout.newTab().setText("TAB3"));

    }
}
