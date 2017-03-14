package com.timmy.customeView.myPhotoView;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ConfigurationHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.timmy.library.util.Logger;

/**
 * Created by admin on 2017/3/14.
 * 高仿今日头条图片功能:
 * 1.单击放大缩小:onTouchEvent()处理--scale
 * 2.拖拽处理:图片随着手指滑动而移动 MOVE--layout
 * 3.多点缩放:
 */
public class MyPhotoView extends android.support.v7.widget.AppCompatImageView {

    private String TAG = this.getClass().getSimpleName();
    private Matrix matrix = new Matrix();
    private float scaleRatio = 0.5f;//缩放率
    private boolean isScale = false;//缩放状态
    private boolean isDrag = false;//拖拽状态
    private int mCenterX;
    private int mCenterY;
    private int downX;
    private int downY;
    private int lastX;
    private int lastY;
    private int touchSlop;
    private boolean doubleScale;//双手缩放
    private float beforeDistance;

    public MyPhotoView(Context context) {
        this(context,null);
    }

    public MyPhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyPhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        touchSlop = viewConfiguration.getScaledTouchSlop();
        Logger.d(TAG,"touchSlop:"+touchSlop);
//        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int width = getWidth();
        int height = getHeight();
        mCenterX = width/2;
        mCenterY = height/2;
        Logger.d(TAG,"left:"+left+",top:"+top+",right:"+right+",bottom:"+bottom+",width:"+width+",height:"+height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.d(TAG, "onTouchEvent:" + event.getAction());
        //处理单点,多点触摸  event.getAction() & MotionEvent.ACTION_MASK
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                //有一个手指已经在屏幕上,下一个手指也触摸到屏幕时会调用
                Logger.d(TAG, "point_down:" + event.getPointerCount());
                if (event.getPointerCount() == 2){
                    //两个手指,获取两个手指的距离->然后对图片进行缩放
                    doubleScale = true;
                    beforeDistance = getPointDistance(event);
                }

                break;
            case MotionEvent.ACTION_DOWN:
                matrix.reset();
                doubleScale = false;
                isScale = !isScale;
                downX = lastX = (int) event.getX();
                downY = lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //求出偏移差值
                int dx = (int) (event.getX() - lastX);
                int dy = (int) (event.getY() - lastY);
                if (doubleScale){
                    float afterDistance = getPointDistance(event);
                    float pointScale = afterDistance/beforeDistance * 1.0f;

                    matrix.postScale(pointScale, pointScale, mCenterX, mCenterY);
                    setImageMatrix(matrix);
                    beforeDistance = getPointDistance(event);
                    return true;
                }

                if (Math.abs(dx) > touchSlop &&Math.abs(dy)>touchSlop) {
                    isDrag = true;
                    int left = this.getLeft()+dx;
                    int top = this.getTop() +dy;
                    int right = this.getRight()+dx;
                    int bottom = this.getBottom() +dy;

                    this.layout(left,top,right,bottom);

                    lastX = (int) event.getX();
                    lastY = (int) event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isDrag) {
                    //缩放比例
                    float sx = isScale ? 2 : 1;
                    float sy = isScale ? 2 : 1;
                    //缩放点位置
                    float px = downX;
                    float py = downY;
                    Logger.d(TAG, "sx:" + sx + ",sy:" + sy + ",px:" + px + ",py:" + py);
//                matrix.postScale(1/scaleRatio, 1/scaleRatio, mCenterX, mCenterY);
                    matrix.postScale(sx, sy, px, py);
                    setImageMatrix(matrix);
                }
                isDrag = false;
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }

    private float getPointDistance(MotionEvent event) {
        float dx = event.getX(0) - event.getX(1);
        float dy = event.getY(0) - event.getX(1);
        return (float) Math.sqrt(dx*dx+dy+dy);
    }
}
