package com.timmy.advance.motionEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.timmy.library.util.Logger;

/**
 * ViewGroup事件分发过程:
 */
public class MyLinearLayout extends LinearLayout {

    private static final java.lang.String TAG = MyLinearLayout.class.getSimpleName();

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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d(TAG, "--dispatchTouchEvent--ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG, "--dispatchTouchEvent--ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Logger.d(TAG, "--dispatchTouchEvent--ACTION_UP");
                break;
        }
        Logger.d(TAG, "--super.dispatchTouchEvent(event):" + super.dispatchTouchEvent(event));
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d(TAG, "--onInterceptTouchEvent--ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG, "--onInterceptTouchEvent--ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Logger.d(TAG, "--onInterceptTouchEvent--ACTION_UP");
                break;
        }
        Logger.d(TAG, "--ViewGroup默认不拦截事件--super.onInterceptTouchEvent(event):" + super.onInterceptTouchEvent(event));
        return super.onInterceptTouchEvent(event);
    }

    /**
     * 设置事件拦截
     *
     * @param disallowIntercept true为不拦截
     */
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Logger.d(TAG, "--requestDisallowInterceptTouchEvent:" + disallowIntercept);
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d(TAG, "--onTouchEvent--ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG, "--onTouchEvent--ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Logger.d(TAG, "--onTouchEvent--ACTION_UP");
                break;
        }
        Logger.d(TAG, "--super.onTouchEvent(event):" + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }


}
