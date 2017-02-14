package com.timmy.highUI.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.R;

/**
 * Created by admin on 2017/2/14.
 * 实现图片点击局部放大的效果
 * 1.获取原始图片
 * 2.根据原始图片生成放大后的图片
 * 3.点击图片的时候截取对应放大后的图片的局部图片
 * 4.在原始图片的布局上再覆盖一层局部位置放大图片
 *
 */
public class ZoomImageView extends View {

    private final int FACTOR = 3;
    private final int RADIUS = 100;
    private Bitmap bitmap;
    private Bitmap scaleBitmap;
    private ShapeDrawable drawable;
    private Matrix matrix = new Matrix();

    public ZoomImageView(Context context) {
        this(context,null);
    }

    public ZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //原始图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_meinv2);
        //放大3倍后的图片
        scaleBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()*FACTOR, bitmap.getHeight()*FACTOR,true);
        //制作一个圆形的图片（图片放大的部分），改在Canvas上面--渲染部分
        BitmapShader bitmapShader = new BitmapShader(scaleBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //创建一个drawable--图片：再在该图片上将渲染部分放在上面，并设置宽高
        drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setShader(bitmapShader);
        //切出矩形区域--再绘制内切圆
        drawable.setBounds(0,0,RADIUS*2,RADIUS*2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制图片，再绘制局部放大部分
        canvas.drawBitmap(bitmap,0,0,null);
        drawable.draw(canvas);
    }

    //点击事件处理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        matrix.setTranslate(RADIUS - x*FACTOR,RADIUS - y*FACTOR);
        drawable.getPaint().getShader().setLocalMatrix(matrix);
        drawable.setBounds(x - RADIUS,y-RADIUS,x+RADIUS,y+RADIUS);

        invalidate();
        return true;
    }
}
