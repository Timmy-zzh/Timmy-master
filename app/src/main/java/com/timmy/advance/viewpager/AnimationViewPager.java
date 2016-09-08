package com.timmy.advance.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;
import com.timmy.library.util.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2016/9/8.
 */
public class AnimationViewPager extends ViewPager {

    private static final java.lang.String TAG = AnimationViewPager.class.getSimpleName();
    private static final float SCALE_MAX = 0.5f;
    private HashMap<Integer, View> pagerItems = new LinkedHashMap<Integer, View>();

    public AnimationViewPager(Context context) {
        super(context);
    }

    public AnimationViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * ViewPager滑动时调用的方法
     *
     * @param position     代表ViewPager当前的ItemPager
     * @param offset       手指从右向左滑动(往下一页),offset变化为[0->1],
     *                     从左往右滑动(往上一页)[1->0]
     * @param offsetPixels 代表滑动的像素值
     */
    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        Logger.d(TAG, "--onPageScrolled--position:" + position + "--offset" + offset + "--offsetPixels" + offsetPixels);
        //获取左右ItemPager的实例
        View leftView = pagerItems.get(position);
        View rightView = pagerItems.get(position + 1);

        //为左右的Pager做动画
        pagerAnimation(leftView, rightView, offset, offsetPixels);

        super.onPageScrolled(position, offset, offsetPixels);
    }

    /**
     * 做右边pager的缩放和位移动画
     *
     * @param leftView
     * @param rightView
     * @param offset
     * @param offsetPixels
     */
    private void pagerAnimation(View leftView, View rightView,
                                float offset, int offsetPixels) {
        if (rightView != null) {
            //到下一页offset(0->1) 右边的pager应该放大(0.5->1) 到上一页(1->0)
            float scale = (1 - SCALE_MAX) * offset + SCALE_MAX;

            ViewHelper.setScaleX(rightView, scale);
            ViewHelper.setScaleY(rightView, scale);

            Logger.d(TAG, "--pagerAnimation--width:" + getWidth() + "--pagerMargin:" + getPageMargin());
            int transX = -getWidth() - getPageMargin() + offsetPixels;
            ViewHelper.setTranslationX(rightView, transX);

        }

        if (leftView != null) {
            leftView.bringToFront();
        }
    }

    public void setObjectForPosition(int position, View view) {
        pagerItems.put(position, view);
    }
}
