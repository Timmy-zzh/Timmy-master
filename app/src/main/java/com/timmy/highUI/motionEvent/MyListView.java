package com.timmy.highUI.motionEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by timmy1 on 17/1/27.
 * 事件拦截处理：当listview滑动到底部时，让父容器进行事件拦截，
 * 问题1:我怎么知道滑动到了最后一个条目：即最后一个item全部条目出现，并且往上拉时，
 * 问题2:让scrollview进行事件拦截
 */
public class MyListView extends ListView {

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
