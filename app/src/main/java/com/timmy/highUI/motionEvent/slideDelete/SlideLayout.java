package com.timmy.highUI.motionEvent.slideDelete;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.timmy.library.util.Logger;

/**
 * Created by admin on 2017/2/22.
 * 滑动控件：
 * 1.onSizeChange方法获取滑动的左右子控件
 * 2.onTouchEvent 方法处理
 * down事件获取点击位置
 * move事件处理滑动效果（边界值处理）
 * up事件处理最后的展示效果（是否大于阈值）
 * 3.使用Scroll来进行滑动处理
 */
public class SlideLayout extends LinearLayout {

    private String TAG = this.getClass().getSimpleName();
    private View leftChild;
    private View rightChild;
    //    private int rightWidth;
    private Scroller scroller;
    private float startX;
    private float startY;

    public SlideLayout(Context context) {
        this(context, null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        scroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //可在该方法中重新设置左右滑动控件的宽高
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        leftChild = getChildAt(0);
        rightChild = getChildAt(1);
//        rightWidth = rightChild.getWidth();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();

                //消费down事件->才能接收到move和up后续事件
                return true;
            case MotionEvent.ACTION_MOVE:
                //x ,y 方向的偏移量
                float offsetX = event.getX() - startX;
                float offsetY = event.getY() - startY;

                //判断是否达到滑动删除资格
                if (Math.abs(offsetX) - Math.abs(offsetY) > ViewConfiguration.getTouchSlop()) {

                    int scrollX = getScrollX();//获取控件滑动的距离
                    Logger.d(TAG, "scrollX:" + scrollX + ",offsetX:" + offsetX + ",rightWidth:" + rightChild.getWidth());
                    //边界值处理
                    if (scrollX + (-offsetX) > rightChild.getWidth() || scrollX + (-offsetX) < 0) {
                        return true;
                    }
                    //滑动
                    this.scrollBy((int) -offsetX, 0);
                    startX = event.getX();
                    startY = event.getY();

                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //up事件，根据滑动的距离决定最后弹回的位置
                if (getScrollX() > rightChild.getWidth() / 2) {//滑动超过删除控件宽度一半，最后位置显示全部的删除控件
                    this.scrollTo(rightChild.getWidth(), 0);
                } else {
                    this.scrollTo(0, 0);
                }

                break;
        }
        return super.onTouchEvent(event);
    }


}
