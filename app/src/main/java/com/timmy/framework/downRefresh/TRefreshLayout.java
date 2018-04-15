package com.timmy.framework.downRefresh;

import android.content.Context;
import android.util.AttributeSet;

/**
 *  xml中使用的下拉刷新控件:设置头部和尾部
 *
 */
public class TRefreshLayout extends TRefreshBaseLayout {


    public TRefreshLayout(Context context) {
        this(context, null);
    }

    public TRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        HeaderView headerView = new HeaderView(getContext());
        setHeaderView(headerView);
    }


}
