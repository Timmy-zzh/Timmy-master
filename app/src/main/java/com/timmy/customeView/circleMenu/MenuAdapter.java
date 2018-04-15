package com.timmy.customeView.circleMenu;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Timmy on 2018/4/15.
 */

public interface MenuAdapter {

    View getView(int position, View view, ViewGroup parent);

    void setItemView(View view,int position);

    int getCount();
}
