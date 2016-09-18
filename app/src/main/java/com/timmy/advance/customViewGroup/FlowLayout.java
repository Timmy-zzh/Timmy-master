package com.timmy.advance.customViewGroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.timmy.R;

/**
 * 自定义ViewGroup实现流式布局:
 * 里面有很多子View(TextView)往里面添加,
 * 先测量大小:如果大于一行的高度,则后面的子View放到下一行.
 * onLayout()控制位置
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlowLayout, defStyleAttr, 0);
//       getDimension
//        ta.getDimension()
//        ta.getDimensionPixelSize()


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
