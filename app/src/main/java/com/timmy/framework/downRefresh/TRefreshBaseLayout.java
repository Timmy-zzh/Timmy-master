package com.timmy.framework.downRefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.R;

/**
 * 可以下拉刷新,也可以上拉加载更多的控件
 * <p>
 * 目标: 下拉刷新:
 * 1.往下拉动,头部出现,超过1.2倍,显示释放刷新
 * 2.往下拉最长距离是 1.5倍
 * <p>
 * 3.手指释放时: 为正在刷新状态,
 * 4.刷新完成后头部回弹到原始状态
 * 5.动画处理
 * <p>
 * <p>
 * 1.设置头部
 * 2.测量  :  头部高度处理LayoutParams
 * 3.摆放
 * 4.滑动事件处理
 */
public class TRefreshBaseLayout extends ViewGroup {

    private String TAG = this.getClass().getSimpleName();

    //    private int loadViewBgColor;
//    private final int mLoadViewTextColor;
//    private final int mProgressBgColor;
//    private final int mProgressColor;
//    private final String mPullRefreshText;
//    private final String mPullLoadText;
//    private final boolean mAutoLoadMore;
//    private final boolean mPullRefreshEnable;
//    private final boolean mPullLoadEnable;
    private View headerView;
    private View contentView;
    private int headerHeight;
    private boolean isRefreshing;

    public TRefreshBaseLayout(Context context) {
        this(context, null);
    }

    public TRefreshBaseLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TRefreshBaseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TRefreshLayout);
//        //获取LoadView背景颜色
//        loadViewBgColor = ta.getColor(R.styleable.TRefreshLayout_load_view_bg_color, Color.WHITE);
//        //加载文字颜色
//        mLoadViewTextColor = ta.getColor(R.styleable.TRefreshLayout_load_text_color, Color.BLACK);
//        //进度条背景颜色
//        mProgressBgColor = ta.getColor(R.styleable.TRefreshLayout_progress_bg_color, Color.WHITE);
//        //进度条颜色
//        mProgressColor = ta.getColor(R.styleable.TRefreshLayout_progress_bar_color, Color.RED);
//        //下拉刷新文字描述
//        mPullRefreshText = ta.getString(R.styleable.TRefreshLayout_pull_refresh_text);
//        //上拉加载文字描述
//        mPullLoadText = ta.getString(R.styleable.TRefreshLayout_pull_load_text);
//
//        mAutoLoadMore = ta.getBoolean(R.styleable.TRefreshLayout_auto_load_more, false);
//        mPullRefreshEnable = ta.getBoolean(R.styleable.TRefreshLayout_pull_refresh_enable, true);
//        mPullLoadEnable = ta.getBoolean(R.styleable.TRefreshLayout_pull_load_enable, true);
//
//        //回收
//        ta.recycle();
    }


    public void setHeaderView(HeaderView headerView) {
        if (headerView == null) {
            return;
        }
        if (headerView == this.headerView) {
            return;
        }

        ViewGroup.LayoutParams layoutParams = headerView.getLayoutParams();
        if (layoutParams == null) {  // 因为HeaderView是代码直接new的,所以LayotuParams为null,需要代码设置
            Log.d(TAG, "setHeaderView   layoutParams ==null");
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(layoutParams);
        }

        this.headerView = headerView;
        addView(headerView, 0);
    }

    /**
     * 内容View布局完成调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
        int childCount = getChildCount();
        View childAt0 = getChildAt(0);
        contentView = getChildAt(1);
        Log.d(TAG, "childCount:" + childCount + " , childAt0: " + childAt0 + ", childAt1: " + contentView);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //添加padding  和 子类自己的margin
        //测量  -- 简单处理
        if (headerView != null) {
            measureChildWithMargins(headerView, widthMeasureSpec, 0, heightMeasureSpec, 0);
            //获取头部的高度
            headerHeight = headerView.getMeasuredHeight();
        }

        if (contentView != null) {
            measureChildWithMargins(contentView, widthMeasureSpec, 0, heightMeasureSpec, 0);
//            contentView.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //控件摆放,不考虑 间隔
        Log.d(TAG, "headerView  getMeasuredWidth:" + headerView.getMeasuredWidth() + " , getMeasuredHeight: " + headerView.getMeasuredHeight());
        Log.d(TAG, "headerView  headerHeight:" + headerHeight);
        Log.d(TAG, "contentView  getMeasuredWidth:" + contentView.getMeasuredWidth() + " , getMeasuredHeight: " + contentView.getMeasuredHeight());

        if (headerView != null) {
            headerView.layout(0, -headerHeight, headerView.getMeasuredWidth(), 0);
        }

        if (contentView != null) {
            contentView.layout(0, 0, contentView.getMeasuredWidth(), contentView.getMeasuredHeight());
        }
    }

    int downY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isRefreshing) {
            return super.dispatchTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                Log.d(TAG, "ACTION_DOWN  downY: " + downY);
                Log.d(TAG, "super.dispatchTouchEvent(ev): " + super.dispatchTouchEvent(ev));

                return true;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                int dy = moveY - downY;
                downY = moveY;
                //头部和内容控件往下偏移
                startMove(dy);
                Log.d(TAG, " ACTION_MOVE  downY: " + moveY + ", dy:" + dy);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, " ACTION_UP ");
                //释放,头部隐藏  记录当前向下滑动了多少距离
                headerView.offsetTopAndBottom(-scrollDis);
                contentView.offsetTopAndBottom(-scrollDis);

                scrollDis = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param dy
     */
    int scrollDis = 0;//手指下拉距离

    private void startMove(int dy) {
        Log.d(TAG, "headerView  headerHeight:" + headerHeight + ",scrollDis:" + scrollDis);
//        HeaderView header = (HeaderView) headerView;
//        if (scrollDis > headerHeight * 3 * 1.2) {
//            header.setTextState("释放可刷新");
//        } else {
//            header.setTextState("下拉可刷新");
//        }

//        if (scrollDis > headerHeight * 3 * 1.5) {
//            return;
//        }

        scrollDis += dy;

        headerView.offsetTopAndBottom(dy);
        contentView.offsetTopAndBottom(dy);

        //
        float headerX = headerView.getX();
        float headerY = headerView.getY();

        float contentX = contentView.getX();
        float contentY = contentView.getY();

        Log.d(TAG, "headerView  x:" + headerX + " ,y:" + headerY + ", getTop:" + headerView.getTop());
        Log.d(TAG, "contentView  x:" + contentX + " ,y:" + contentY);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        @SuppressWarnings({"unused"})
        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
