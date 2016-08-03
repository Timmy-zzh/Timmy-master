package com.timmy.advance.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.R;
import com.timmy.util.Logger;
import com.timmy.util.Toast;

/**
 * 自定义View开关按钮,搞明白自定义View中的各个方法的使用和含义
 * 实现功能,点击按钮实现按钮开关两种状态的切换
 * 1.构造函数 MyToggleButton(Context context)该函数在代码实例化会调用
 * MyToggleButton(Context context, AttributeSet attrs)通过xml文件调用,
 * AttributeSet是控件的属性.
 * 实现步骤:初始化获取控件需要的图片资源-->测量获取图片资源的宽高-->最后是绘制-->点击事件处理
 * onMeasure()方法实现控件测量
 * onLayout()方法实现控件的布局位置
 * onDraw()方法实现控件的绘制
 * setOnclickListener()设置控件的点击回调
 */
public class MyToggleButton extends View {

    private final String TAG = this.getClass().getSimpleName();
    private Bitmap switchBackground;//滑动开关的背景
    private Bitmap slideButton;//滑动按钮
    private boolean currentState = false;//当前开关按钮的状态
    private float slideBtnWidth;


    public MyToggleButton(Context context) {
        this(context, null);
    }

    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.d(TAG, "MyToggleButton");
        init();
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
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 在传入的画布中进行绘制--创建画笔
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Paint paint = new Paint();
        //打开抗据此
        paint.setAntiAlias(true);
        //画背景
        canvas.drawBitmap(switchBackground, 0, 0, paint);
        //画滑动开关按钮
        canvas.drawBitmap(slideButton, slideBtnWidth, 0, paint);
    }

    /**
     * 从本地获取图片资源数据
     */
    private void init() {
        switchBackground = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);
        slideButton = BitmapFactory.decodeResource(getResources(), R.mipmap.slide_button_background);

        //点击事件--状态改变/控件滑动,从新进行绘制
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentState = !currentState;
                Toast.toastShort("开关状态:" + currentState);
                flashState();
                flushView();
            }
        });
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
            slideBtnWidth = switchBackground.getWidth() - slideButton.getWidth();
        } else {
            slideBtnWidth = 0;
        }
    }
}
