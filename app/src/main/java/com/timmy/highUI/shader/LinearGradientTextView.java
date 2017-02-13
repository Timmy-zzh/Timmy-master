package com.timmy.highUI.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by admin on 2017/2/13.
 * 线性渐变的文字，模仿KTV歌词播放效果
 */
public class LinearGradientTextView extends TextView {

    private TextPaint textPaint;
    private int[] colors = new int[]{Color.RED, Color.WHITE, Color.BLUE};
    private Matrix matrix;
    private LinearGradient linearGradient;
    private int translationX;
    private int deltaX = 20;
    private Paint paint;

    public LinearGradientTextView(Context context) {
        this(context, null);
    }

    public LinearGradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//         paint =  new Paint();
//        linearGradient = new LinearGradient(0,0,200,0,colors,new float[]{0, 0.5f, 1f}, Shader.TileMode.CLAMP);
//        paint.setShader(linearGradient);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        paint = getPaint();
        //在该方法中获取文本宽度
        String text = getText().toString();
        float textWidth = paint.measureText(text);
        //渲染的宽度
        int gradientWidth = (int) (3 * textWidth / text.length());
        linearGradient = new LinearGradient(-gradientWidth, 0, 0, 0, new int[]{0xaa000000,0xffffffff,0xaa000000},
                new float[]{0, 0.5f, 1f}, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        //矩阵，用于改变位置
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float textWidth = paint.measureText(getText().toString());
        translationX += deltaX;
        if (translationX > textWidth + 1 || translationX < 1) {
            deltaX = -deltaX;//加减改变处理
        }

        matrix.setTranslate(translationX, 0);
        linearGradient.setLocalMatrix(matrix);
        //每隔50毫秒去重新绘制文本渐变部分，渐变部分的位置发生改变
        postInvalidateDelayed(50);

//        canvas.drawRect(0,0,200,200,paint);
    }
}
