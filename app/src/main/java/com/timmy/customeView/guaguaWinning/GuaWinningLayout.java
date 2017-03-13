package com.timmy.customeView.guaguaWinning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.timmy.R;
import com.timmy.library.util.Logger;

/**
 * Created by admin on 2017/3/2.
 * 刮刮乐效果:实现手指滑动刮开奖品
 * 1:界面采用两张图片,背景图是奖品详情图片,顶部盖一层灰色的图片
 * 2.监听手指滑动事件,当手指在灰色图片上滑动时刮开灰色图片
 * <p>
 * 实现:
 * 1.分成三层图片:最底层为红包图片,中间一层为灰色的覆盖图,顶部一层为画笔轨迹图片,
 * 2.根据画笔轨迹将中间一层和顶部一层采用交集绘制出来
 * 2.监听滑动事件,滑到滑动路径path
 * 3.根据Path绘制背景图和覆盖图的交集
 */
public class GuaWinningLayout extends View {

    private final String TAG = GuaWinningLayout.this.getClass().getSimpleName();
    private Bitmap redPackagerBitmap;
    private Paint paint;
    private float paintWidht = 50;
    private Path path;
    private Canvas mForeBitCanvas;
    private int downY;
    private int downX;
    private Bitmap foreBitmap;
    private boolean openWin;

    public GuaWinningLayout(Context context) {
        this(context, null);
    }

    public GuaWinningLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaWinningLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.获取背景图片
        redPackagerBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_red_package);
        //2.初始化画笔
        initPaint();
    }

    private void initPaint() {
        //路径
        path = new Path();
        //画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        //设置画笔透明
        paint.setAlpha(0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidht);
        //笔帽
        paint.setStrokeCap(Paint.Cap.ROUND);
        //线条交集样式
        paint.setStrokeJoin(Paint.Join.ROUND);
        //设置画笔交集样式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        Logger.d(TAG, "redPackagerBitmap widht:" + redPackagerBitmap.getWidth() + ",redPackagerBitmap height:" + redPackagerBitmap.getHeight());
        //根据背景图-创建同样大小的顶层图片
//        Bitmap foreBitmap = Bitmap.createBitmap(redPackagerBitmap,0,0,redPackagerBitmap.getWidth(),redPackagerBitmap.getHeight());
        foreBitmap = Bitmap.createBitmap(redPackagerBitmap.getWidth(), redPackagerBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //创建顶层画布--绘制灰色
        mForeBitCanvas = new Canvas(foreBitmap);
        mForeBitCanvas.drawColor(Color.GRAY);
    }

    private int offsetX, offsetY;//x,y方向路径的总长度

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                path.moveTo(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                //滑动一段,就绘制一段
                path.lineTo(event.getX(), event.getY());
                mForeBitCanvas.drawPath(path, paint);
                invalidate();

                int dx = (int) (event.getX() - downX);
                int dy = (int) (event.getY() - downY);
                offsetX += Math.abs(dx);
                offsetY += Math.abs(dy);
                Logger.d(TAG, "dx:" + dx + ",offsetX:" + offsetX + ",dy:" + dy + ",offsetY:" + offsetY);
                break;
            case MotionEvent.ACTION_UP:
                if (offsetX > redPackagerBitmap.getWidth() * 10
                        || offsetY > redPackagerBitmap.getWidth() * 10) {
                    openWin = true;
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Logger.d(TAG, "canvas widht:" + canvas.getWidth() + ",canvas height:" + canvas.getHeight());
        //画背景图
        canvas.drawBitmap(redPackagerBitmap, 0, 0, null);
        if (!openWin) {//画顶层图片
            canvas.drawBitmap(foreBitmap, 0, 0, null);
        }
    }
}
