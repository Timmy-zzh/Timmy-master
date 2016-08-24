package com.timmy.advance.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.timmy.R;
import com.timmy.library.util.Logger;

import java.util.Random;

/**
 * 自定义随机数验证码
 * 功能:点击该自定义View生产不同的四位数
 * 需要处理的有:
 * 1.自定义属性--在attr.xml文件中设置--文字,大小,颜色
 * 2.复写onMeasure方法进行测量--处理不同的SpecMode类型
 * 3.<复写onLayout方法>
 * 4.复写onDraw方法绘制--画笔-画布设置
 * 5.点击事件处理
 */
public class VerificaCodeView extends View {

    private final java.lang.String TAG = this.getClass().getSimpleName();
    private int textColor;
    private String textContent = "";
    private int textSize;
    private Paint mPaint;
    private Rect mBound;

    public VerificaCodeView(Context context) {
        this(context, null);
    }

    public VerificaCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 在该构造函数中获取自定义属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public VerificaCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //其内部调用的就是context.getTheme().obtainStyledAttributes()
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.VerificaCodeView);
        //获取该自定义控件包含自定义属性的个数
        int indexCount = ta.getIndexCount();
        Logger.d(TAG, "--indexCount--" + indexCount);
        for (int i = 0; i < indexCount; i++) {
            //获取第i个属性值
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.VerificaCodeView_titleColor:
                    //文字颜色->获取文字颜色
                    textColor = ta.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.VerificaCodeView_titleSize:
                    //文字大小,默认为16sp
                    /**
                     * TypedValue.COMPLEX_UNIT_SP 为自己设置的单位
                     */
                    int defauleSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
                    Logger.d(TAG, "--defauleSize--" + defauleSize);
                    textSize = ta.getDimensionPixelSize(attr, defauleSize);
                    Logger.d(TAG, "--textSize--" + textSize);
                    break;
                case R.styleable.VerificaCodeView_titleText:
                    //文字内容
                    textContent = ta.getString(attr);
                    Logger.d(TAG, "--textContent--" + textContent);
                    break;
            }
        }
        //一定要记得回收
        ta.recycle();

        init();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                textContent = randomNum();
                invalidate();
            }
        });
    }

    /**
     * 生成四位随机数
     *
     * @return
     */
    private String randomNum() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int nextInt = random.nextInt(10);
            sb.append(nextInt);
        }
        return sb.toString();
    }

    //初始化一些东西
    private void init() {
        mPaint = new Paint();
        //设置画笔文字大小
        mPaint.setTextSize(textSize);
        mBound = new Rect();
        //暂时没有搞明白这个方法的用途
        mPaint.getTextBounds(textContent, 0, textContent.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 绘制--在画布上操作
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //先画背景
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        /**
         * 画文字
         * xPos 是文字左边的位置
         * yPos 是文字底部的位置
         */
        int xPos = getWidth() / 2 - mBound.width() / 2;
        int yPos = getHeight() / 2 + mBound.height() / 2;
        mPaint.setColor(textColor);
        Logger.d(TAG, "--getWidth--" + getWidth() + "--getHeight()--" + getHeight());
        Logger.d(TAG, "--mBound.width()--" + mBound.width() + "--mBound.height()--" + mBound.height());
        canvas.drawText(textContent, xPos, yPos, mPaint);
    }
}
