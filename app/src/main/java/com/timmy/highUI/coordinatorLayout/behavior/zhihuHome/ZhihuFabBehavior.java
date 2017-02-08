package com.timmy.highUI.coordinatorLayout.behavior.zhihuHome;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

import com.timmy.library.util.Logger;

/**
 * Created by admin on 2017/2/8.
 * 高仿知乎首页右下角的Fab按钮和CoordinatorLayout 实现类Behavior进行联动实现显示隐藏效果
 */
public class ZhihuFabBehavior extends CoordinatorLayout.Behavior<View> {

    private String TAG = "ZhihuFabBehavior";
    private float viewY;//控件距离CoordinatorLayout底部距离
    private boolean isAnimating;//动画是否正在执行


    public ZhihuFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //确定所提供的子视图是否有一个特定的同级视图作为布局从属--我们使用AppBarlayout
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    //响应从属布局的变化
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    //在嵌套滑动开始前调用
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        Logger.d(TAG, "child.getY():" + child.getY());
        if (child.getVisibility() == View.VISIBLE && viewY == 0) {
            viewY = coordinatorLayout.getHeight() - child.getY();
        }
        //判断是否是垂直滚动
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    //在嵌套滑动进行时,对象消费滑动距离前调用
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        Logger.d(TAG, "dy:" + dy + ",viewY:" + viewY);
        //dy>0 向上滚动--隐藏, 小于0向下滚动--显示
        if (dy > 0 && !isAnimating && child.getVisibility() == View.VISIBLE) {
            hide(child);
        } else if (dy < 0 && !isAnimating && child.getVisibility() == View.GONE) {
            show(child);
        }
    }

    //隐藏动画
    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(viewY)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void show(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
