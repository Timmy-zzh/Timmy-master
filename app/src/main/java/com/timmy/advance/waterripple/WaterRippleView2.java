package com.timmy.advance.waterRipple;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义View实现水波纹效果
 * 一:实现点击就出现水波纹效果--界面上只有一小圆环
 * 实现步骤:初始化画笔->测量(宽高为屏幕宽高,使用默认测量方法)->布局(用不上)-->
 * 绘制(在画布上绘制水波纹)->点击事件处理(获取点击的位置,在该位置绘制波纹效果,并且随着时间波纹变大,透明度变淡)
 * <p/>
 * 二:界面上有多个圆环--需要使用集合保存多个波纹对象,波纹对象中保存有自己的画笔,画笔宽度和透明度和圆环的位置
 * 还要处理多次点击创建波纹对象,并且进行界面绘制
 * 点击事件的处理:怎样让后续点击生成的波纹自动变化-->并且在单个波纹透明度变为0后,需要从列表中删除
 */
public class WaterRippleView2 extends View {
    private final String TAG = this.getClass().getSimpleName();

    private List<Ripple> rippleList;
    private int MAX_ALPHA = 255;
    private boolean isEmpty = true;//标记波纹列表是否为空,
    private int[] colors = new int[]{Color.RED, Color.BLUE, Color.DKGRAY, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.BLACK};

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                //更新画笔设置--透明度,半径,宽度->重新绘制->透明度未减少到0时,再次发送消息
                flushState();
                invalidate();
                if (rippleList != null && !rippleList.isEmpty()) {//当波纹列表不为空时,循环发送通知
                    handler.sendEmptyMessageDelayed(0, 50);
                }
            }
        }
    };


    /**
     * 遍历波纹列表中所有的波纹对象,判断波纹对象的透明度是否为0,为0则将该对象从列表中删除
     */

    private void flushState() {
        for (int i = 0; i < rippleList.size(); i++) {
            Logger.d(TAG, "--rippleCount-" + i);
            Ripple ripple = rippleList.get(i);
            if (isEmpty == false && ripple.alpha == 0) {
                rippleList.remove(i);
                ripple.paint = null;
                ripple.paint = null;
                continue;
            } else if (isEmpty == true) {
                isEmpty = false;
            }
            ripple.alpha -= 10;
            ripple.radius += rippleList.size() - i;
            if (ripple.alpha < 0)
                ripple.alpha = 0;
            ripple.width = ripple.radius / 4;
            ripple.paint.setAlpha(ripple.alpha);
            ripple.paint.setStrokeWidth(ripple.width);
        }
    }


    public WaterRippleView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化波纹列表对象
        rippleList = Collections.synchronizedList(new ArrayList<Ripple>());
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
    private Paint initPaint(int alpha, float width) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //设置圆环宽度
        paint.setStrokeWidth(width);
        //设置画笔空心绘制
        paint.setStyle(Paint.Style.STROKE);
        //设置画笔透明度
        paint.setAlpha(alpha);
        //设置画笔颜色
        paint.setColor(colors[(int) (Math.random() * (colors.length - 1))]);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 需要绘制波纹列表中全部的波纹
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        for (Ripple ripple : rippleList) {
            canvas.drawCircle(ripple.xDown, ripple.yDown, ripple.radius, ripple.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://每次点击都需要创建一个新的波纹对象,并且不能一直发送消息

            case MotionEvent.ACTION_MOVE:
                Ripple ripple = new Ripple();

                ripple.radius = 0;
                ripple.alpha = MAX_ALPHA;
                ripple.width = ripple.radius / 4;
                ripple.xDown = (int) event.getX();
                ripple.yDown = (int) event.getY();
                ripple.paint = initPaint(ripple.alpha, ripple.width);
                if (rippleList.size() == 0) {
                    isEmpty = true;
                }
                Logger.d(TAG, "--isEmpty-" + isEmpty);
                rippleList.add(ripple);
                invalidate();//重新绘制

                if (isEmpty)//波纹列表为空,需要发送通知,不为空,Hnadler自动发送通知
                    handler.sendEmptyMessage(0);

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
