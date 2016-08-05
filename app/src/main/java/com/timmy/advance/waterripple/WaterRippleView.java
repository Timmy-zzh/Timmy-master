package com.timmy.advance.waterripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.util.Logger;

/**
 * 自定义View实现水波纹效果
 * 一:实现点击就出现水波纹效果--界面上只有一小圆环
 * 实现步骤:初始化画笔->测量(宽高为屏幕宽高,使用默认测量方法)->布局(用不上)-->
 * 绘制(在画布上绘制水波纹)->点击事件处理(获取点击的位置,在该位置绘制波纹效果,并且随着时间波纹变大,透明度变淡)
 *
 * 二:界面上有多个圆环
 *
 */
public class WaterRippleView extends View {
    private final String TAG = this.getClass().getSimpleName();
    private float width;//设置线宽
    private int alpha;
    private int xDown, yDown;//手势点击时的位置
    private float radius;//画笔绘制时使用到的半径
    private Paint mPaint;
    private int MAX_ALPHA = 255;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                //更新画笔设置--透明度,半径,宽度->重新绘制->透明度未减少到0时,再次发送消息
                flushState();
                invalidate();

                if (alpha != 0) {
                    handler.sendEmptyMessageDelayed(0, 50);
                }
            }
        }
    };

    private void flushState() {
        radius += 5;
        alpha -= 10;
        if (alpha < 0)
            alpha = 0;

        width = radius / 4;
        mPaint.setAlpha(alpha);
        mPaint.setStrokeWidth(width);
    }


    public WaterRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        alpha = 0;
        radius = 0;
        initPaint();
    }

    /**
     * 初始化画笔-->设置画笔颜色和在画布上画圆环
     * paint.setColor(Color.RED);                          //设置画笔颜色
     * paint.setStyle(Style.STROKE);                       //设置画笔为空心
     * paint.setStrokeWidth((float) 10.0);                 //设置线宽
     * canvas.drawColor(Color.WHITE);
     * canvas.drawLine(50, 50, 450, 50, paint);            //绘制直线
     * canvas.drawRect(100, 100, 200, 600, paint);         //绘制矩形
     * canvas.drawRect(300, 100, 400, 600, paint);         //绘制矩形
     * canvas.drawCircle(xDown, yDown, radius, mPaint);     //绘制圆形
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //设置圆环宽度
        mPaint.setStrokeWidth(width);
        //设置画笔空心绘制
        mPaint.setStyle(Paint.Style.STROKE);
        //设置画笔透明度
        mPaint.setAlpha(alpha);
        Logger.d(TAG, "画笔透明度-" + mPaint.getAlpha());
        //设置画笔颜色
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //绘制,在画布上绘制圆环
        canvas.drawCircle(xDown, yDown, radius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://手指按下时,需要设置画笔透明度,和半径-->通过Handler通信
                alpha = MAX_ALPHA;
                radius = 0;
                width = radius / 4;

                xDown = (int) event.getX();
                yDown = (int) event.getY();

                handler.sendEmptyMessage(0);
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
