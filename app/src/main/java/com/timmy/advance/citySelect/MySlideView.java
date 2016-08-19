package com.timmy.advance.citySelect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.timmy.R;

/**
 */
public class MySlideView extends View {


    public interface onTouchListener {
        void showTextView(String textView, boolean dismiss);
    }

    private onTouchListener listener;

    public void setListener(onTouchListener listener) {
        this.listener = listener;
    }


    private int mWidth, mHeight, mTextHeight, position;
    private Paint paint;
    private Rect mBound;
    private int backgroundColor;
    private int yDown, yMove, mTouchSlop;
    private boolean isSlide;
    private String selectTxt;
    private Handler handler = new Handler();

    public MySlideView(Context context) {
        this(context, null);
    }

    public MySlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        mBound = new Rect();
        backgroundColor = getResources().getColor(R.color.transparent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(backgroundColor);
        canvas.drawRect(0, 0, (float) mWidth, mHeight, paint);
        for (int i = 0; i < CitySelectActivity.pinyinList.size(); i++) {
            String textView = CitySelectActivity.pinyinList.get(i);
            if (i == position - 1) {
                paint.setColor(getResources().getColor(R.color.colorPrimary));
                selectTxt = CitySelectActivity.pinyinList.get(i);
                listener.showTextView(selectTxt, false);
            } else {
                paint.setColor(getResources().getColor(R.color.Black));
            }
            paint.setTextSize(40);
            paint.getTextBounds(textView, 0, textView.length(), mBound);
            canvas.drawText(textView, (mWidth - mBound.width()) * 1 / 2, mTextHeight - mBound.height(), paint);
            mTextHeight += mHeight / CitySelectActivity.pinyinList.size();

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y;
                int dy = yMove - yDown;
                //如果是竖直方向滑动
                if (Math.abs(dy) > mTouchSlop) {
                    isSlide = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                backgroundColor = getResources().getColor(R.color.Black);
                mTextHeight = mHeight / CitySelectActivity.pinyinList.size();
                position = y / (mHeight / (CitySelectActivity.pinyinList.size() + 1));
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSlide) {
                    backgroundColor = getResources().getColor(R.color.Black);
                    mTextHeight = mHeight / CitySelectActivity.pinyinList.size();
                    position = y / (mHeight / CitySelectActivity.pinyinList.size() + 1) + 1;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                backgroundColor = getResources().getColor(R.color.Black);
                mTextHeight = mHeight / CitySelectActivity.pinyinList.size();
                position = 0;
                invalidate();
                listener.showTextView(selectTxt, true);
                break;
        }
        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize * 1 / 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize * 1 / 2;
        }
        mWidth = width;
        mHeight = height;
        mTextHeight = mHeight / CitySelectActivity.pinyinList.size();
        setMeasuredDimension(width, height);
    }
}
