package com.timmy.advance.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 支付宝咻一咻功能实现:
 * 1.先画一个圈
 * 2.画多个圈:用一个list保存多个圈的半径,
 * 3.每隔一段时间去增加一个圆,当圆圈的半径超过屏幕宽度一般时,将这个圆的半径删除
 */
public class XiuXiuView extends View {

    private Paint paint;
    private int mWidth;
    private int mHeight;
//    private float mRadius;
private int mAlpha = 170;
    private long currentTime = System.currentTimeMillis();
    private List<Integer> mRadiuss = new ArrayList<>();
    private Bitmap bitmap;
    private  Handler mHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (System.currentTimeMillis() - currentTime >= 400){
                mRadiuss.add(bitmap.getWidth()/2);
                currentTime = System.currentTimeMillis();
            }
            int size = mRadiuss.size();
            for (int i = 0; i < size; i++) {
               int radius = mRadiuss.get(i);
                mRadiuss.set(i,radius+4);
            }

            Iterator<Integer> iterator = mRadiuss.iterator();
            while (iterator.hasNext()){
                int radius  = iterator.next();
                if (radius >= mWidth/2){
                   //删除
                    iterator.remove();
                }
            }
            invalidate();
            mHander.sendMessageDelayed(Message.obtain(),16);
        }
    };


    public XiuXiuView(Context context) {
        this(context, null);
    }

    public XiuXiuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XiuXiuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化画笔工具
        //参数为抗锯齿设置
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.parseColor("#523684"));
        paint.setColor(getResources().getColor(R.color.c_xiuxiu));

        //从本地获取图片资源
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_good);
//        mRadius = bitmap.getWidth()/2;
        mRadiuss.add(bitmap.getWidth()/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取画布的大小
        mWidth = canvas.getWidth();
        mHeight = canvas.getHeight();

        //先画一个圆
//        canvas.drawCircle(mWidth/2,mHeight/2,mRadius,paint);
        //画多个圆
        int size = mRadiuss.size();
        for (int i = 0; i < size; i++) {
            Integer radius = mRadiuss.get(i);
            mAlpha = 170 - 170*(radius / mWidth);
                    paint.setAlpha(mAlpha);
            canvas.drawCircle(mWidth/2,mHeight/2,radius,paint);
        }

        //先将中间的图片画出来
        canvas.drawBitmap(bitmap, (mWidth- bitmap.getWidth())/2, (mHeight - bitmap.getHeight())/2, paint);

    }

    public void startXiu(){
        //使用Handler来发送消息,通知每隔一段时间,圆圈的半径变化
        mHander.sendEmptyMessage(0);
    }
}
