package com.timmy.customeView.hotTag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.library.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/9.
 * 自定义商品热门标签控件
 * 1.需要重写onMeasure()进行测量
 * 2.onLayout()摆放
 * 3.onDraw()方法在ViewGroup中默认是不会调用的
 * 4.内部子控件排放的时候超出一行需要换行处理
 */
public class TagLayout extends ViewGroup {

    private String TAG = "TagLayout";
    private List<List<View>> views = new ArrayList<>();
    private List<Integer> lineHights = new ArrayList<>();
    private List<View> lineViews = new ArrayList<>();
    private boolean lastChildNextLine;
    //最终确定自己的宽高
    private int mWidth;
    private int mHeight;


    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 测量:
     * 1.先测量所有的子控件的宽高
     * 2.根据子控件宽高和父容器给定的宽高确定自己的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.d(TAG, "onMeasure");
        //父容器给定的宽高
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widhtMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        //子控件确定每行的宽高
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            //先测量子控件-》测量后才能拿到子控件的大小
            View child = getChildAt(i);
            //这三个方法都是用于测量子控件
//            measureChild(child, widthMeasureSpec, heightMeasureSpec);
//            measureChildren(widthMeasureSpec, heightMeasureSpec);
//            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //拿到子控件测量后的宽高
            int childWidth = child.getMeasuredWidth();
            int childHeigth = child.getMeasuredHeight();
            Logger.d(TAG, "childWidth:" + childWidth + ",childHeigth:" + childHeigth);
            //拿到子控件的marger信息
//            LayoutParams layoutParams = child.getLayoutParams();//获取不到margin信息
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            if (lineWidth + childWidth + lp.leftMargin + lp.rightMargin > widthSize) {//换行
                //宽
                lineWidth = childWidth + lp.leftMargin + lp.rightMargin;
                mWidth = Math.max(mWidth, lineWidth);

                //高-累加
                mHeight += (childHeigth + lp.topMargin + lp.bottomMargin);
//                lineHeight = childHeigth + lp.topMargin + lp.bottomMargin;
                if (i == childCount - 1) {
                    lastChildNextLine = true;
                }
            } else {
                //不用换行,宽 - 累加
                lineWidth += (childWidth + lp.leftMargin + lp.rightMargin);
                lineHeight = Math.max(lineHeight, childHeigth + lp.topMargin + lp.bottomMargin);
                if (i == childCount - 1) {
                    lastChildNextLine = false;
                }
            }
            //最后一行处理
            if (i == childCount - 1 && !lastChildNextLine) {
                mWidth = Math.max(mWidth, lineWidth);
                mHeight += (childHeigth + lp.topMargin + lp.bottomMargin);
            }
        }
        Logger.d(TAG, "mWidth:" + mWidth + ",mHeight:" + mHeight);
        //最终调用setMeasureDimention();确定自己的宽高-根据父容器给定的Mode进行判断
        mWidth = widhtMode == MeasureSpec.EXACTLY ? widthSize : mWidth;
        mHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : mHeight;
        setMeasuredDimension(mWidth, mHeight);
    }

    /**
     * 摆放子控件:先计算，后排放
     * 1.使用List<List<View>> views;保存每一行有多少个子控件
     * 2.使用List<Integer> hights;保存每一行的高度
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Logger.d(TAG, "onLayout-changed：" + changed);
        views.clear();
        lineHights.clear();
        lineViews.clear();

        int lineWidth = 0;
        int lineHeight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            if (lineWidth + childWidth + lp.leftMargin + lp.rightMargin > mWidth) {
                //换行,保存这一行所有的子控件最高的高度
                lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
                lineHights.add(lineHeight);
                lineWidth = 0;
                views.add(lineViews);
                lineViews = new ArrayList<>();
            }

            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);
            lineWidth += (childWidth + lp.leftMargin + lp.rightMargin);
            lineViews.add(child);
            if (i == childCount - 1) {
                views.add(lineViews);
                lineHights.add(lineHeight);
            }
        }

        //先计算要排放的位置,最后调用child.layout()方法摆放
        int lc = 0;
        int tc = 0;
        int rc = 0;
        int bc = 0;
        lineWidth = 0;
        int top = 0;

        int rawCount = views.size();//行数
        for (int i = 0; i < rawCount; i++) {
            //这一行的高度
            lineHeight = lineHights.get(i);
            List<View> lineViews = this.views.get(i);
            int colCount = lineViews.size();//一行有多少列
            for (int j = 0; j < colCount; j++) {
                View child = lineViews.get(j);
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                lc = lineWidth + lp.leftMargin;
                rc = lc + childWidth;
                tc = top + lp.topMargin;
                bc = tc + childHeight;

                Logger.d(TAG, "lc:" + lc + ",rc:" + rc + ",tc:" + tc + ",bc:" + bc);
                child.layout(lc, tc, rc, bc);
                lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            }
            top += lineHeight;
            lineWidth = 0;
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //给子控件传递的LayoutParams
        return new MarginLayoutParams(getContext(), attrs);
    }
}
