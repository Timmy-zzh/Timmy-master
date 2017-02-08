package com.timmy.highUI.coordinatorLayout.behavior.zhihuHome;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.library.util.Logger;

/**
 * Created by admin on 2017/2/8.
 */

public class ZhihuBottomBehavior extends CoordinatorLayout.Behavior<View> {

    public ZhihuBottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //确定所提供的子视图是否有一个特定的同级视图作为布局从属--我们使用AppBarlayout
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    //响应从属布局的变化
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY = Math.abs(dependency.getTop());
        child.setTranslationY(translationY);
        Logger.d("Zhihu", "translationY:" + translationY);
        return true;
    }
}
