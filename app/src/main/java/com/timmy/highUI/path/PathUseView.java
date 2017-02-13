package com.timmy.highUI.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2017/2/13.
 */

public class PathUseView extends View {

    private Paint mPaint;

    public PathUseView(Context context) {
        this(context, null);
    }

    public PathUseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathUseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    //初始化画笔
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.reset();

        //设置画笔颜色
        mPaint.setColor(Color.RED);
        //设置画笔的宽度
        mPaint.setStrokeWidth(10);
        //设置画笔样式
        mPaint.setStyle(Paint.Style.STROKE);//描边
//        mPaint.setStyle(Paint.Style.FILL);//填充
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //画圆
//        canvas.drawCircle(200,200,100,mPaint);

        //线帽
//        mPaint.setStrokeCap(Paint.Cap.BUTT);//没有
//        mPaint.setStrokeCap(Paint.Cap.ROUND);//圆形
//        mPaint.setStrokeCap(Paint.Cap.SQUARE);//方形

        //线条交接处处理
//        mPaint.setStrokeJoin(Paint.Join.MITER);//锐角
//        mPaint.setStrokeJoin(Paint.Join.ROUND);//圆弧
//        mPaint.setStrokeJoin(Paint.Join.BEVEL);//直线

//        Path path = new Path();
//        画线
//        path.moveTo(100,100);
//        path.lineTo(200, 200);
//        path.lineTo(100,300);
//        mPaint.setStrokeJoin(Paint.Join.MITER);
//        canvas.drawPath(path, mPaint);
//
//        path.moveTo(100,400);
//        path.lineTo(200, 500);
//        path.lineTo(100,600);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
//        canvas.drawPath(path, mPaint);
//
//        path.moveTo(100,700);
//        path.lineTo(200, 800);
//        path.lineTo(100,900);
//        mPaint.setStrokeJoin(Paint.Join.BEVEL);
//        canvas.drawPath(path, mPaint);

        //二阶贝塞尔曲线
//        Path path = new Path();
//        path.moveTo(0,300);
//        path.quadTo(150,0,300,300);
//        canvas.drawPath(path,mPaint);

        //三阶贝塞尔曲线
//        Path path = new Path();
//        path.moveTo(300, 0);
//        path.cubicTo(0, 150, 300, 450, 0, 600);
//        canvas.drawPath(path, mPaint);

        //绘制圆弧
//        Path path = new Path();
//        RectF oval = new RectF(0, 0, 700, 300);
//        path.arcTo(oval, 0, 90);
////        path.arcTo(oval,0,90,true);//第四个参数为是否和上一个path路径相连，false表示相连
//        canvas.drawPath(path, mPaint);

        //在一个矩形区域中绘制各种图形
//        Path path = new Path();
//        RectF oval = new RectF(0,0,300,300);
//        //添加圆弧
//        path.addArc(oval,0,180);
//        //添加圆
//        path.addCircle(500,150,100, Path.Direction.CCW);
//        //添加矩形
//        RectF rectOval = new RectF(0,320,300,600);
//        path.addRect(rectOval, Path.Direction.CCW);
//        //添加圆角矩形
//        RectF roundRect = new RectF(320,300,700,600);
//        path.addRoundRect(roundRect,30,30, Path.Direction.CCW);
//        canvas.drawPath(path,mPaint);

        //两个path路径的交集处理
        Path path1 = new Path();
        Path path2 = new Path();
        path1.addCircle(150,150,100, Path.Direction.CCW);

        RectF rectOval = new RectF(150,150,250,250);
        path2.addRect(rectOval, Path.Direction.CCW);
        //其他交集处理
//        path1.op(path2, Path.Op.DIFFERENCE);

        canvas.drawPath(path1,mPaint);

    }
}
