package com.timmy.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;

import java.util.ArrayList;
import java.util.List;

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
    private int tabSize = 10;
    private List<View> pageList;
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
        initTabData();
    }

    /**
     * 初始化TabLayout数据
     */
    private void initTabData() {
        //存放viewpager容器
        pageList = new ArrayList(tabSize);

        for (int i = 0; i < tabSize; i++) {
            tl_tab.addTab(tl_tab.newTab().setText("Tab" + i));

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_tab_pager, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_title);
            tv.setText("TAB" + i);
            pageList.add(view);
        }


        adapter = new TabPagerAdapter();
        vp_viewPager.setAdapter(adapter);

        //给viewPager添加监听
        vp_viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_tab));

        //TabLayout添加监听
        tl_tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp_viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        for (int i = 0;i<tabSize;i++){
//            TabLayout.Tab tab = tl_tab.getTabAt(i);
//            if (tab!= null){
//                tab.setCustomView(pageList.get(i));
//            }
//        }


    }

    class TabPagerAdapter extends android.support.v4.view.PagerAdapter{

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(pageList.get(position));
            return pageList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(pageList.get(position));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
