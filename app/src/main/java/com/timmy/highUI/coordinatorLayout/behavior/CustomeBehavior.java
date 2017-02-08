package com.timmy.highUI.coordinatorLayout.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by admin on 2017/2/8.
 */

public class CustomeBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    public CustomeBehavior() {
    }

    public CustomeBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 返回true,说明child的滑动会根据parent来处理
     *
     * @param parent     父容器
     * @param child      观察者
     * @param dependency 被观察者（需要监听的那个View）
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    //处理-实现自己的Behavior功能-旋转

    /**
     * 当被监听的view发生变化的时候回调
     * 可以在此方法里面做一些响应的联动动画等效果
     *
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        float translationY = getFabTranslationYForSnackbar(parent, child);
        float percentComplete = -translationY / dependency.getHeight();
        child.setRotation(-90 * percentComplete);
        child.setTranslationY(translationY);
        return false;
    }

    private float getFabTranslationYForSnackbar(CoordinatorLayout parent,
                                                FloatingActionButton fab) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(fab);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(fab, view)) {
                minOffset = Math.min(minOffset,
                        ViewCompat.getTranslationY(view) - view.getHeight());
            }
        }

        return minOffset;
    }
}

