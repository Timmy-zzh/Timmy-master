package com.timmy.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.timmy.R;
import com.timmy.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class MainFragment extends BaseFragment {

    @Bind(R.id.tl_tab)
    TabLayout tl_tab;
    @Bind(R.id.vp_viewPage)
    ViewPager vp_viewPager;
    private TabPagerAdapter adapter;

    @Override
    public int inflaterFragment() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化TabLayout数据
     */
    private void initView() {
        adapter = new TabPagerAdapter(getActivity().getSupportFragmentManager());
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
