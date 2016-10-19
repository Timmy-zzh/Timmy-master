package com.timmy.advance.customViewGroup;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.timmy.library.util.Logger;

/**
 * 实现可以拖拽的控件,控制子View的拖拽,试用DragVieHelp类
 */
public class DragViewGroup extends RelativeLayout {

    private  final java.lang.String TAG =this.getClass().getSimpleName();

    private ViewDragHelper viewDragHelper;
    private View floatChild;//悬浮窗子控件

    public DragViewGroup(Context context) {
        this(context,null);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                Logger.d(TAG,"child == floatChild:"+ child );
                return child == floatChild;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                Logger.d(TAG,"left:"+left);
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Logger.d(TAG,"top:"+top);
                return top;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return 100;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return 100;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                viewDragHelper.captureChildView(floatChild,pointerId);
            }
        });
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT|ViewDragHelper.EDGE_RIGHT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        floatChild = getChildAt(childCount - 1);
        Logger.d(TAG,"childCount:"+childCount+"--childView:"+floatChild);
    }
}
