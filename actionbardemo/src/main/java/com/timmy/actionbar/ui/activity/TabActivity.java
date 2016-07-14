package com.timmy.actionbar.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.timmy.actionbar.R;
import com.timmy.actionbar.listener.MyTabListener;
import com.timmy.actionbar.ui.fragment.TabFragment1;

public class TabActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView();
    }

    private void initView() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //设置导航模式为Tab选项标签导航模式
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //为ActionBar添加Tab选项标签
        actionBar.addTab(actionBar.newTab().setText("TAB1").setTabListener(new MyTabListener<TabFragment1>(this, TabFragment1.class)));
        actionBar.addTab(actionBar.newTab().setText("TAB2").setTabListener(new MyTabListener<TabFragment1>(this, TabFragment1.class)));
        actionBar.addTab(actionBar.newTab().setText("TAB3").setTabListener(new MyTabListener<TabFragment1>(this, TabFragment1.class)));

    }
}
