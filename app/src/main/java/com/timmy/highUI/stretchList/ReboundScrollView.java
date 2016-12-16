package com.timmy.highUI.stretchList;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.timmy.library.util.Logger;

/**
 * 反弹效果的ScrollView,内部嵌套ListView当往下拉超过一半时,接口回调
 * 主要是手势滑动的时候的逻辑处理
 * 1.首先获取内部包含的子控件ListView
 * 2.手势滑动事件
 */
public class ReboundScrollView extends ScrollView {

    private final java.lang.String TAG = this.getClass().getSimpleName();
    private View mView;
    private int lastY;
    private boolean isFirst = true;
    private Rect mRect = new Rect();
    private OnSlideHalfListener mListener;
    private boolean isCalling = false;//标记是否正在回调

    public ReboundScrollView(Context context) {
        super(context);
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.d(TAG, "--ReboundScrollView");
    }

    public ReboundScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 控件布局完成时调用->获取内部ListView实例
     */
    @Override
    protected void onFinishInflate() {
        Logger.d(TAG, "--onFinishInflate");
        if (getChildCount() > 0)
            mView = getChildAt(0);
        super.onFinishInflate();
    }

    /**
     * ScrollView往下拉,需要实现的效果是内部的ListView距离底部有一块空白的地方
     * 需要手势下拉多少,ListView距离顶部就有多少距离
     * 当滑动超过屏幕一半时,回调接口函数
     * 当滑动未超过屏幕一半时,反弹回去
     * MOVE_UP的时候,反弹回去
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mView != null) {
            int touchY = (int) ev.getY();
            Logger.d(TAG, "--onTouchEvent--touchY:" + touchY);
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    //滑动的距离
                    int dy = touchY - lastY;
                    if (isFirst) {//第一次滑动
                        dy = 0;
                        isFirst = false;
                    }
                    Logger.d(TAG, "--onTouchEvent--touchY:" + touchY + "--lastY:" + lastY + "--dy:" + dy);
                    lastY = touchY;

                    if (isCanMove()) {//dy=0的时候可以滑动
                        if (mRect.isEmpty()) {
                            //记录ListView原始位置,用于返回回去
                            mRect.set(mView.getLeft(), mView.getTop(),
                                    mView.getRight(), mView.getBottom());

                        }
                        //ListView从新布局
                        mView.layout(mView.getLeft(), mView.getTop() + dy * 2 / 3,
                                mView.getRight(), mView.getBottom() + dy * 2 / 3);
                        Logger.d(TAG, "--left:" + mView.getLeft() + "" + "--top:" + mView.getTop() +
                                "--right:" + mView.getRight() + "--bottom:" + mView.getBottom());
                        //当滑动距离超过一半时,回调接口函数
                        if (isExceedHalf(dy) && !isCalling && mListener != null) {
                            isCalling = true;
                            resetPostion();
                            mListener.onSlideHalf();
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    resetPostion();
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     *
     * @return
     */
    private boolean isCanMove() {
        int offset = mView.getMeasuredHeight() - getHeight();
        Logger.d(TAG, "--mView--getMeasuredHeight:" + mView.getMeasuredHeight() + "--getHeight:" + mView.getHeight());
        int scrollY = getScrollY();//滑动距离
        Logger.d(TAG, "--ScrollView--getHeight:" + getHeight() + "--滑动距离getScrollY:" + scrollY);

        if (scrollY == 0 || scrollY == offset)
            return true;
        return false;
    }

    /**
     * 反弹--ListView恢复到原始位置
     */
    private void resetPostion() {
        Animation animation = new TranslateAnimation(0f, 0f, mView.getTop(), mRect.top);
        animation.setDuration(300);
        animation.setFillAfter(true);
        mView.startAnimation(animation);
        mView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
        isCalling = false;
        isFirst = true;
    }

    private boolean isExceedHalf(int dy) {
        if (dy > 0 && mView.getTop() > getHeight() / 2)
            return true;
        return false;
    }

    public interface OnSlideHalfListener {
        void onSlideHalf();
    }

    public void setOnSlideHalfListener(OnSlideHalfListener listener) {
        this.mListener = listener;
    }
}
