package com.timmy.advance.customViewGroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.R;

/**
 * 自定义ViewGroup实现流式布局:
 * 里面有很多子View(TextView)往里面添加,
 * 先测量大小:如果大于一行的高度,则后面的子View放到下一行.
 * onLayout()控制位置
 */
public class FlowLayout extends ViewGroup {

    private static final String TAG = FlowLayout.class.getSimpleName();

    private int horizontalMargin, verticalMargin;//横竖方向的间隔
    private int mWidth, mHeight;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlowLayout, defStyleAttr, 0);
        int defaultValut = (int) TypedValue.complexToDimension(12, getResources().getDisplayMetrics());
        horizontalMargin = ta.getDimensionPixelSize(R.styleable.FlowLayout_horizontalMargin, defaultValut);
        verticalMargin = ta.getDimensionPixelSize(R.styleable.FlowLayout_verticalMargin, defaultValut);
        Log.d(TAG, "--FlowLayout--horizontalMargin:" + horizontalMargin + "--verticalMargin:" + verticalMargin);
        ta.recycle();
    }

    /**
     * onMeasure需要确定父控件和子控件的大小,判断每行子控件的宽度之和是否超过屏幕宽度,超过了,就取下一行.
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        Log.d(TAG, "--onMeasure--widthSize:" + widthSize + "--heightSize:" + heightSize);

        mWidth = widthSize;
        //每行的宽度,子View累加
        int lineWidht = horizontalMargin;
        //每行的高度,累加,当 mode为Wrap_content时,使用累加的高度
        int lineHeigh = verticalMargin;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
//            int childHeight = childView.getMeasuredHeight();
//            int childWidth = childView.getMeasuredWidth();
//            Log.d(TAG, "--onMeasure--childHeight:" + childHeight + "--childWidth:" + childWidth);-->结果为0

//            MarginLayoutParams chileLp = (MarginLayoutParams) childView.getLayoutParams();
            //通过LayoutParams可以获取子View的margin数值
//            chileLp.bottomMargin

            //获取子View的宽高前,需要先进行测量
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            int childHeight = childView.getMeasuredHeight();
            int childWidth = childView.getMeasuredWidth();
            Log.d(TAG, "--onMeasure2222--childHeight:" + childHeight + "--childWidth:" + childWidth);

            if (lineWidht + childWidth + 2*horizontalMargin > widthSize) {
                //超过最大宽度(屏幕宽度),宽度等于当前子View的宽度,高度累加
                lineWidht = childWidth + horizontalMargin;
                lineHeigh = lineHeigh + verticalMargin + childHeight;

            } else {
                //未超过最大宽度,宽度累加
                lineWidht = lineWidht + childWidth + horizontalMargin;
//                lineHeigh = Math.max(childHeight, lineHeigh);
            }

            //最后一个view,高度加上间隔
            if (i == childCount - 1) {
                lineHeigh = lineHeigh + verticalMargin + childHeight;
            }
        }
        //如果mode为Exactly,则使用widthSize;
        mHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : lineHeigh;
        Log.d(TAG, "--onMeasure3333--mWidth:" + mWidth + "--mHeight:" + mHeight);
        Log.d(TAG, "--onMeasure444--lineWidht:" + lineWidht + "--lineHeigh:" + lineHeigh);
        setMeasuredDimension(widthSize, mHeight);
    }

    /**
     * 为每个子View设置位置
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //每行的宽度,子View累加
        int lineWidht = 0;
        //每行的高度,累加,当 mode为Wrap_content时,使用累加的高度
        int lineHeigh = 0;

        int childCount = getChildCount();
        int cl = 0;
        int ct = 0;
        int cr = 0;
        int cb = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            int childHeight = childView.getMeasuredHeight();
            int childWidth = childView.getMeasuredWidth();
            Log.d(TAG, "--onLayout--childHeight:" + childHeight + "--childWidth:" + childWidth);

            if (lineWidht + childWidth + 2*horizontalMargin > mWidth) {
                //超过最大宽度(屏幕宽度),宽度等于当前子View的宽度,高度累加
                lineWidht = childWidth + horizontalMargin;
                lineHeigh = lineHeigh + verticalMargin + childHeight;
                cl = horizontalMargin;
                ct = lineHeigh + verticalMargin;
                cr = cl + childWidth;
                cb = ct + childHeight;

            } else {
                cl = lineWidht + horizontalMargin;
                ct = lineHeigh + verticalMargin;
                cr = cl + childWidth;
                cb = ct + childHeight;
                //未超过最大宽度,宽度累加
//                lineHeigh = Math.max(childHeight, lineHeigh);
                lineWidht = lineWidht + childWidth + horizontalMargin;
            }
            Log.d(TAG, "--onLayout222--cl:" + cl + "--ct:" + ct);
            childView.layout(cl, ct, cr, cb);
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


}
