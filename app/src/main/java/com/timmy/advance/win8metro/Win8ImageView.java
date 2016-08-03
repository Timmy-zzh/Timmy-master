package com.timmy.advance.win8metro;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.timmy.util.Logger;

/**
 * 本自定义View主要实现的功能是,获取控件设置的图片数据,进行缩放
 * 监听图片的onTouch事件,如果是按下就缩小图片--使用Handler进行通信
 * 抬起,就放大
 */
public class Win8ImageView extends ImageView {

    private static final int SCALE_REDUCE_INIT = 1;//标记缩小
    private static final int SCALE_ADD_INIT = 2;//标记放大
    private static final int SCALEING = 3;//标记正在缩放处理


    private boolean isFinish = true;//缩放是否结束--默认是结束状态
    private int mWidth;//控件的宽
    private int mHeight;//控件的高
    private int mHalfWidth, mHalfHeight;//宽高的一般
    private float mMinScale = 0.85f;//'最小缩放系数
    private OnViewClickListener mListener;

    public Win8ImageView(Context context) {
        super(context);
    }

    public Win8ImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Win8ImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化-获取控件的图片数据
     * 拿到当前控件的宽高数据--需要减去padding
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Logger.d("--onLayout--changed--" + changed);
        if (changed) {
            mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
            mHeight = getHeight() - getPaddingTop() - getPaddingBottom();

            mHalfWidth = mWidth / 2;
            mHalfHeight = mHeight / 2;

            Drawable drawable = getDrawable();
            BitmapDrawable bd = (BitmapDrawable) drawable;
            bd.setAntiAlias(true);
        }
    }

    /**
     * 点击事件处理:按下时,发送通知去缩小
     * 抬起是:发送通知去放到到原始大小
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.d("--onTouchEvent--ACTION_DOWN--");
                float x = event.getX();
                float y = event.getY();
                mScaleHander.sendEmptyMessage(SCALE_REDUCE_INIT);
                break;
            case MotionEvent.ACTION_UP:
                Logger.d("--onTouchEvent--ACTION_UP--");
                mScaleHander.sendEmptyMessage(SCALE_ADD_INIT);
                break;
        }
        return true;
    }


    /**
     * 缩放处理的Handler
     * 添加一个标记位isFinish标记缩放是否已经完成
     */
    private Handler mScaleHander = new Handler() {

        //是否已经调用了点击事件
        public boolean isClicked;
        private Matrix matrix = new Matrix();
        private int count = 0;
        private float s;

        @Override
        public void handleMessage(Message msg) {
            matrix.set(getImageMatrix());
            switch (msg.what) {
                case SCALE_REDUCE_INIT:
                    if (!isFinish) {//未完成缩放处理-->接着发送通知
                        sendEmptyMessage(SCALE_REDUCE_INIT);
                    } else {
                        //已经缩放完成-->执行缩小操作-->发送通知标示正在缩放
                        Logger.d("--onTouchEvent--ACTION_UP--");
                        isFinish = true;
                        count = 0;
                        s = (float) Math.sqrt(Math.sqrt(mMinScale));
                        beginScale(matrix, s);
                        sendEmptyMessage(SCALEING);
                    }
                    break;
                case SCALEING://缩放四次-->
                    beginScale(matrix, s);
                    if (count < 4) {
                        sendEmptyMessage(SCALEING);
                    } else {
                        //缩放完成
                        isFinish = true;
                        if (Win8ImageView.this.mListener != null && !isClicked) {
                            isClicked = true;
                            mListener.onViewClick(Win8ImageView.this);
                        } else {
                            isClicked = false;
                        }
                    }
                    count++;
                    break;
                case SCALE_ADD_INIT://缩小完成-->执行放大
                    if (!isFinish) {
                        mScaleHander.sendEmptyMessage(SCALE_ADD_INIT);
                    } else {
                        //重置数据,count,执行完成标记位false,缩放系数改变
                        isFinish = false;
                        count = 0;
                        s = (float) Math.sqrt(Math.sqrt(1.0f / mMinScale));
                        beginScale(matrix, s);
                        mScaleHander.sendEmptyMessage(SCALEING);
                    }
                    break;
            }
        }
    };

    private synchronized void beginScale(Matrix matrix, float scale) {
        matrix.postScale(scale, scale, mHalfWidth, mHalfHeight);
        setImageMatrix(matrix);
    }

    public interface OnViewClickListener {
        void onViewClick(Win8ImageView view);
    }

    public void setOnViewClickListener(OnViewClickListener listener) {
        this.mListener = listener;
    }

}
