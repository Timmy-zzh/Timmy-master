package com.timmy.highUI.stretchList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

import com.timmy.R;

/**
 * 可伸缩头部的listview:实现效果为
 * 1.listview向下拉的时候顶部的美女图片实现放大效果
 * 2.而往上推的时候，图片恢复回去，
 * 3.listview向下拉了一段距离后，松开手的时候图片弹回到原始状态
 * Created by timmy1 on 16/11/25.
 * 总结：
 * 1.listview拉伸过度调用的方法overScrollBy中去处理图片的高度
 * 2.往上滑动时，图片高度恢复，恢复的高度是父容器gettop的高度
 * 3.松开手时touch事件处理，并自定义动画处理
 */
public class StretchListView extends ListView {

    private final String TAG = this.getClass().getSimpleName();
    private ImageView mImageView;
    private int mIvHeight = 0;

    public StretchListView(Context context) {
        this(context, null);
    }

    public StretchListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StretchListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setStretchImageView(ImageView stretchIv) {
        this.mImageView = stretchIv;
        this.mIvHeight = this.getResources().getDimensionPixelSize(R.dimen.default_stretch_height);
    }

    /**
     * 滑动过度调用的方法
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        Log.d(TAG,"overScrollBy-deltaY:"+deltaY);//主要监听y方向滑动距离
        if (deltaY < 0) {//表示向下滑动过度－》头部的美女图片高度改变->并进行界面绘制
            ViewGroup.LayoutParams params = mImageView.getLayoutParams();
            params.height = params.height - deltaY;
            mImageView.setLayoutParams(params);
            mImageView.requestLayout();
        } else {//处理上拉过度问题，并且adapter条目不超过一屏时
            if (mImageView.getHeight() > mIvHeight) {
                ViewGroup.LayoutParams params = mImageView.getLayoutParams();
                params.height = params.height - deltaY;
                mImageView.setLayoutParams(params);
                mImageView.requestLayout();
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


    /**
     * listview滑动监听，当listview下拉超过图片的默认高度，往上拉的时候，实现图片恢复到原来位置
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        View parent = (View) mImageView.getParent();
        Log.d(TAG, "onScrollChanged-t:" + t + "--oldT:" + oldt );//主要监听y方向滑动距离
        //当滑动到图片在最顶部，并且继续往上滑动时－》图片要恢复到原始高度－图片要恢复的高度就是parent的gettop高度
        if (parent.getTop() < 0 && mImageView.getHeight() > mIvHeight) {
            mImageView.getLayoutParams().height = mImageView.getHeight() + parent.getTop();
            //父容器也需要重新布局
            parent.layout(0, 0, parent.getWidth(), parent.getHeight() + parent.getTop());
            mImageView.requestLayout();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {//手指松开时，图片弹回到原来位置－动画处理
            if (mImageView.getHeight() > mIvHeight) {
                ResetAnimation animation = new ResetAnimation(mImageView, mIvHeight);
                animation.setDuration(300);
                mImageView.startAnimation(animation);
            }
        }
        return super.onTouchEvent(ev);
    }

    private class ResetAnimation extends Animation {

        private  int origenHeight;//图片原始高度
        private  int extenHeight;//高度差
        private ImageView imageView;
        private int targetHeight;

        public ResetAnimation(ImageView mImageView, int mIvHeight) {
            this.imageView = mImageView;
            this.targetHeight = mIvHeight;
            this.origenHeight = mImageView.getHeight();
            this.extenHeight = origenHeight - targetHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            //实现效果，
            imageView.getLayoutParams().height = (int) (origenHeight - extenHeight*interpolatedTime);
            imageView.requestLayout();
        }
    }
}
