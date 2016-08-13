package com.timmy.advance.customViewGroup;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.timmy.library.util.Logger;

/**
 * 自定义ViewGroup实现ViewPager效果
 * 需要处理的有:1.onLayout方法,设置子View的位置
 * 2.触摸事件,当手指左右滑动时,子View可以左右滑动--使用Ges来实现
 * 3.onTouchEvent()方法处理,防止滑动超出边界
 * 4.滑动距离控制,
 */
public class MyViewPager extends ViewGroup {

    private final String TAG = this.getClass().getSimpleName();
    private final Context mContext;
    private final GestureDetector gestureDetector;
    private float downX;
    private int currentPager = 0;//标记当前View在那个位置
    private final DistanceProvider distanceProvider;


    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        distanceProvider = new DistanceProvider(context);
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            /**
             * 手指滑动时调用,所有在onTouchEvetn的ACTION_MOVE就不需要处理滑动逻辑
             * @param e1
             * @param e2
             * @param distanceX
             * @param distanceY
             * @return
             */
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX, 0);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    /**
     * 指定组件位置,给ViewGroup中的子View确定位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        int width = getWidth();
        int height = getHeight();
        Logger.d(TAG, "--onLatyou--childCount--" + childCount + "--width--" + width + "--height--" + height);
        for (int i = 0; i < childCount; i++) {
            //为每个子View设置位置
            View childView = getChildAt(i);
            childView.layout(i * width, 0, (i + 1) * width, height);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                Logger.d(TAG, "--action_down--downX--" + downX);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                //手指抬起时,超过半屏距离的处理
                float offsetX = event.getX() - downX;//x方向滑动距离
                Logger.d(TAG, "--action_up--getx--" + event.getX());

                Logger.d(TAG, "--offset--" + offsetX);
                boolean isHalf = isAchieveHalf(offsetX);
                if (offsetX < 0 && isHalf) {
                    //往左滑动,超过半屏,到达下一个View
                    ++currentPager;
                    if (currentPager > getChildCount() - 1)
                        currentPager = getChildCount() - 1;
                }

                if (offsetX > 0 && isHalf) {
                    //往右滑动,超过半屏,达到上一个view
                    --currentPager;
                    if (currentPager < 0)
                        currentPager = 0;
                }
                Logger.d(TAG, "--currentPager--" + currentPager);

//                scrollTo(currentPager * getWidth(), 0);
// 该方法的使用的效果在两个页面之间滑动时,会快速的跳转,效果不好,我们希望可以缓慢的滑动,有个过渡
                moveSooth();
                break;
        }
        return true;
    }

    /**
     * 匀速的滑动
     */
    private void moveSooth() {
        int scrollX = getScrollX();
        Logger.d(TAG, "--当前滑动距离scrollX--" + scrollX);
        int distance = currentPager * getWidth() - scrollX;
        Logger.d(TAG, "--滑动--distance--" + distance);

        /**
         * 要滑动这段距离,需要分几个时间段来进行,计算一遍后,再重新绘制
         */
        distanceProvider.startScroll(getScrollX(), 0, distance, 0);
        invalidate();//该方法会导致onDraw()和computerScroll()方法的调用
    }

    //判断滑动距离是否超过半屏
    private boolean isAchieveHalf(float offsetX) {
        return Math.abs(offsetX) > getWidth() / 2;
    }

    /**
     * 计算滑动偏移量,
     */
    @Override
    public void computeScroll() {
//        super.computeScroll();
        if (!distanceProvider.computeScrollOffset()) {
            //未完成,计算还剩下多少距离,再重新绘制
            int currentX = (int) distanceProvider.getCurrentX();
            scrollTo(currentX, 0);
            invalidate();
        }
    }


}
