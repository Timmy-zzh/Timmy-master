package com.timmy.advance.scrollvertical;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

import com.timmy.util.Logger;

/**
 * 实现垂直滚动效果的自定义View
 * 继承自ViewGroup,通过getChildAt获取内部包含的子控件
 * 使用ScrollBy方法,和Scroll让界面滑动起来.
 * 监听onTouch事件,判断滑动的距离,实现ViewGroup中子控件的滑动
 */
public class VerticalScrollView extends ViewGroup {


    private final String TAG = this.getClass().getSimpleName();
    private int mScreenHeight;//屏幕高度
    private final Scroller mScroller;//滑动的辅助类
    private boolean isScrolling;//标记当前控件是否正在滑动
    private VelocityTracker mVelocityTracker;//加速度检测器
    private int mScrollStart;//手指按下时的ScrollY
    private int mScrollEnd;//手指抬起是的getScrollY
    private int mLastY;//记录移动时的Y
    private int currentPage = 0;//记录当前页
    private OnPagerChangeListener mListener;

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.d(TAG, "--VerticalScrollView-");
        /**
         * 获取屏幕的高度
         */
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        Logger.d(TAG, "--mScreenHeight-" + mScreenHeight);

        //初始化
        mScroller = new Scroller(context);
    }

    /**
     * 测量方法,父类和子类都要进行测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.d(TAG, "--onMeasure-");
        //获取各个子类,进行测量
        int count = getChildCount();
        Logger.d(TAG, "--getChildCount-" + count);
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * @param changed 表示该控件布局是否有变化
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Logger.d(TAG, "--onLayout--changed-" + changed);
        if (changed) {
            int childCount = getChildCount();
            //设置父类布局的高度
            MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
            mlp.height = mScreenHeight * childCount;
            setLayoutParams(mlp);
            Logger.d(TAG, "--父类布局的高度-" + mlp.height);
            //设置每个子控件的onLayout方法
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                //子控件不隐藏-->就去调用layout方法
                if (childAt.getVisibility() != View.GONE) {
                    childAt.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
                }
            }
        }
    }

    /**
     * 当前控件的点击事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isScrolling)
            return super.onTouchEvent(event);
        int action = event.getAction();
        int y = (int) event.getY();
        Logger.d(TAG, "--onTouchEvent--getY-" + y);
        obtainVelocity(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //手指按下,获取当前手指按下时的y轴坐标
                mScrollStart = getScrollY();
                mLastY = y;
                Logger.d(TAG, "--ACTION_DOWN--mScrollStart-" + mScrollStart + "--mLastY-" + mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                //手指移动时
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                //获取手指移动的距离,根据该距离判断是向上滑,还是向下滑,和边界的处理
                int dy = mLastY - y;
                int scrollY = getScrollY();
                Logger.d(TAG, "--ACTION_MOVE--scrollY-" + scrollY + "--dy-" + dy);

                //已经到达顶端,下拉多少,就往上滚动多少
                if (dy < 0 && scrollY + dy < 0) {
                    dy = -scrollY;
                }

                //已经到达底部,上拉多少,就往下滚动多少
                if (dy > 0 && scrollY + dy > getHeight() - mScreenHeight) {
                    dy = getHeight() - mScreenHeight - scrollY;
                }
                Logger.d(TAG, "--scrollBy--dy-" + dy);
                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mScrollEnd = getScrollY();

                int dScrollY = mScrollEnd - mScrollStart;
                Logger.d(TAG, "--ACTION_UP--getScrollY-" + mScrollEnd + "-dScrollY-" + dScrollY);

                //往上滑动-->根据滑动距离是否可以往上滑动
                if (wantScrollToNext()) {
                    if (shouldScrollToNext()) {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }

                //往下滑动
                if (wantScrollToPre()) {
                    if (shouldScrollToPre()) {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }
                }

                isScrolling = true;
                postInvalidate();
                recyclerVelocity();
                break;
        }
        return true;
    }

    /**
     * 释放资源
     */
    private void recyclerVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private boolean shouldScrollToPre() {
        return -mScrollEnd + mScrollStart > mScreenHeight / 2 || Math.abs(getVelocity()) > 600;
    }

    private boolean wantScrollToPre() {
        return mScrollEnd < mScrollStart;
    }

    /**
     * 超过半屏
     *
     * @return
     */
    private boolean shouldScrollToNext() {
        return (mScrollEnd - mScrollStart) > (mScreenHeight / 2) || Math.abs(getVelocity()) > 600;
    }

    /**
     * 获取Y方向的加速度
     *
     * @return
     */
    private int getVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        return (int) mVelocityTracker.getYVelocity();
    }

    private boolean wantScrollToNext() {
        return mScrollEnd > mScrollStart;
    }

    /**
     * 初始化加速度检测器
     *
     * @param event
     */
    private void obtainVelocity(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Logger.d(TAG, "--computeScroll");

        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            Logger.d(TAG, "--computeScroll--getCurrY-" + mScroller.getCurrY());
            postInvalidate();
        } else {
            int position = getScrollY() / mScreenHeight;
            Logger.d(TAG, "--computeScroll--position-" + position);
            if (position != currentPage) {
                if (mListener != null) {
                    currentPage = position;
                    mListener.onPagerChange(currentPage);
                }
            }
        }
        isScrolling = false;
    }

    //设置回调接口
    public interface OnPagerChangeListener {
        void onPagerChange(int currentPager);
    }

    public void setOnPagerChangeListener(OnPagerChangeListener listener) {
        this.mListener = listener;
    }


}
