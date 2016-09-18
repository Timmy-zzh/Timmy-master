package com.timmy.advance.customViewGroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.timmy.R;
import com.timmy.library.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置回调接口:获取手指滑动到的锁按钮数字
 * 设置是否是正确结果的回调
 * 设置超过试用次数的回调
 */
public class GestureLockViewGroup extends RelativeLayout {

    private static final java.lang.String TAG = GestureLockViewGroup.class.getSimpleName();
    private int mColorNoFingerOutter = 0Xabc111;
    private int mColorNoFingerInner = 0X87d888;
    private int mColorFingerOn = 0X2dd458;
    private int mColorFingerUp = 0X008877;
    private int tryTime = 3;
    private int rawCount = 3;
    private Paint mPaint;
    private Path mPath;
    private int mWidth;
    private int mHeight;
    private GestureLockView[] gestureLockViews;
    private List<Integer> mChoosedLock = new ArrayList<>();//已经选择的锁按钮
    private int gestureLockWidth;
    private int mLastPathX;
    private int mLastPathY;
    private Point mGuidePoint = new Point();
    private int[] mAnswer = new int[]{1, 2, 5, 8};
    private OnGestureLockViewListener mListener;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            reset();
            invalidate();
        }
    };


    public GestureLockViewGroup(Context context) {
        this(context, null);
    }

    public GestureLockViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLockViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GestureLockViewGroup, defStyleAttr, 0);

        mColorNoFingerOutter = ta.getColor(R.styleable.GestureLockViewGroup_noFingerOutterColor, mColorNoFingerOutter);
        mColorNoFingerInner = ta.getColor(R.styleable.GestureLockViewGroup_noFingerInnerColor, mColorNoFingerInner);
        mColorFingerOn = ta.getColor(R.styleable.GestureLockViewGroup_fingerOnColor, mColorFingerOn);
        mColorFingerUp = ta.getColor(R.styleable.GestureLockViewGroup_fingerUpColor, mColorFingerUp);

        rawCount = ta.getInt(R.styleable.GestureLockViewGroup_rawCount, rawCount);
        tryTime = ta.getInt(R.styleable.GestureLockViewGroup_tryTime, tryTime);
        Logger.d(TAG, "--GestureLockViewGroup--mColorNoFingerOutter:" + mColorNoFingerOutter + "--rawCount:" + rawCount);

        ta.recycle();

        //初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        mPath = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = mHeight = Math.min(mWidth, mHeight);

        Logger.d(TAG, "--onMeasure--mWidth:" + mWidth + "--mHeight:" + mHeight);

        if (gestureLockViews == null) {
            gestureLockViews = new GestureLockView[rawCount * rawCount];
            // 设置单个按钮锁的宽度,相互之间的间隔为按钮锁宽度的1/4,
            gestureLockWidth = mWidth * 4 / (5 * rawCount + 1);
            mPaint.setStrokeWidth(gestureLockWidth / 3);
            int getureMarginBetween = gestureLockWidth / 4;

            for (int i = 0; i < gestureLockViews.length; i++) {
                gestureLockViews[i] = new GestureLockView(getContext(), mColorNoFingerOutter,
                        mColorNoFingerInner, mColorFingerOn, mColorFingerUp);

                //设置,id 大小,位置,间隔,状态
                gestureLockViews[i].setId(i + 1);//设置id,也就是锁按钮代表的数字
                //大小
                RelativeLayout.LayoutParams lockParams = new RelativeLayout.LayoutParams(gestureLockWidth, gestureLockWidth);
                //排列 不是第一列->都放到前一个右边
                if (i % rawCount != 0) {
                    lockParams.addRule(RIGHT_OF, gestureLockViews[i - 1].getId());
                }
                //从第二行开始->放在上一行下面
                if (i > rawCount - 1) {
                    lockParams.addRule(BELOW, gestureLockViews[i - rawCount].getId());
                }

                int leftMargin = 0;
                int topMargin = 0;
                int rightMargin = getureMarginBetween;
                int bottomMargin = getureMarginBetween;
                //间隔 第一行
                if (i < rawCount) {
                    topMargin = getureMarginBetween;
                }
                //第一列
                if (i % rawCount == 0) {
                    leftMargin = getureMarginBetween;
                }

                lockParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                gestureLockViews[i].setStatus(GestureLockView.Status.STATUS_NO_FINGER);
                addView(gestureLockViews[i], lockParams);
            }
        }
    }

    /**
     * 手指点击事件的处理
     * down事件的时候:重置一些数据--轨迹和选择的数字的清理,锁按钮充值状态
     * move事件:获取当前手指的位置是否在单个锁按钮的位置范围内?是的话改变锁按钮状态
     * 记录手指滑动的轨迹
     * up的事件:绘制锁轨迹,回调接口,更新锁按钮状态,
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                reset();
                break;
            case MotionEvent.ACTION_MOVE:
                //设置轨迹画笔属性
                mPaint.setColor(mColorFingerOn);
                mPaint.setAlpha(50);

                //根据手指当前位置获取手指下面的锁按钮
                GestureLockView lockChild = getChildLockByPos(x, y);
                if (lockChild != null) {
                    int id = lockChild.getId();
                    if (!mChoosedLock.contains(id)) {
                        mChoosedLock.add(id);
                        lockChild.setStatus(GestureLockView.Status.STATUS_FINGER_ON);
                        if (mListener != null) {
                            mListener.onLockSelected(id);
                        }

                        //获取指引线的位置
                        mLastPathX = lockChild.getLeft() / 2 + lockChild.getRight() / 2;
                        mLastPathY = lockChild.getTop() / 2 + lockChild.getBottom() / 2;
                        Logger.d(TAG, "--mLastPathX:" + mLastPathX + "--mLastPathY:" + mLastPathY);
                        if (mChoosedLock.size() == 1) {//起点位置
                            mPath.moveTo(mLastPathX, mLastPathY);
                        } else {
                            mPath.lineTo(mLastPathX, mLastPathY);
                        }
                    }
                }
                mGuidePoint.x = x;
                mGuidePoint.y = y;
                break;
            case MotionEvent.ACTION_UP:
                mPaint.setColor(mColorFingerUp);
                mPaint.setAlpha(50);
                this.tryTime--;

                mGuidePoint.x = mLastPathX;
                mGuidePoint.y = mLastPathY;

                if (mListener != null && mChoosedLock.size() > 0) {
                    mListener.isAnswerMatched(checkAnswer());
                    if (tryTime == 0)
                        mListener.onUnMatchedExceedBoundary();
                }

                if (handler != null) {
                    handler.sendEmptyMessageDelayed(0,600);
                }

                changeItemStatus();
                //计算每个元素中箭头需要旋转的角度
                for (int i = 0; i + 1 < mChoosedLock.size(); i++) {
                    int startId = mChoosedLock.get(i);
                    int endId = mChoosedLock.get(i + 1);
                    Logger.d(TAG, "--startId:" + startId + "--endId:" + endId);
                    GestureLockView startLock = (GestureLockView) findViewById(startId);
                    GestureLockView endLock = (GestureLockView) findViewById(endId);

                    int dx = endLock.getLeft() - startLock.getLeft();
                    int dy = endLock.getTop() - startLock.getTop();
                    //计算角度
                    int angle = (int) (Math.toDegrees(Math.atan2(dy, dx)) + 90);
                    startLock.setArrowDegree(angle);
                }

                break;
        }
        invalidate();
        return true;
    }

    /**
     * 检测滑动的锁按钮是否和设置的答案是否匹配
     *
     * @return
     */
    private boolean checkAnswer() {
        if (mAnswer.length != mChoosedLock.size())
            return false;
        for (int i = 0; i < mChoosedLock.size(); i++) {
            if (mChoosedLock.get(i) != mAnswer[i])
                return false;
        }
        return true;
    }

    private void changeItemStatus() {
        for (GestureLockView gestureLockView : gestureLockViews) {
            if (mChoosedLock.contains(gestureLockView.getId()))
                gestureLockView.setStatus(GestureLockView.Status.STATUS_FINGER_UP);
        }
    }

    /**
     * 根据坐标(x,y)获取当前锁按钮
     *
     * @param x
     * @param y
     * @return
     */
    private GestureLockView getChildLockByPos(int x, int y) {
        for (GestureLockView gestureLockView : gestureLockViews) {
            if (checkPositionInChild(gestureLockView, x, y)) {
                return gestureLockView;
            }
        }
        return null;
    }

    /**
     * 判断坐标(x,y)是否在当前锁按钮范围内
     *
     * @param child
     * @param x
     * @param y
     * @return
     */
    private boolean checkPositionInChild(View child, int x, int y) {
        int padding = (int) (gestureLockWidth * 0.15);
        Logger.d(TAG, "--child--left:" + child.getLeft() + "--right:" + child.getRight()
                + "--Top:" + child.getTop() + "--Bottom:" + child.getBottom());
        if (x > child.getLeft() + padding &&
                x < child.getRight() - padding &&
                y > child.getTop() + padding &&
                y < child.getBottom() - padding) {
            return true;
        }
        return false;
    }

    public void reset() {
        mChoosedLock.clear();
        mPath.reset();

        //充值全部锁状态
        for (GestureLockView gestureLockView : gestureLockViews) {
            gestureLockView.setStatus(GestureLockView.Status.STATUS_NO_FINGER);
            gestureLockView.setArrowDegree(-1);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Logger.d(TAG, "--dispatchDraw");
        super.dispatchDraw(canvas);
        if (mPath != null) {
            canvas.drawPath(mPath, mPaint);
        }
        //绘制指引线
        if (mChoosedLock.size() > 0) {
            if (mLastPathX != 0 && mLastPathY != 0) {
                Logger.d(TAG, "--child--mLastPathX:" + mLastPathX + "--mLastPathY:" + mLastPathY +
                        "--mGuidePoint.x:" + mGuidePoint.x + "--mGuidePoint.y:" + mGuidePoint.y);
                canvas.drawLine(mLastPathX, mLastPathY, mGuidePoint.x, mGuidePoint.y, mPaint);
            }
        }
    }

    public void setAnswer(int[] answer) {
        this.mAnswer = answer;
    }

    public interface OnGestureLockViewListener {
        void onLockSelected(int number);

        void isAnswerMatched(boolean isMatched);

        void onUnMatchedExceedBoundary();//超过尝试次数
    }

    public void setOnGestureLockViewListener(OnGestureLockViewListener listener) {
        this.mListener = listener;
    }
}
