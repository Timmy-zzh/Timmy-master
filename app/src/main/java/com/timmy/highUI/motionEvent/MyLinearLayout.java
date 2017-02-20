package com.timmy.highUI.motionEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.timmy.library.util.Logger;

/**
 * ViewGroup事件分发过程:
 */
public class MyLinearLayout extends LinearLayout {

    private static final java.lang.String TAG ="timmy";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 事件分发
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Logger.d(TAG, "--MyLinearLayout--dispatchTouchEvent--"+event.getAction());
        return super.dispatchTouchEvent(event);
    }

    /**
     * 事件拦截
     *
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Logger.d(TAG, "--MyLinearLayout--onInterceptTouchEvent--"+event.getAction());
        return super.onInterceptTouchEvent(event);
//        return true;
    }

    /**
     * 设置事件拦截
     *
     * @param disallowIntercept true为不拦截
     */
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Logger.d(TAG, "--MyLinearLayout--requestDisallowInterceptTouchEvent:" + disallowIntercept);
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    /**
     * 事件消费
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.d(TAG, "--MyLinearLayout--onTouchEvent--"+event.getAction());
        return super.onTouchEvent(event);
    }


}
