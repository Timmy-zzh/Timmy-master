package com.timmy.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by admin on 2017/3/1.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<String> mData;

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.mData = list;
    }

    @Override
    public Fragment getItem(int position) {
        return SimplePagerFragment.newInstance(position,mData.get(position));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position);
    }
}
