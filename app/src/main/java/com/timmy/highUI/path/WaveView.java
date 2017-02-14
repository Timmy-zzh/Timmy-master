package com.timmy.highUI.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by admin on 2017/2/14.
 * 水波纹处理
 * 1.使用二阶贝塞尔曲线画出一个波峰波谷，->循环
 * 2.高度增加
 * 3.开启无限循环动画来改变移动位置
 */
public class WaveView extends View {

    private Paint paint;
    private Path path;
    private int waveWidth = 400;//一个波纹的宽度
    private int halfWaveWidth = waveWidth/2;//半个波纹的宽度
    private int dx;
    private int deafultHeight = 500;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
//        //画一个波峰波谷
//        path.moveTo(0,500);
//        //quadTo二阶贝塞尔方法的参数为确定值
//        path.quadTo(halfWaveWidth/2,300,halfWaveWidth,500);
//        path.quadTo(halfWaveWidth+halfWaveWidth/2,700,waveWidth,500);

        //高度往上浮动
        if (deafultHeight >-200){
            deafultHeight-= 5;
        }else{
            deafultHeight= 800;
        }

        path.moveTo(-dx,deafultHeight);
        for (int i = -waveWidth;i<getWidth()+waveWidth;i+=waveWidth){
            //rQuadTo使用的是相对上一个坐标的位置
            path.rQuadTo(halfWaveWidth/2,-150,halfWaveWidth,0);
            path.rQuadTo(halfWaveWidth/2,150,halfWaveWidth,0);
        }

        path.lineTo(getWidth(),getHeight());
        path.lineTo(0,getHeight());
        path.close();

        canvas.drawPath(path,paint);
    }

    //开启波纹动画
    public void startWaveAnimate() {
        ValueAnimator animator = ValueAnimator.ofInt(0,waveWidth)
                .setDuration(3000);
//        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = waveWidth - (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }
}
