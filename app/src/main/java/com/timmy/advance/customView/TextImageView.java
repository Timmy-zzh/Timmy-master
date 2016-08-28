package com.timmy.advance.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.timmy.R;
import com.timmy.library.util.Logger;

/**
 * 带文字的图片
 * 实现步骤:
 * 构造函数获取自定义属性
 * onMeasure测量
 * onDraw绘制
 */
public class TextImageView extends View {

    private final java.lang.String TAG = this.getClass().getSimpleName();
    private Bitmap mBitmap;
    private String titleText;
    private int titleColor;
    private int titleSize;
    private int imageScaleType;
    private Rect mTextBound;
    private Paint mPaint;
    private Rect mRect;
    int mWidth = 0;
    int mHeight = 0;

    public TextImageView(Context context) {
        this(context, null);
    }

    public TextImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextImageView, defStyleAttr, 0);
        int indexCount = ta.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.TextImageView_image:
                    //获取xml文件中设置的图片资源
                    Drawable drawable = ta.getDrawable(attr);
                    Logger.d(TAG, "TextImageView--drawable--" + drawable.toString());
                    //获取图片资源对应的id-->再使用BitmapFactory获取
                    int imageResourceId = ta.getResourceId(attr, 0);
                    Logger.d(TAG, "TextImageView--imageResourceId--" + imageResourceId);
                    mBitmap = BitmapFactory.decodeResource(getResources(), imageResourceId);
                    break;
                case R.styleable.TextImageView_titleText:
                    //图片下面展示的文字
                    titleText = ta.getString(attr);
                    Logger.d(TAG, "TextImageView--titleText--" + titleText);
                    break;
                case R.styleable.TextImageView_titleColor:
                    //文字的颜色
                    titleColor = ta.getColor(attr, Color.RED);
                    Logger.d(TAG, "TextImageView--titleColor--" + titleColor);
                    break;
                case R.styleable.TextImageView_titleSize:
                    //文字大小
                    int defaultSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
                    Logger.d(TAG, "TextImageView--defaultSize--" + defaultSize);
                    titleSize = ta.getDimensionPixelSize(attr, defaultSize);
                    Logger.d(TAG, "TextImageView--titleSize--" + titleSize);
                    break;
                case R.styleable.TextImageView_imageScaleType:
                    //图片的缩放方式
                    imageScaleType = ta.getInt(attr, 0);
                    Logger.d(TAG, "TextImageView--imageScaleType--" + imageScaleType);
                    break;
            }
        }
        //最后是释放资源
        ta.recycle();

        //初始化需要的画笔--设置画笔画文字的大小,和画文字内容的区域
        Logger.d(TAG, "TextImageView--initPaint--");
        mPaint = new Paint();
        mPaint.setTextSize(titleSize);
        //设置画文字的区域
        mTextBound = new Rect();
        //计算了绘制文字需要的内容
        mPaint.getTextBounds(titleText, 0, titleText.length(), mTextBound);

        //还需要创建一个区域用于绘制整个背景,图片和文字都放在里面
        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 设置宽度
         * 情况一:控件宽度为确定值 xxdp或match_parent-->宽度就是width
         * 情况二:控件宽度为warp_content包裹,如果文字的宽度比图片宽度要宽,这个时候使用文字宽度/反之使用图片宽度
         */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        Logger.d(TAG, "onMeasure--specSize--width--" + specSize);
        Logger.d(TAG, "onMeasure--getPaddingLeft()--" + getPaddingLeft());
        Logger.d(TAG, "onMeasure--getPaddingRight()--" + getPaddingRight());

        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else {
            //先分别获取图片和文字的宽度
            int imageWidth = mBitmap.getWidth() + getPaddingLeft() + getPaddingRight();
            Logger.d(TAG, "onMeasure--mBitmap.getWidth()--" + mBitmap.getWidth());
            int textWidth = mTextBound.width() + getPaddingLeft() + getPaddingRight();
            Logger.d(TAG, "onMeasure--mTextBound.width()--" + mTextBound.width());
            if (specMode == MeasureSpec.AT_MOST) {
                int maxWidth = Math.max(imageWidth, textWidth);
                //再求maxWidth和specSize的较小的数
                mWidth = Math.min(maxWidth, specSize);
            }
        }

        //设置高度--和宽度一样处理
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        Logger.d(TAG, "onMeasure--specSize--height--" + specSize);
        Logger.d(TAG, "onMeasure--getPaddingTop()--" + getPaddingTop());
        Logger.d(TAG, "onMeasure--getPaddingBottom()--" + getPaddingBottom());
        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        } else {
            //高度为图片和文字的和
            int viewHeight = mBitmap.getHeight() + mTextBound.height() +
                    getPaddingTop() + getPaddingBottom();
            Logger.d(TAG, "onMeasure--mBitmap.getHeight()--" + mBitmap.getHeight());
            Logger.d(TAG, "onMeasure--mTextBound.height()--" + mTextBound.height());
            if (specMode == MeasureSpec.AT_MOST) {
                //再求maxWidth和specSize的较小的数
                mHeight = Math.min(viewHeight, specSize);
            }
        }

        Logger.d(TAG, "onMeasure--mWidth--" + mWidth);
        Logger.d(TAG, "onMeasure--mHeight--" + mHeight);
        setMeasuredDimension(mWidth, mHeight);
    }


    /**
     * 绘制--需要绘制边框--再绘制图片(根据缩放方式绘制)-和文字
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制矩形边框
        mPaint.setStrokeWidth(4);//设置画笔的宽度--也就是边框的宽度
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔类型???
        mPaint.setColor(Color.GREEN);//设置画笔颜色
        Logger.d(TAG, "onDraw--getMeasuredWidth()--" + getMeasuredWidth());
        Logger.d(TAG, "onDraw--getMeasuredHeight()--" + getMeasuredHeight());
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        //绘制文字-->先判断文字的宽度和控件的宽度的大小
        mPaint.setColor(titleColor);
        mPaint.setStyle(Paint.Style.FILL);
        if (mTextBound.width() - getPaddingLeft() - getPaddingRight() > mWidth) {
            //大于,文字不全部显示出来,用三个点结尾
            TextPaint textPaint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(titleText, textPaint,
                    mWidth - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END).toString();
            Logger.d(TAG, "--msg-------" + msg);
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            //文字内容小于控件宽度,文字绘制居中
            int textLeft = mWidth / 2 - mTextBound.width() / 2;
            int textBottom = mHeight - getPaddingBottom();
            canvas.drawText(titleText, textLeft, textBottom, mPaint);
        }

        //绘制图片-->确定图片的绘制区域
        mRect.left = getPaddingLeft();
        mRect.top = getPaddingTop();
        mRect.right = mWidth - getPaddingRight();
        mRect.bottom = mHeight - getPaddingBottom() - mTextBound.height();

        if (imageScaleType == 0) {//fitXY图片铺满整个区域
            canvas.drawBitmap(mBitmap, null, mRect, mPaint);
        } else {
            //根据图片的宽高绘制
            mRect.left = mWidth / 2 - mBitmap.getWidth() / 2;
            mRect.top = (mHeight - mTextBound.height()) / 2 - mBitmap.getHeight() / 2;
            mRect.right = mWidth / 2 + mBitmap.getWidth() / 2;
            mRect.bottom = (mHeight - mTextBound.height()) / 2 + mBitmap.getHeight() / 2;
            canvas.drawBitmap(mBitmap, null, mRect, mPaint);
        }

    }
}
