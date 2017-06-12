package com.timmy.highUI.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.timmy.library.util.Logger;

/**
 * Path基本用法:
 *
 */
public class PathMeasureUseView extends View {

    private Paint mPaint;
    private PathMeasure pathMeasure;
    private Path path;
    private float[] pos = new float[2];

    public PathMeasureUseView(Context context) {
        this(context, null);
    }

    public PathMeasureUseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureUseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    //初始化画笔
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        RectF rect = new RectF(300,300,600,600);
        path.addRect(rect, Path.Direction.CW);
        pathMeasure = new PathMeasure();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.reset();

        //设置画笔颜色
        mPaint.setColor(Color.BLACK);
        //设置画笔的宽度
        mPaint.setStrokeWidth(3);
        //设置画笔样式
        mPaint.setStyle(Paint.Style.STROKE);//描边

        pathMeasure.setPath(path,false);
        Logger.d("Length",pathMeasure.getLength()+"");

        //绘制举行
        canvas.drawPath(path,mPaint);

        //绘制根据矩形轨迹运动的小圆球
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(pos[0],pos[1],12f,mPaint);

    }

    /**
     * 开启动画
     */
    public void startMove(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,pathMeasure.getLength());
        valueAnimator.setDuration(3*1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取到当前距离path路径起始点距离值
                float distance = (float) animation.getAnimatedValue();
                pathMeasure.getPosTan(distance,pos,null);
                postInvalidate();
            }
        });
        valueAnimator.start();
    }
}
