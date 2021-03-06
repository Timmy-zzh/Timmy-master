package com.timmy.customeView.clockView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.timmy.R;
import com.timmy.library.util.Logger;

import java.util.Calendar;

/**
 * 自定义钟表控件
 * 需要在画布上绘制钟表的表盘
 * 小时文本
 * 时针，分针，秒针
 * 并且每隔一秒更新
 * 思路：1.构造函数中获取基本属性
 * 2.复写onDraw（）方法
 */
public class ClockView extends View {
    private String TAG = "ClockView";

    //绘制表盘画笔
    private Paint clockPaint;
    //绘制表盘数字
    private Paint numPaint;
    //绘制表盘中心
    private Paint centerPaint;
    //时针-分针-秒针
    private Paint hourPaint, minutePaint, secondPaint;
    //时针-分针-秒针的颜色
    private int hourColor, minuteColor, secondColor;
    //时针-分针-秒针的宽度
    private int hourWidth, minuteWidth, secondWidth;
    //View的宽高
    private int mWidth, mHeight;
    //日历类,获取当前时间
    private Calendar calendar;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        //通过这种方式获取到自定义控件的属性信息
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
//        int color = typedArray.getColor(R.styleable.CustomImageView_imageType, 0);
//        typedArray.recycle();
        //设置画笔参数和基本属性
        //钟表的默认宽高
        mWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 256f, getResources().getDisplayMetrics());
        mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 256f, getResources().getDisplayMetrics());
        //初始化表针颜色
        hourColor = Color.RED;
        minuteColor = Color.GREEN;
        secondColor = Color.BLUE;
        //初始化表针宽度
        hourWidth = 8;
        minuteWidth = 6;
        secondWidth = 4;

        //设置表盘画笔样式
        clockPaint = new Paint();
        //描边
        clockPaint.setStyle(Paint.Style.STROKE);
        //抗锯齿
        clockPaint.setAntiAlias(true);
        //设置画笔颜色
        clockPaint.setColor(Color.BLUE);
        //设置画笔宽度
        clockPaint.setStrokeWidth(10);

        //设置中心点的画笔样式
        centerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerPaint.setColor(Color.RED);
        centerPaint.setStyle(Paint.Style.FILL);

        //设置小时文本的画笔样式
        numPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        numPaint.setTextSize(28);
        numPaint.setColor(Color.RED);
        numPaint.setStyle(Paint.Style.FILL);
        //文本对齐方式
        numPaint.setTextAlign(Paint.Align.CENTER);

        //时针-分针-秒针画笔样式设置
        hourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hourPaint.setColor(hourColor);
        hourPaint.setStrokeWidth(hourWidth);
        hourPaint.setStyle(Paint.Style.FILL);

        minutePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        minutePaint.setColor(minuteColor);
        minutePaint.setStrokeWidth(minuteWidth);
        minutePaint.setStyle(Paint.Style.FILL);

        secondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        secondPaint.setColor(secondColor);
        secondPaint.setStrokeWidth(secondWidth);
        secondPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //获取当前时间
        calendar = Calendar.getInstance();

        //表盘中心点
        int centerX = mWidth / 2;
        int centerY = mHeight / 2;

        //绘制表盘-画圆  --参数信息(中心点,半径,画笔)
        int radius = centerX - 20;
        canvas.drawCircle(centerX, centerY, radius, clockPaint);
        //画表盘中心圆圈
        canvas.drawCircle(centerX, centerY, 20, centerPaint);

        /**
         * 画小时的刻度和数字文本-使用for循环
         * 和画板的save()和restore()方法结合处理
         * 并且配合画板旋转
         */
        for (int i = 1; i <= 12; i++) {
            canvas.save();
            //画板旋转 --参数(选择角度,锚点)
            canvas.rotate(30 * i, centerX, centerY);
            //画小时刻度,一小段直线
            canvas.drawLine(centerX, centerY - radius, centerX, centerY - radius + 16, clockPaint);
            //画小时文本
            canvas.drawText(i + "", centerX, centerY - radius + 40, numPaint);
            canvas.restore();
        }

        //根据时间绘制时分秒
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        //保存当前画布的状态A,然后对画布进行旋转等操作,这个时候的画布状态为B,使用restore()方法将画布状态回退到状态A.
        canvas.save();
        canvas.rotate(hour * 30 + 0.5f * minute, centerX, centerY);
        canvas.drawLine(centerX, centerY - 160, centerX, centerY + 40, hourPaint);
        canvas.restore();
        //分
//        minute = calendar.get(Calendar.MINUTE);
        canvas.save();
        canvas.rotate(minute * 6, centerX, centerY);
        canvas.drawLine(centerX, centerY - 200, centerX, centerY + 50, minutePaint);
        canvas.restore();

        //秒
        int second = calendar.get(Calendar.SECOND);
        canvas.save();
        canvas.rotate(second * 6, centerX, centerY);
        canvas.drawLine(centerX, centerY - 260, centerX, centerY + 60, secondPaint);
        canvas.restore();
//        Logger.d(TAG, "hour:" + hour + ",minute:" + minute + ",second" + second);
        postInvalidateDelayed(1000);
    }
}
