package com.timmy.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class MainFragment extends Fragment {

    @Bind(R.id.tl_tab)
    TabLayout tl_tab;
    @Bind(R.id.vp_viewPage)
    ViewPager vp_viewPager;
    private int tabSize = 3;
    private TabPagerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * 初始化TabLayout数据
     */
    private void initView() {

        adapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());
        vp_viewPager.setAdapter(adapter);
        //给viewPager添加监听
//        vp_viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_tab));

        tl_tab.setupWithViewPager(vp_viewPager);
//        tl_tab.setTabMode(TabLayout.MODE_FIXED);
//        tl_tab.setTabMode(TabLayout.MODE_SCROLLABLE);单个Tab会挤到一块去


        //TabLayout添加监听
//        tl_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                vp_viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    class TabPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[]{"技术点", "源码", "框架"};
        private Context context;

        public TabPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return TabPageFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
