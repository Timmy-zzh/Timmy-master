package com.timmy.advance.customViewGroup;

import android.view.View;

/**
 * Created by Administrator on 2016/9/20.
 */

public abstract class ScrollAdapter {

    public abstract View getView(AdapterScrollView parent,int position);
    public abstract int getCount();

}
