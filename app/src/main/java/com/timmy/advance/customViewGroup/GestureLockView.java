package com.timmy.advance.customViewGroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.timmy.library.util.Logger;

/**
 * Created by admin on 2016/9/8.
 */
public class GestureLockView extends View {

    private static final java.lang.String TAG = GestureLockView.class.getSimpleName();
    private int mColorNoFingerOutter;
    private int mColorNoFingerInner;
    private int mColorFingerOn;
    private int mColorFingerUp;
    private int mWidth;
    private int mHeight;
    private int mOutterRadius;//外圆半径
    private int mInnerRadius;//内圆半径
    private int mTriangWidth;//三角形宽度
    private final Paint mPaint;
    private final Path mTrianglePath;
    private int mStrokeWidth = 2;
    private int centreX;
    private int centreY;
    private Status currentStatus;
    private int mDegree = -1;


    public enum Status {
        STATUS_NO_FINGER, STATUS_FINGER_ON, STATUS_FINGER_UP;
    }

    public GestureLockView(Context context, int mColorNoFingerOutter, int mColorNoFingerInner,
                           int mColorFingerOn, int mColorFingerUp) {
        super(context);
        this.mColorNoFingerOutter = mColorNoFingerOutter;
        this.mColorNoFingerInner = mColorNoFingerInner;
        this.mColorFingerOn = mColorFingerOn;
        this.mColorFingerUp = mColorFingerUp;

        //初始化画笔和三角形路径
        mPaint = new Paint();
        mTrianglePath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mWidth = mHeight = Math.min(mWidth, mHeight);
        centreX = centreY = mOutterRadius = mWidth / 2;
        mOutterRadius -= mStrokeWidth / 2;
        mInnerRadius = mTriangWidth = mOutterRadius / 3;
        //三角形
        mTrianglePath.moveTo(centreX, mStrokeWidth + 2);
        mTrianglePath.lineTo(centreX - mTriangWidth, mStrokeWidth + 2 + mTriangWidth);
        mTrianglePath.lineTo(centreX + mTriangWidth, mStrokeWidth + 2 + mTriangWidth);
        mTrianglePath.close();
        mTrianglePath.setFillType(Path.FillType.WINDING);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Logger.d(TAG, "--onDraw--mColorNoFingerOutter:" + mColorNoFingerOutter +
                "--mOutterRadius:" + mOutterRadius + "--mInnerRadius:" + mInnerRadius);
        switch (currentStatus) {
            case STATUS_NO_FINGER:
                //绘制外圆
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(mColorNoFingerOutter);
                canvas.drawCircle(centreX, centreY, mOutterRadius, mPaint);
                //绘制内圆
                mPaint.setColor(mColorNoFingerInner);
                canvas.drawCircle(centreX, centreY, mInnerRadius, mPaint);
                break;
            case STATUS_FINGER_ON:
                //绘制外环
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(mStrokeWidth);
                mPaint.setColor(mColorFingerOn);
                canvas.drawCircle(centreX, centreY, mOutterRadius, mPaint);
                //绘制内圆
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(mColorFingerOn);
                canvas.drawCircle(centreX, centreY, mInnerRadius, mPaint);
                break;
            case STATUS_FINGER_UP:
                //绘制外环
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setStrokeWidth(mStrokeWidth);
                mPaint.setColor(mColorFingerUp);
                canvas.drawCircle(centreX, centreY, mOutterRadius, mPaint);
                //绘制内圆
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(mColorFingerUp);
                canvas.drawCircle(centreX, centreY, mInnerRadius, mPaint);

                drawTriangle(canvas);
                break;
        }
    }

    /**
     * 绘制三角形
     *
     * @param canvas
     */
    private void drawTriangle(Canvas canvas) {
        if (mDegree != -1) {
            mPaint.setStyle(Paint.Style.FILL);
            canvas.save();
            canvas.rotate(mDegree, centreX, centreY);
            canvas.drawPath(mTrianglePath, mPaint);
            canvas.restore();
        }
    }

    public void setStatus(Status status) {
        this.currentStatus = status;
        invalidate();
    }

    public void setArrowDegree(int degree) {
        this.mDegree = degree;
//        invalidate();
    }
}
