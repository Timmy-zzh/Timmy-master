package com.timmy.highUI.motionEvent.nest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.timmy.library.util.Logger;

/**
 * Created by timmy1 on 17/1/27.
 * 事件拦截处理：当listview滑动到底部时，让父容器进行事件拦截，
 * 问题1:我怎么知道滑动到了最后一个条目：即最后一个item全部条目出现，并且往上拉时，
 * 问题2:让scrollview进行事件拦截
 */
public class MyListView extends ListView implements AbsListView.OnScrollListener {

    private float downY;
    private int itemCount;
    private String TAG = this.getClass().getSimpleName();
    private int mOverScrollY = -1;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        ListAdapter adapter = getAdapter();

//        ViewGroup
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemCount = getCount();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = ev.getY() - downY;
                //滚动到最后一行，且还在往上拉
                int lastVisiblePosition = getLastVisiblePosition();
                int firstVisiblePosition = getFirstVisiblePosition();
//                Logger.d(TAG, "------------firstVisiblePosition:" + firstVisiblePosition + ",lastVisiblePosition:" + lastVisiblePosition);

                if (dy < 0) {
                    if (lastVisiblePosition == itemCount - 1 && itemCount > 0 && mOverScrollY == 0) {
                        //让父容器拦截
                        getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        mOverScrollY = -1;
                        return false;
                    }
                } else if (dy > 0) {
                    int scrollY = getScrollY();
                    int top = getTop();

//                    Logger.d(TAG,"scrollY:"+scrollY+",top:"+top);
                    if (firstVisiblePosition == 0 && mOverScrollY == 0) {
                        //让父容器拦截
                        getParent().getParent().requestDisallowInterceptTouchEvent(false);
                        mOverScrollY = -1;
                        return false;
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        super.setOnScrollChangeListener(l);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        super.setOnScrollListener(l);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

//        Logger.d(TAG,"firstVisibleItem:"+firstVisibleItem+",visibleItemCount:"+visibleItemCount+",totalItemCount:"+totalItemCount);
//        getLastVisiblePosition() -visibleItemCount
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        Logger.d(TAG, "scrollY:" + scrollY + ",clampedY:" + clampedY);
        mOverScrollY = scrollY;
    }
}
