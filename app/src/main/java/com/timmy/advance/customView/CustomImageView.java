package com.timmy.advance.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.timmy.R;

/**
 * 带圆角的自定义ImageView--实现效果,通过xml文件设置属性,控制图片的类型
 * 类型包括: 正方形图片,圆形图片,圆角图片
 * 如果是圆形或者圆角,再设置圆角半径
 * 实现步骤:
 * 1.自定义属性
 * 2.构造函数中获取自定义属性
 * 3.onMeasure
 * 4.onDraw,最重要的绘制处理
 */
public class CustomImageView extends View {

    private static final int IMAGE_TYPE_SQUARE = 0;//正方形图片
    private static final int IMAGE_TYPE_CIRCLE = 1;//圆形图片
    private static final int IMAGE_TYPE_ROUND = 2;//圆角图片
    private Rect mRect;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;
    private int imageType;
    private int mRadius;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);
        int indexCount = ta.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.CustomImageView_image:
                    mBitmap = BitmapFactory.decodeResource(getResources(), ta.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_imageType:
                    imageType = ta.getInt(attr, IMAGE_TYPE_SQUARE);
                    break;
                case R.styleable.CustomImageView_roundRadius:
                    int defaultRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            4, getResources().getDisplayMetrics());
                    mRadius = ta.getDimensionPixelSize(attr, defaultRadius);
                    break;
            }
        }
        ta.recycle();
        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                int imageWidth = mBitmap.getWidth() + getPaddingRight() + getPaddingLeft();
                mWidth = Math.min(imageWidth, specSize);
            }
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                int imageHeight = mBitmap.getHeight() + getPaddingBottom() + getPaddingTop();
                mHeight = Math.min(imageHeight, specSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (imageType == IMAGE_TYPE_SQUARE) {

            canvas.drawBitmap(mBitmap, 0, 0, null);

        } else if (imageType == IMAGE_TYPE_CIRCLE) {
            //圆形图片,需要长宽一样,-->并进行压缩
            int min = Math.min(mWidth, mHeight);
            mBitmap = Bitmap.createScaledBitmap(mBitmap, min, min, false);

            Bitmap bitmap = createCircleBitmap(mBitmap, min);

            canvas.drawBitmap(bitmap, 0, 0, null);

        } else if (imageType == IMAGE_TYPE_ROUND) {
            //圆角图片
            canvas.drawBitmap(createRoundCornerBitmpa(mBitmap), 0, 0, null);
        }
    }

    private Bitmap createRoundCornerBitmpa(Bitmap source) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);

        RectF rect = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    private Bitmap createCircleBitmap(Bitmap source, int min) {
        //新建一个画布和图片-->在上面先绘制出需要的图片的圆形区域,在再上面绘制图片
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 先绘制一个圆
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);

        //设置画笔使用SRC_IN
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        /**
         * 再绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
