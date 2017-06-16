package com.timmy.customeView.imoocRipple;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.timmy.R;

/**
 * Created by admin on 2017/6/15.
 * 高仿慕课网下拉刷新控件,具有水波纹效果
 * 1.宽高问题处理 onMeasure
 * 2.onDraw()绘制
 * a.绘制默认展示图片
 * b.绘制可以浮动的水波纹:贝塞尔曲线使用
 */
public class ImoocRippleView extends View {

    private static final long DUTATION = 6 * 1000;
    private int mHeight;
    private int mWidth;
    private Bitmap mResultBitmap;
    private Xfermode xfermode;
    private Canvas mCanvas;
    private Paint mPaint;
    private int mColor = Color.BLUE;
    private Bitmap mLogoBitmap;
    private Path mPath;
    private int currHeight;//水波纹当前的高度: 从mHeight-->0
    private int crestWidth, crestHeight;//一个波峰的宽度和高度
    private int waveWidth = 100;
    private int halfWaveWidth = waveWidth / 2;
    private int dx;

    public ImoocRippleView(Context context) {
        this(context, null);
    }

    public ImoocRippleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImoocRippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfi();
    }

    /**
     * 拿到默认展示图片,获取宽高,在onMeasure方法中需要使用
     * 初始化画笔
     * 新建画布Canvas,需要在该画布上绘制我们想要展示的水波纹
     */
    private void initConfi() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
        mPath = new Path();

        mLogoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_imooc_logo);
        mHeight = mLogoBitmap.getHeight();
        mWidth = mLogoBitmap.getWidth();

        currHeight = mHeight / 2;
        crestWidth = mWidth / 4;
        crestHeight = 20;

        mResultBitmap = Bitmap.createBitmap(mLogoBitmap.getWidth(), mLogoBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mResultBitmap);

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                getSuggestedDimension(widthMeasureSpec, mWidth),
                getSuggestedDimension(heightMeasureSpec, mHeight)
        );
    }

    private int getSuggestedDimension(int measureSpec, int defaultSize) {
        int resultSize = defaultSize;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                resultSize = size;
                break;
            case MeasureSpec.AT_MOST:
                resultSize = Math.min(resultSize, size);
                break;
            default:
                break;
        }
        return resultSize;
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        drawRippleView();
        canvas.drawBitmap(mResultBitmap, getPaddingLeft(), getPaddingTop(), null);
        //刷新

    }

    /**
     * 在画布上绘制,就是往mResultBitmap上面绘制图形
     * 1.绘制贝塞尔曲线的波纹图形
     * 2.再把原图通过xFerMode绘制上去就可以了
     */
    private void drawRippleView() {
        mPath.reset();
        mResultBitmap.eraseColor(Color.parseColor("#00ffffff"));
        //左下角
        mPath.moveTo(0, mHeight);
        //左上角
        mPath.lineTo(-dx, currHeight);
        //贝塞尔曲线
//        mPath.cubicTo(mWidth/4,currHeight-50,mWidth/4*3,currHeight+50,mWidth,currHeight);
        //运动的水波纹效果
//        mPath.rCubicTo(crestWidth, -crestHeight, crestWidth * 3, crestHeight, mWidth, 0);
        for (int i = -waveWidth; i < getWidth() + waveWidth; i += waveWidth) {
            //rQuadTo使用的是相对上一个坐标的位置
            mPath.rQuadTo(halfWaveWidth / 2, -crestHeight, halfWaveWidth, 0);
            mPath.rQuadTo(halfWaveWidth / 2, crestHeight, halfWaveWidth, 0);
        }

        mPath.lineTo(mWidth, currHeight);
        //右下角
        mPath.lineTo(mWidth, mHeight);
        mPath.close();

        //画图片
        mCanvas.drawBitmap(mLogoBitmap, 0, 0, mPaint);
        mPaint.setXfermode(xfermode);
        mCanvas.drawPath(mPath, mPaint);
        mPaint.setXfermode(null);
    }

    public void startRippleMove() {
        startHeighMove();
        startWaveMove();
    }

    private void startHeighMove() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mHeight, 0);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currHeight = (int) animation.getAnimatedValue();
            }
        });
    }

    private void startWaveMove() {
            ValueAnimator animator = ValueAnimator.ofInt(0,waveWidth)
                    .setDuration(DUTATION/4);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    dx = waveWidth - (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        }

    /**
     * 设置当前加载进度,改变水波纹加载的高度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (progress > 0 && progress <= 100) {
            currHeight = (int) (mHeight - (mHeight * progress / 100 * 1.0f));
            Log.d("progress", "progress:" + progress + ",currHeight:" + currHeight);
            invalidate();
        }
    }
}
