package com.timmy.highUI.motionEvent.nest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by timmy1 on 17/1/27.
 */

public class MyScrollView extends ScrollView {

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //不拦截
        requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
//        return  false;
    }
}
