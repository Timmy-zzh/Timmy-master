package com.timmy.highUI.motionEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.timmy.library.util.Logger;

/**
 * Created by Administrator on 2016/9/22.
 */

public class MyTextView extends TextView {

    private static final java.lang.String TAG = "timmy";

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        Logger.d(TAG, "--MyTextView--dispatchTouchEvent----"+event.getAction());

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Logger.d(TAG, "--MyTextView--onTouchEvent----"+event.getAction());

        return super.onTouchEvent(event);
    }
}
