package com.timmy.advance.screenAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.timmy.library.util.Logger;

/**
 * 适配屏幕的自定义ImageView,只要知道图片的宽高比,通过计算处理,展示的图片效果不出现拉伸变形.
 * 处理步骤:在测量方法onMeasure方法中根据已知的宽度或高度和设备api,通过公式求出我们需要的宽高
 */
public class AdapterImagerView extends ImageView {


    private final java.lang.String TAG = this.getClass().getSimpleName();
    private float mRatio;

    public AdapterImagerView(Context context) {
        super(context);
    }

    public AdapterImagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdapterImagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Logger.d(TAG, "--onMeasure--widthSize--" + widthSize + "--heightSize--" + heightSize);
        //如果宽度确定,高度为包裹,且知道图片宽高比-->计算出高度的精确值
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY &&
                mRatio != 0.0f) {
            heightSize = (int) (widthSize / mRatio + 0.5f);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        } else if (heightMode == MeasureSpec.EXACTLY && widthMode != MeasureSpec.EXACTLY &&
                mRatio != 0.0f) {
            widthSize = (int) (heightSize * mRatio + 0.5f);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setRatio(float ratio) {
        this.mRatio = ratio;
    }
}
