package com.timmy.customeView.guaguaWinning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
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
    private float paintWidht = 10;
    private Path path;

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
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(paintWidht);
        paint.setColor(Color.GREEN);
        //笔帽
        paint.setStrokeCap(Paint.Cap.ROUND);
        //线条交集样式
        paint.setStrokeJoin(Paint.Join.ROUND);

        Logger.d(TAG, "redPackagerBitmap widht:" + redPackagerBitmap.getWidth() + ",redPackagerBitmap height:" + redPackagerBitmap.getHeight());
        //根据背景图

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Logger.d(TAG, "canvas widht:" + canvas.getWidth() + ",canvas height:" + canvas.getHeight());

        //画背景图
        canvas.drawBitmap(redPackagerBitmap, 0, 0, null);

        //画覆盖灰色图片
        Rect rect = new Rect(0,0,redPackagerBitmap.getWidth(),redPackagerBitmap.getHeight());
        canvas.drawRect(rect,paint);

    }



}
