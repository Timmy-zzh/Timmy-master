package com.timmy.customeView.notePad;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;

/**
 * Created by admin on 2017/2/13.
 * 自定义笔记本实现NotePad界面:
 * 1.绘制线条
 */
public class NotePadLayout extends EditText {

    private int padding = 20;
    private int lineColor = Color.GREEN;
    private float lineWidth = 1;
    private Paint paint;

    public NotePadLayout(Context context) {
        this(context, null);
    }

    public NotePadLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotePadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化
    private void init() {
        //焦点处理,
        setFocusableInTouchMode(true);
        setGravity(Gravity.TOP);
        //设置每行的高度
        setLineSpacing(40, 1);
        setPadding(25, 10, padding, 10);
        //绘制画线的画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(lineColor);
        paint.setStrokeWidth(lineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int viewHeight = getHeight();//屏幕高度
        int lineHeight = getLineHeight();//每行文本的高度
        //计算每页有多少行
        int lineCount = viewHeight / lineHeight;

        for (int i = 0; i < lineCount; i++) {
            //绘制间隔线
            canvas.drawLine(padding, (i + 1) * lineHeight, getWidth() - padding, (i + 1) * lineHeight, paint);
        }

        //获取当前文本的总行数
        int allCount = getLineCount();
        int extraCount = allCount - lineCount;
        //绘制剩余下来的行数

    }
}
