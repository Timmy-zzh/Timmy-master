package com.timmy.advance.animation;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.R;

/**
 * Created by admin on 2016/9/13.
 */
public class CircleView extends View {

    private int mBgColor;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleView, defStyleAttr, 0);
        mBgColor = ta.getColor(R.styleable.CircleView_bg_color, Color.YELLOW);
        ta.recycle();

        init();

    }

    private void init() {
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        PropertyValuesHolder
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int centreX = right / 2 + left / 2;
        int centreY = top / 2 + bottom / 2;

    }
}
