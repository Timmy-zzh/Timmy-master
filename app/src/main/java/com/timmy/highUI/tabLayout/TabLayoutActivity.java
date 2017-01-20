package com.timmy.highUI.tabLayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class TabLayoutActivity extends BaseActivity {

    private static final int COUNT_PAGER_FRAGMENT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initToolBar();
        TabLayout tabLayout =  (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        WxFragmentPagerAdaper pagerAdaper = new WxFragmentPagerAdaper(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdaper);
//        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //这两个有什么区别
    //    private class WxFragmentPagerAdaper extends FragmentStatePagerAdapter{
    private class WxFragmentPagerAdaper extends FragmentPagerAdapter{

        public WxFragmentPagerAdaper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return WxPagerFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return COUNT_PAGER_FRAGMENT;
        }
    }

}
