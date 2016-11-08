package com.timmy.advance.motionEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.timmy.library.util.Logger;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MyButton extends Button {

    private static final java.lang.String TAG = MyButton.class.getSimpleName();

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
//        Logger.d(TAG, "--super.dispatchTouchEvent(event):" + super.dispatchTouchEvent(event));
        return super.dispatchTouchEvent(event);
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
//        Logger.d(TAG, "--super.onTouchEvent(event):" + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }
}
