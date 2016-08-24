package com.timmy.advance.viewpagerIndicator;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.timmy.R;
import com.timmy.ui.base.BaseActivity;
import com.timmy.viewpagerindicator.TabPageIndicator;

/**
 * ViewPager指示器
 */
public class ViewPagerIndicatiorActivity extends BaseActivity {

    private TabPageIndicator pageIndicator;
    private ViewPager viewPager;
    private TabPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_indicatior);
        initToolBar();
        initView();
    }

    private void initView() {
        pageIndicator = (TabPageIndicator) findViewById(R.id.pi_indicator);
        viewPager = (ViewPager) findViewById(R.id.vp_viewPage);
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        pageIndicator.setViewPager(viewPager, 0);
    }
}
