package com.timmy.advance.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.R;

/**
 *
 */
public class TabPagerFragment extends Fragment {

    public TabPagerFragment(){}

//    public TabPagerFragment(int type) {
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View pagerView = inflater.inflate(R.layout.item_tab_pager, null);


        return pagerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
