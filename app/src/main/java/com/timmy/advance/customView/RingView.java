package com.timmy.advance.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.timmy.R;
import com.timmy.library.util.Logger;

/**
 * 圆环自定义view--有两个颜色,交替变换
 * 步骤:
 * 1.自定义属性
 * 2.onMeasure
 * 3.onDraw
 */
public class RingView extends View {

    private final java.lang.String TAG = this.getClass().getSimpleName();
    private int circleWidth;
    private int firstColor;
    private int secondColor;
    private int speed;
    private Paint mPaint;
    private int mProgress;
    private boolean inTurn = false;

    public RingView(Context context) {
        this(context, null);
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RingView);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.RingView_circleWidth:
                    //默认像素
                    int defaultWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20,
                            getResources().getDisplayMetrics());
                    //圆环的宽度
                    circleWidth = ta.getDimensionPixelOffset(attr, defaultWidth);
                    break;
                case R.styleable.RingView_firstColor:
                    firstColor = ta.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.RingView_secondColor:
                    secondColor = ta.getColor(attr, Color.GRAY);
                    break;
                case R.styleable.RingView_speed:
                    speed = ta.getInt(attr, 30);
                    break;
            }
        }
        ta.recycle();

        init();
    }

    /**
     * 初始化画笔,开启子线程改变圆环的弧度
     */
    private void init() {
        mPaint = new Paint();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        if (inTurn) {//交替切换控制器
                            inTurn = !inTurn;
                        }else{
                            inTurn = !inTurn;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //设置画圆的区域
        int center = getWidth() / 2;//圆心x的坐标
        Logger.d(TAG, "--center--" + center);
        //圆环的半径
        int radius = center - circleWidth / 2;
        //设置画笔的宽度,也就是圆环的宽度
        mPaint.setStrokeWidth(circleWidth);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //设置空心
        mPaint.setStyle(Paint.Style.STROKE);
        //设置圆弧的区域
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);

        if (!inTurn) {
            mPaint.setColor(firstColor);
            //画圆环
            /**
             * (center,center)为圆心
             */
            canvas.drawCircle(center, center, radius, mPaint);
            //画弧度
            mPaint.setColor(secondColor);
            canvas.drawArc(rectF, -90, mProgress, false, mPaint);
        } else {
            mPaint.setColor(secondColor);
            //画圆环
            /**
             * (center,center)为圆心
             */
            canvas.drawCircle(center, center, radius, mPaint);
            //画弧度
            mPaint.setColor(firstColor);
            canvas.drawArc(rectF, -90, mProgress, false, mPaint);
        }

    }
}
