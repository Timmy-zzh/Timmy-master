package com.timmy.customeView.letterNavigation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.R;
import com.timmy.library.util.Logger;


/**
 * Created by timmy1 on 17/2/12.
 * 右侧字母导航控件
 * 1.构造函数中处理paint和自定义属性信息
 * 2.onmeasure方法中进行测量
 * 3.ondraw方法中进行绘制，绘制单个的子控件
 * 4.点击事件和滑动事件处理
 * 5.处理导航字母的点击和滑动事件过程中需要的左边listview和中间弹窗的联系
 */
public class LetterNavigationView extends View implements LetterNavigationActivity.OnListViewScrollListener {

    private String TAG = "LetterNavigationView";
    private int mWidth = 120;
    private int mHeight = 600;
    //右边的所有文字
    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private int currentPosition = 0;//当前选择的文字位置
    private int eachLetterHeight;//每个字母占有的高度
    private Paint paint;
    private OnLetterUpdateListener mListener;

    public LetterNavigationView(Context context) {
        this(context, null);
    }

    public LetterNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widhtSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mWidth = widthMode == MeasureSpec.EXACTLY ? widhtSize : mWidth;
        mHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : mHeight;

        eachLetterHeight = mHeight / letters.length;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int size = letters.length;
        for (int i = 0; i < size; i++) {
            if (i == currentPosition) {//当前选中的字母
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.BLACK);
            }
            float x = (mWidth - paint.measureText(letters[i])) / 2;
            float y = (i + 1) * eachLetterHeight;
            canvas.drawText(letters[i], x, y, paint);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //1.先处理点击事件
        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(getResources().getColor(R.color.Gray_e));
                //消费点击事件
                float upy = event.getY();
                float rawY = event.getRawY();//相对于父容器的高度，即是距离RelativeLayout的顶部高度
                Logger.d(TAG, "getY:" + upy + ",getRawY:" + rawY);
                //根据y方向的位置，寻找到当前点击的位置是哪个字母
                currentPosition = (int) (upy / eachLetterHeight);
                if (mListener != null) {
                    mListener.letterUpdate(letters[currentPosition], currentPosition);
                }
                invalidate();
                return true;
            default:
                setBackgroundColor(Color.RED);
                float y = event.getY();
                //根据y方向的位置，寻找到当前点击的位置是哪个字母
                currentPosition = (int) (y / eachLetterHeight);
                if (mListener != null) {
                    mListener.letterUpdate(letters[currentPosition], currentPosition);
                }
                invalidate();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void updateLetrer(int position) {
        currentPosition = position;
        invalidate();
    }


    public interface OnLetterUpdateListener {
        void letterUpdate(String letter, int position);
    }

    public void setOnLetterUpdateListener(OnLetterUpdateListener listener) {
        this.mListener = listener;
    }

}
