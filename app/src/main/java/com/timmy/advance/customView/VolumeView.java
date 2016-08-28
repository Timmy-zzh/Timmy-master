package com.timmy.advance.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.R;

/**
 * 音量控件:效果是随着滑动时间音量大小随着改变
 * 处理步骤:
 * 1.获取自定义属性
 * 2.onDraw绘制,绘制原始音量背景--绘制圆弧背景
 * 3.绘制中间的音量图片
 */
public class VolumeView extends View {

    private Paint mPaint;
    private int firstColor;
    private int secondColor;
    private int intervalDegree;//音量之间的间隔度数
    private int pointCount;//音量的个数
    private int ringWidth;//圆环的宽度
    private Bitmap mBitmap;//音量中的图标
    private Rect mRect;//用于绘制图标区域
    private int currentCount = 0;
    private float downY;
    private float upY;

    public VolumeView(Context context) {
        this(context, null);
    }

    public VolumeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VolumeView);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.VolumeView_firstColor:
                    firstColor = ta.getColor(attr, Color.GRAY);
                    break;
                case R.styleable.VolumeView_secondColor:
                    secondColor = ta.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.VolumeView_interval:
                    intervalDegree = ta.getInt(attr, 20);
                    break;
                case R.styleable.VolumeView_pointCount:
                    pointCount = ta.getInt(attr, 10);
                    break;
                case R.styleable.VolumeView_ringWidth:
                    int defaultWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 10, getResources().getDisplayMetrics());
                    ringWidth = ta.getDimensionPixelSize(attr, defaultWidth);
                    break;
                case R.styleable.VolumeView_image:
                    mBitmap = BitmapFactory.decodeResource(getResources(), ta.getResourceId(attr, 0));
                    break;
            }
        }

        ta.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //抗锯齿
        mPaint.setAntiAlias(true);
        //设置画笔宽度
        mPaint.setStrokeWidth(ringWidth);
        //设置圆环断电形式为源头
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置空心
        mPaint.setStyle(Paint.Style.STROKE);

        //绘制音量背景和音量
        int centre = getWidth() / 2;//x方向的圆心坐标

        int radius = centre - ringWidth / 2;//圆环半径

        //绘制音量块
        drawPiect(canvas, centre, radius);

        //绘制音量中间的图标--需要根据外面圆环求得内切正方形的区域
        //求出内圆的半径
        int innerRadius = radius - ringWidth / 2;
        //求出正方形边的一半
        int halfSquareWidth = (int) (Math.sqrt(2) / 2 * innerRadius);

        //设置图标的绘制区域
        mRect.left = centre - halfSquareWidth;
        mRect.top = centre - halfSquareWidth;
        mRect.right = centre + halfSquareWidth;
        mRect.bottom = centre + halfSquareWidth;
        //如果图标比较小,就放到正中心
        if (mBitmap.getWidth() < halfSquareWidth * 2) {
            mRect.left = centre - mBitmap.getWidth() / 2;
            mRect.top = centre - mBitmap.getHeight() / 2;
            mRect.right = centre + mBitmap.getWidth() / 2;
            mRect.bottom = centre + mBitmap.getHeight() / 2;
        }

        canvas.drawBitmap(mBitmap, null, mRect, mPaint);
    }

    private void drawPiect(Canvas canvas, int centre, int radius) {
        //求每个音量的度数
        float itemDegree = (360 * 1.0f - pointCount * intervalDegree) / pointCount;
        //设置绘制圆弧的区域
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        //绘制背景音量
        mPaint.setColor(firstColor);
        for (int i = 0; i < pointCount; i++) {
            canvas.drawArc(oval, i * (itemDegree + intervalDegree), itemDegree, false, mPaint);
        }
        //根据当前音量数绘制
        mPaint.setColor(secondColor);
        for (int i = 0; i < currentCount; i++) {
            canvas.drawArc(oval, i * (itemDegree + intervalDegree), itemDegree, false, mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                upY = event.getY();
                if (upY > downY) {
                    downVolume();
                } else {
                    upVolume();
                }
                break;
        }
        return true;
    }

    private void upVolume() {
        currentCount++;
        postInvalidate();
    }

    //降低音量
    private void downVolume() {
        currentCount--;
        postInvalidate();
    }
}
