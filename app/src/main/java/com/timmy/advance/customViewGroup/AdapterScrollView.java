package com.timmy.advance.customViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.timmy.library.util.Logger;
import com.timmy.library.util.ScreenUtils;


/**
 * 自定义ScrollView实现的效果为ScrollView内部有很多Item,item的个数由用户决定,高度平均分配屏幕高度.
 * 当Item滑动时判断滑动高度是否超过item高度一半,未超过一半,重置为0
 * <p>
 * 实现原理:ScrollView内部保持一个LinearLayout容器,保持Item+1个,并且动态的增加减少Item.
 * <p>
 * 在onMeasure方法中获取屏幕高度,将高度平均分成item份,
 */
public class AdapterScrollView extends ScrollView {

    private static final java.lang.String TAG = AdapterScrollView.class.getSimpleName();
    private ScrollAdapter mAdapter;
    private int mParentHeight;
    private int itemCount;//item个数
    private int itemHeight;//屏幕高度
    private ViewGroup mContainer;
    private boolean flag;
    private int scrollY;

    public AdapterScrollView(Context context) {
        this(context, null);
    }

    public AdapterScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //先获取需要的高度,需要使用屏幕的高度减去ToolBar和状态栏的高度
        mParentHeight = ScreenUtils.getScreenHeight(context) - ScreenUtils.getStatusHeight(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!flag) {
            Logger.d(TAG, "---onMeasure----");
            mContainer = (ViewGroup) getChildAt(0);
            if (mAdapter != null) {
                itemCount = mAdapter.getCount();

                itemHeight = mParentHeight / itemCount;
                Logger.d(TAG, "count:" + itemCount + "--mParentHeight:" + mParentHeight + "--itemHeight:" + itemHeight);
                for (int i = 0; i < itemCount; i++) {
                    addChildView(i);
                }
            }
            addChildView(0);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 先添加item个内部子View
     *
     * @param i
     */
    private void addChildView(int i) {
        View childView = mAdapter.getView(this, i);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
        childView.setLayoutParams(layoutParams);

        childView.setTag(i);
        mContainer.addView(childView);
    }

    //根据滑动的高度,判断
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        flag = true;
        scrollY = getScrollY();
        Logger.d(TAG, "--ScrollView的Y方向上滑动的距离--scrollY:" + scrollY);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setAdapter(ScrollAdapter adapter) {
        this.mAdapter = adapter;
    }
}
