package com.timmy.highUI.motionEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.timmy.library.util.Logger;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MyButton extends Button {

    private static final java.lang.String TAG = "timmy";

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

        Logger.d(TAG, "--MyButton--dispatchTouchEvent----"+event.getAction());

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

        Logger.d(TAG, "--MyButton--onTouchEvent----"+event.getAction());

        return super.onTouchEvent(event);
    }
}
