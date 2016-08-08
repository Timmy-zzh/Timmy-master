package com.timmy.advance.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.R;
import com.timmy.util.Logger;
import com.timmy.util.Toast;

/**
 * 滑动开关:需要处理控件的onTouchEvent()事件;
 * 1.在手指按下时记录当前位置
 * 2.在手指滑动时记录滑动距离;根据滑动距离更新控件的位置.
 * 3.另外还要响应控件的点击事件
 */
public class SlideToggleButton extends View implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();
    private Bitmap switchBackground;//滑动开关的背景
    private Bitmap slideButton;//滑动按钮
    private boolean currentState = false;//当前开关按钮的状态
    private float slideBtnXLocation;//标记滑动按钮的x位置,默认为0
    private int downX;
    private int moveX;
    private int lastX;//标记手指最后的位置
    private int SLIDE_MAX_VALUE;//手指滑动的最大距离
    private boolean clickTag;//标记点击事件


    public SlideToggleButton(Context context) {
        this(context, null);
    }

    public SlideToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.d(TAG, "MyToggleButton");
        init();
    }

    /**
     * 从本地获取图片资源数据
     */
    private void init() {
        switchBackground = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);
        slideButton = BitmapFactory.decodeResource(getResources(), R.mipmap.slide_button_background);

        SLIDE_MAX_VALUE = switchBackground.getWidth() - slideButton.getWidth();
        Logger.d(TAG, "switchBackground.getWidth()--" + switchBackground.getWidth() + "-slideButton.getWidth()-" + slideButton.getWidth());
        setOnClickListener(this);
        //点击事件--状态改变/控件滑动,从新进行绘制
//        setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                currentState = !currentState;
//                Toast.toastShort("开关状态:" + currentState);
//                flashState();
//                flushView();
//            }
//        });
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置控件的大小
        int width = switchBackground.getWidth();
        int height = switchBackground.getHeight();
        Logger.d(TAG, "--onMeasure--width-" + width + "-height-" + height);
        setMeasuredDimension(width, height);
    }

    /**
     * 设置控件的位置,因为该自定义View继承自View,位置由父控件决定
     * 所以该方法没有多大作用
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Logger.d(TAG, "--onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 在传入的画布中进行绘制--创建画笔
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Logger.d(TAG, "--onDraw");
//        super.onDraw(canvas);
        Paint paint = new Paint();
        //打开抗据此
        paint.setAntiAlias(true);
        //画背景
        canvas.drawBitmap(switchBackground, 0, 0, paint);
        //画滑动开关按钮
        canvas.drawBitmap(slideButton, slideBtnXLocation, 0, paint);
    }

    /**
     * 处理手机触摸事件--
     *
     * @param event
     * @return boolean 返回值调用是否消费本次事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);//触发点击事件
        Logger.d(TAG, "--onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://手指按下,记录按下时的位置
                clickTag = true;
                downX = (int) event.getX();
                lastX = downX;
                Logger.d(TAG, "--ACTION_DOWN--downX-" + downX);
//                Logger.d(TAG, "--ACTION_DOWN--getRawX-" + event.getRawX() + "--getRawY--" + event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE://手指滑动,记录滑动的距离,注意边界值
                clickTag = false;
                moveX = (int) event.getX();
                Logger.d(TAG, "--ACTION_MOVE--moveX-" + moveX);
                int offset = moveX - lastX;
                lastX = moveX;
                //以为坐标原点在左上角,所以手指往右滑动,offset>0,反之往左滑动,offset<0
                Logger.d(TAG, "--ACTION_MOVE--offset-" + offset + "--SLIDE_MAX_VALUE--" + SLIDE_MAX_VALUE);
                //设置滑动按钮的位置
                slideBtnXLocation = slideBtnXLocation + offset;

                if (slideBtnXLocation > SLIDE_MAX_VALUE) {//边界处理,右滑到最右边,滑动值设为最大值
                    slideBtnXLocation = SLIDE_MAX_VALUE;
                } else if (slideBtnXLocation < 0) {//往左滑动,且超过最大距离
                    slideBtnXLocation = 0;
                }
                Logger.d(TAG, "--ACTION_MOVE--slideBtnXLocation-" + slideBtnXLocation);
                break;
            case MotionEvent.ACTION_UP://当手指抬起时,需要判断滑动的距离的大小是否大于滑动距离的一半,大于的话,需要设置滑动按钮的值
                //手指抬起的时候,需要标记点击事件触发
//                clickTag = true;

                if (slideBtnXLocation <= SLIDE_MAX_VALUE / 2) {//滑动距离小于最大值一半
//                    slideBtnXLocation = 0; //状态为关闭
                    currentState = false;
                } else {
//                    slideBtnXLocation = SLIDE_MAX_VALUE; //状态为开启
                    currentState = true;
                }
                flashState();
                Logger.d(TAG, "--ACTION_UP--slideBtnXLocation-" + slideBtnXLocation);
                Logger.d(TAG, "--ACTION_UP--clickTag-" + clickTag);
                break;
        }
        //根据滑动按钮x方向的位置,去重新绘制控件
        flushView();
        return true;
    }

    /**
     * 会从新调用onDraw方法再次绘制
     * 这两个方法的区别???
     */
    private void flushView() {
//        postInvalidate();
        invalidate();
    }

    private void flashState() {
        if (currentState) {
            slideBtnXLocation = switchBackground.getWidth() - slideButton.getWidth();
        } else {
            slideBtnXLocation = 0;
        }
    }

    @Override
    public void onClick(View v) {
        if (clickTag) {//点击事件处理
            currentState = !currentState;
            Toast.toastShort("开关状态:" + currentState);
            flashState();
            flushView();
        } else {
            //拖动处理
        }
    }
}
