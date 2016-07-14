package com.timmy.actionbar.listener;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class MyTabListener<T extends Fragment> implements ActionBar.TabListener {

    private  Fragment fragment;
    private Activity mActivity;
    private Class<T> mClass;

    public MyTabListener(Activity activity,Class<T> clazz){
        mActivity = activity;
        mClass = clazz;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if(fragment == null){
            fragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, fragment, null);
        }
        ft.attach(fragment);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (fragment != null) {
            ft.detach(fragment);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
