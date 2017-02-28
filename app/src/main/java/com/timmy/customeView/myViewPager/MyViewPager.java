package com.timmy.customeView.myViewPager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.timmy.library.util.Logger;

/**
 * Created by admin on 2017/2/28.
 * 自定义ViewGroup：
 * 1.获取到子控件，使用addView()添加/xml文件添加
 * 2.onLayout()方法摆放子控件
 * 3.让图片滑动起来-使用手势控制器
 * 4.滑动事件处理
 * 5.边界处理
 * 6.增加ViewPager指示器：在onTouchEvent方法中的MOTION_UP事件中回调接口
 */
public class MyViewPager extends ViewGroup {

    private String TAG = "MyViewPager";
    private GestureDetector gestureDetector;
    private int downX;
    private int touchSlop;
    private int currPage = 0;//标记当前第几页
    private Scroller mScroller;
    private OnItemPagerSelectedListener mListener;
    private int intercepDownX;
    private int intercepDownY;
    private int lastMoveX;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        touchSlop = ViewConfiguration.getTouchSlop();
        mScroller = new Scroller(getContext());

        //让该ViewPager滑动起来
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Logger.d(TAG, "onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Logger.d(TAG, "onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Logger.d(TAG, "onSingleTapUp");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Logger.d(TAG, "onScroll,distanceX:" + distanceX);
                scrollBy((int) distanceX, 0);//让该ViewPager滑动起来
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Logger.d(TAG, "onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Logger.d(TAG, "onFling");
                return false;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Logger.d(TAG, "l:" + l + ",t:" + t + ",r:" + r + ",b:" + b + ",getWidth:" + getWidth() + ",getHeight:" + getHeight());
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }
    }

    /**
     * 事件拦截：左右滑动则拦截，给ViewPager使用
     * 上下滑动则不拦截，事件交给子view使用
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepDownX = (int) ev.getX();
                intercepDownY = (int) ev.getY();
                lastMoveX = (int) ev.getX();
                downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int intercepOffsetX = (int) (ev.getX() - intercepDownX);
                int intercepOffsetY = (int) (ev.getY() - intercepDownY);
                if (Math.abs(intercepOffsetX) - Math.abs(intercepOffsetY) > 0) {
                    return true;//拦截
                }
                break;
            default:
                break;
        }
        return false;//不拦截
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Logger.d(TAG, "MyViewPager--onTouchEvent:");
        //交给手势处理器处理
//        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                lastMoveX = (int) event.getX();
                Logger.d(TAG, "ACTION_DOWN--downX:" + downX + ",lastMoveX:" + lastMoveX);
                break;
            case MotionEvent.ACTION_MOVE: {//move事件交给手势处理器处理
                //边界值处理
                int offsetX = (int) (event.getX() - lastMoveX);
                lastMoveX = (int) event.getX();
                int childCount = getChildCount();
                if (currPage == 0 && offsetX > 0) {//第一页，还往右滑动，
                    scrollTo(0, 0);
                } else if (currPage == childCount - 1 && offsetX < 0) {
                    scrollTo((childCount - 1) * getWidth(), 0);
                } else {
                    scrollBy(-offsetX, 0);
                }
                Logger.d(TAG, "ACTION_MOVE--offsetX:" + offsetX + ",lastMoveX:" + lastMoveX + ",currPage:" + currPage);
                break;
            }
            case MotionEvent.ACTION_UP:
                /**
                 * 中间pager处理
                 * 在up事件中处理边界问题,根据滑动距离来判断当前滑动的是第几个view,超过阈值则进入下一页
                 */
                int upX = (int) (event.getX() - downX);
                int childCount = getChildCount();
                if (Math.abs(upX) > getWidth() / 3) {//滑动超过阈值，进入下一页
                    if (upX > 0 && currPage > 0) {
                        //向右滑动
                        currPage--;
                    } else if (upX < 0 && currPage < childCount - 1) {
                        currPage++;
                    }
                }
                setCurrentItem(currPage);
                break;
        }
//        return super.onTouchEvent(event);
        return true;//viewPager消费
    }

    public void setCurrentItem(int pager) {
        this.currPage = pager;
        int scrollX = getScrollX();//滑动距离
        Logger.d(TAG, "upX:" + ",currPage:" + currPage + ",scrollX:" + scrollX);
        //scrollTo 绝对滑动到某个确定的位置-用户体验不好，太僵硬
//                scrollTo(currPage * getWidth(), 0);
        //使用Scroller类进行优化
        mScroller.startScroll(scrollX, 0, currPage * getWidth() - scrollX, 0);
        invalidate();//调用invalidate()方法，会执行computeScroll()方法

        if (mListener != null) {
            mListener.onItemPagerSelected(currPage);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {//正在执行滑动
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
    }


    public interface OnItemPagerSelectedListener {
        void onItemPagerSelected(int position);
    }

    public void setOnItemPagerSelectedListener(OnItemPagerSelectedListener listener) {
        this.mListener = listener;
    }


}
