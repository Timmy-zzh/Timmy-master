package com.timmy.highUI.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2017/6/1.
 * 仿支付宝支付结果展示效果
 * 三种状态:
 * 加载中 圆弧的弧长变化
 * 支付成功   打勾
 * 支付失败    打差
 */
public class PathLoadingView extends View {

    private int mRadius = 100;//圆圈的半径
    private Paint paint;
    private Path path;
    private PathMeasure pathMeasure;
    private STATUS currStatus = STATUS.LOADING;
    private float minAngle = 30;
    private float startAngle = minAngle;
    private float sweepAngle;
    private int currAngle = 0;
    private Path mPathCircleDst;
    private float circleValue;
    private Path successPath;
    private float successValue;
    private boolean isStartGou = false;
    private int center;
    private Path failurePathLeft;


    public enum STATUS {
        LOADING,
        SUCCESS,
        FAIL
    }

    public PathLoadingView(Context context) {
        this(context, null);
    }

    public PathLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);

        path = new Path();
        mPathCircleDst = new Path();
        successPath = new Path();
        failurePathLeft = new Path();
        pathMeasure = new PathMeasure();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(getPaddingLeft(), getPaddingTop());
        center = canvas.getHeight() / 2;

        switch (currStatus) {
            case LOADING:
                if (startAngle == minAngle) {
                    sweepAngle += 6;
                }
                if (sweepAngle > 300 || startAngle > minAngle) {
                    startAngle += 6;
                    if (sweepAngle > 20) {
                        sweepAngle -= 6;
                    }
                }
                if (startAngle > minAngle + 300) {
                    startAngle %= 360;
                    minAngle = startAngle;
                    sweepAngle = 20;
                }

                canvas.rotate(currAngle += 4, center, center);
                RectF oval = new RectF(center - mRadius, center - mRadius, center + mRadius, center + mRadius);
                //画弧度
                canvas.drawArc(oval, startAngle, sweepAngle, false, paint);

                postInvalidateDelayed(10);
                break;
            case SUCCESS:
                //画圆
                paint.setColor(Color.GREEN);
                path.addCircle(center, center, mRadius, Path.Direction.CW);
                pathMeasure.setPath(path, false);
                pathMeasure.getSegment(0, circleValue * pathMeasure.getLength(), mPathCircleDst, true);
                canvas.drawPath(mPathCircleDst, paint);
                //画钩
                if (circleValue == 1) {
                    if (!isStartGou) {
                        startGouAnim();
                        isStartGou = true;
                    }
                    successPath.moveTo(center / 3 * 2, center / 2 * 2);
                    successPath.lineTo(center, center / 5 * 6);
                    successPath.lineTo(center / 3 * 4, center / 5 * 4);
                    pathMeasure.nextContour();
                    pathMeasure.setPath(successPath, false);
                    pathMeasure.getSegment(0, successValue * pathMeasure.getLength(), mPathCircleDst, true);
                    canvas.drawPath(mPathCircleDst, paint);
                }
                break;
            case FAIL:
//                //画圆
//                paint.setColor(Color.RED);
//                path.addCircle(center, center, mRadius, Path.Direction.CW);
//                pathMeasure.setPath(path, false);
//                pathMeasure.getSegment(0, circleValue * pathMeasure.getLength(), mPathCircleDst, true);
//                canvas.drawPath(mPathCircleDst, paint);
//                //画叉
//                if (circleValue == 1) {
//                    if (!isStartGou) {
//                        startGouAnim();
//                        isStartGou = true;
//                    }
//                    failurePathLeft.moveTo(center / 3, center / 3);
//                    failurePathLeft.lineTo(center / 3 * 2, center / 3 * 2);
//
//                    pathMeasure.nextContour();
//                    pathMeasure.setPath(failurePathLeft, false);
//                    pathMeasure.getSegment(0, successValue * pathMeasure.getLength(), mPathCircleDst, true);
//                    canvas.drawPath(mPathCircleDst, paint);
//                }

                break;
        }
    }


    public void paySuccess() {
        currStatus = STATUS.SUCCESS;
        startCicleAnim();
    }

    private void startCicleAnim() {
        ValueAnimator circleAnimator = ValueAnimator.ofFloat(0, 1);
        circleAnimator.setDuration(1000);
        circleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        circleAnimator.start();
    }


    private void startGouAnim() {
        ValueAnimator circleAnimator = ValueAnimator.ofFloat(0, 1);
        circleAnimator.setDuration(1000);
        circleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                successValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        circleAnimator.start();
    }

    public void payFail() {
        currStatus = STATUS.FAIL;
        isStartGou = false;
        startCicleAnim();
    }
}
