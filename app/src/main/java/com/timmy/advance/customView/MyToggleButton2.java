package com.timmy.advance.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.R;
import com.timmy.util.Logger;
import com.timmy.util.Toast;

/**
 * 自定义View,图片资源和开关状态从xml文件中获取
 * 1.需要在attr.xml文件中定义好属性信息-->然后再xml文件中使用该属性
 * -->最后在构造函数中通过代码获取xml文件中设置的属性信息
 */
public class MyToggleButton2 extends View {

    private final String TAG = this.getClass().getSimpleName();
    private Bitmap switchBackground;//滑动开关的背景
    private Bitmap slideButton;//滑动按钮
    private boolean currentState;//当前开关按钮的状态
    private float slideBtnWidth;


    public MyToggleButton2(Context context) {
        this(context, null);
    }

    public MyToggleButton2(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.d(TAG, "MyToggleButton");
        init(context, attrs);
    }

    /**
     * 从xml文件中获取属性资源
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyToggleButton);

        //取得本集合中共有多少个属性
        int indexCount = typedArray.getIndexCount();
        Logger.d(TAG, "--indexCount-" + indexCount);
        //遍历这些属性,拿到属性队形的id,然后根据id拿到对应的值
        for (int i = 0; i < indexCount; i++) {
            int taId = typedArray.getIndex(i);
            Logger.d(TAG, "--taId-" + taId);
            switch (taId) {
                case R.styleable.MyToggleButton_background_bitmap:
                    //背景图片
                    switchBackground = ((BitmapDrawable) typedArray.getDrawable(taId)).getBitmap();
                    break;
                case R.styleable.MyToggleButton_slide_button:
                    slideButton = ((BitmapDrawable) typedArray.getDrawable(taId)).getBitmap();
                    break;
                case R.styleable.MyToggleButton_current_state:
                    currentState = typedArray.getBoolean(taId, false);
                    break;
            }
        }

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
