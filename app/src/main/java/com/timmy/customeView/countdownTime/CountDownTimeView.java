package com.timmy.customeView.countdownTime;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.timmy.R;

/**
 * Created by admin on 2017/6/21.
 * 广告页面倒计时处理:
 * 1.使用CountDownTime实现倒计时功能
 * 2.
 * <p>
 * <p>
 * 1000为倒计时时长
 * 10为每隔10毫秒会调用一次onTick方法
 * CountDownTimer countDownTimer = new CountDownTimer(1000,50) {
 *
 * @Override public void onTick(long millisUntilFinished) {
 * Log.d(TAG,"millisUntilFinished:"+millisUntilFinished);
 * }
 * @Override public void onFinish() {
 * Log.d(TAG,"onFinish:");
 * }
 * }.start();
 */
public class CountDownTimeView extends View {

    public static final String TAG = CountDownTimeView.class.getSimpleName();
    private static final int BACKGROUND_COLOR = 0x50555555;
    private static final int PROGRESS_COLOR = Color.BLUE;
    private static final float PROGRESS_WIDTH = 10f;
    private static final float TEXT_SIZE = 50f;
    private static final String TEXT = "跳过广告";
    private static final int TEXT_COLOR = 0xffffffff;
    private static final int TAG_WIDTH = 0;
    private static final int TAG_HEIGHT = 1;

    private float mTextSize;
    private int mProgressColor;
    private int mBackgroundColor;
    private float mProgressWidht;
    private final int mTextColor;
    private String mText;
    private Paint mCirclePaint;
    private Paint mProgressPaint;
    private TextPaint mTextPaint;
    private StaticLayout mStaticLayout;
    private int mWidth;
    private int mHeight;
    private int min;
    private RectF mOval;

    public CountDownTimeView(Context context) {
        this(context, null);
    }

    public CountDownTimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "CountDownTimeView -- ");

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownTimeView, defStyleAttr, 0);
        //获取各种属性值
        /**
         *   圆盘的背景色
         圆环颜色
         圆环宽度
         文字内容
         文字颜色
         文字大小
         */
        mBackgroundColor = typedArray.getColor(R.styleable.CountDownTimeView_background_color, BACKGROUND_COLOR);
        mProgressColor = typedArray.getColor(R.styleable.CountDownTimeView_progress_color, PROGRESS_COLOR);
        mProgressWidht = typedArray.getDimension(R.styleable.CountDownTimeView_progress_width, PROGRESS_WIDTH);
        mText = typedArray.getString(R.styleable.CountDownTimeView_text);
        if (TextUtils.isEmpty(mText)) {
            mText = TEXT;
        }
        mTextSize = typedArray.getDimension(R.styleable.CountDownTimeView_text_size, TEXT_SIZE);
        mTextColor = typedArray.getColor(R.styleable.CountDownTimeView_text_color, TEXT_COLOR);
        typedArray.recycle();

        initConf();
    }

    /**
     * 初始化画笔属性
     * 需要画背景圆
     * 画圆弧
     * 画文字
     */
    private void initConf() {
        //背景圆画笔
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(mBackgroundColor);
        mCirclePaint.setStyle(Paint.Style.FILL);
//        mCirclePaint.setDither(); 增加性能

        //圆弧画笔
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidht);

        //文字画笔
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        //使用StaticLayout实现文字绘制自动换行效果
        int textWidth = (int) mTextPaint.measureText(mText.substring(0, (mText.length() + 1) / 2));//文字宽度的一半
        mStaticLayout = new StaticLayout(mText, mTextPaint, textWidth, Layout.Alignment.ALIGN_NORMAL,
                1f, 0, false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
        setMeasuredDimension(setSuitableDimension(widthMeasureSpec, TAG_WIDTH), setSuitableDimension(heightMeasureSpec, TAG_HEIGHT));
    }

    private int setSuitableDimension(int measureSpec, int tag) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode != MeasureSpec.EXACTLY) {
            switch (tag) {
                case TAG_WIDTH:
                    size = mStaticLayout.getWidth();
                    break;
                case TAG_HEIGHT:
                    size = mStaticLayout.getHeight();
                    break;
            }
        }
        return size;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged");
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        min = Math.min(mWidth, mHeight);
        Log.d(TAG, "onSizeChanged  mWidth:"+mWidth+",mHeight:"+mHeight);
        //绘制圆弧的矩形范围
        if (mWidth > mHeight) {
            mOval = new RectF(mWidth / 2 - min / 2 + mProgressWidht / 2,
                    mProgressWidht / 2,
                    mWidth / 2 + min / 2 - mProgressWidht / 2,
                    mHeight - mProgressWidht / 2);
        } else {
            mOval = new RectF(mProgressWidht / 2,
                    mHeight / 2 - min / 2 + mProgressWidht / 2,
                    mWidth - mProgressWidht / 2,
                    mHeight / 2 + min / 2 - mProgressWidht / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw");
        //画背景圆
        canvas.drawCircle(mWidth / 2, mHeight / 2, min / 2, mCirclePaint);
        //画圆弧
        canvas.drawArc(mOval, -90, 100, false, mProgressPaint);
        //画文字-->画布居中,左上角为控制点
        canvas.translate(mWidth/2,mHeight/2-mStaticLayout.getHeight()/2);
        mStaticLayout.draw(canvas);
    }
}
