package com.timmy.highUI.collapsingToolbarLayout;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.home.MainFragment;
import com.timmy.home.MainPageFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollapsingToolbarLayoutActivity extends BaseActivity {


    @Bind(R.id.tl_tab)
    TabLayout tl_tab;
    @Bind(R.id.vp_viewPage)
    ViewPager vp_viewPager;
    private TabPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar_layout);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 初始化TabLayout数据
     */
    private void initView() {
        adapter = new TabPagerAdapter(getSupportFragmentManager());
        vp_viewPager.setAdapter(adapter);
        tl_tab.setupWithViewPager(vp_viewPager);
    }

    class TabPagerAdapter extends FragmentStatePagerAdapter {
        private String tabTitles[] = new String[]{"高级ui","源码", "框架"};
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MainPageFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
