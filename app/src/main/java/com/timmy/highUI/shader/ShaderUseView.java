package com.timmy.highUI.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.R;

/**
 * Created by admin on 2017/2/13.
 * 高级渲染使用
 */
public class ShaderUseView extends View {

    private Bitmap bitmap;
    private Paint paint;
    private int mWidth;
    private int mHeight;

    public ShaderUseView(Context context) {
        this(context,null);
    }

    public ShaderUseView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShaderUseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_meinv2)).getBitmap();
        mWidth = bitmap.getWidth();
        mHeight = bitmap.getHeight();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);//画背景
//        canvas.drawBitmap(bitmap,0,0,paint);//在画布上画图片，第2,3参数表示图片的左边和上边的位置
        /**
         * TileMode.CLAMP 拉伸最后一个像素去铺满剩下的地方
         * TileMode.MIRROR 通过镜像翻转铺满剩下的地方。
         * TileMode.REPEAT 重复图片平铺整个画面（电脑设置壁纸）
         */
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);

//        int radius = Math.min(mWidth/2,mHeight/2);
//        canvas.drawCircle(mWidth/2,mHeight/2,radius,paint);

        RectF rectOval = new RectF(0,0,mWidth,mHeight);
        canvas.drawOval(rectOval,paint);

    }
}
