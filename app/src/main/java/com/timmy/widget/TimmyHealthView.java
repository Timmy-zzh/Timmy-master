package com.timmy.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.timmy.R;
import com.timmy.library.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class TimmyHealthView extends View {

    private int textColor;//文字颜色
    private int lineColor;//线颜色
    private int widthBg, heightBg;//背景宽高
    private int radiusBg;
    //路径
    private Path pathBg;
    //背景画笔
    private Paint backgroundPaint;
    //园画笔
    private Paint arcPaint;
    private Paint textPaint;
    private Paint linePaint;
    private Path linePath;
    private DashPathEffect effects;
    private Paint rectPaint;
    private Path rectPath;
    //底部波纹
    private Paint weavPaint;
    private Path weavPath;
    private AnimatorSet animSet;
    private RectF arcRect;
    private float arcNum;
    private int walkNum;
    private int rankNum;
    private int averageSize;
    private String myaverageTxt;
    private int rectSize;
    private int rectAgHeight;
    private int mySize, rank;

    public TimmyHealthView(Context context) {
        this(context, null);
    }

    public TimmyHealthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimmyHealthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
        init();
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimmyHealthView, defStyleAttr, 0);
        //获取属性的个数
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.TimmyHealthView_titleColor:
                    textColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.TimmyHealthView_lineColor:
                    lineColor = array.getColor(attr, Color.BLACK);
                    break;
            }
        }
        array.recycle();//资源回收

        Logger.d("1111");
    }

    /**
     * 初始化操作
     */
    private void init() {
        pathBg = new Path();
        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);//抗锯齿
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePath = new Path();
        effects = new DashPathEffect(new float[]{5, 5}, 1);
        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPath = new Path();
        weavPaint = new Paint();
        weavPaint.setAntiAlias(true);
        weavPath = new Path();
        animSet = new AnimatorSet();
        Logger.d("222");
    }

    /**
     * 重写onMesure方法确定view大小--测量控件的大小
     * <p>
     * MeasureSpec的specMode模式一共有三种：
     * MeasureSpec.EXACTLY：父视图希望子视图的大小是specSize中指定的大小；一般是设置了明确的值或者是MATCH_PARENT
     * MeasureSpec.AT_MOST：子视图的大小最多是specSize中的大小；表示子布局限制在一个最大值内，一般为WARP_CONTENT
     * MeasureSpec.UNSPECIFIED：父视图不对子视图施加任何限制，子视图可以得到任意想要的大小；表示子布局想要多大就多大。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        /**
         * 如果布局里面是固定值,则取该值
         * 如果设置的是match_patent,则取宽高的部分
         */
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize * 1 / 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize * 3 / 4;
        }

        widthBg = width;
        heightBg = height;
        setMeasuredDimension(width, height);
        startAnim();

        Logger.d("3333");
    }

    private void startAnim() {
        //步数动画的实现
        ValueAnimator walkAnimator = ValueAnimator.ofInt(0, mySize);
        walkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                walkNum = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        //排名动画的实现
        ValueAnimator rankAnimator = ValueAnimator.ofInt(0, rank);
        rankAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rankNum = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        double size = mySize;
        double avgSize = averageSize;
        if (size > avgSize) {
            size = avgSize;
        }
        //圆弧动画的实现
        ValueAnimator arcAnimator = ValueAnimator.ofFloat(0, (float) (size / avgSize * 300));
        arcAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                arcNum = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animSet.setDuration(3000);
        animSet.playTogether(walkAnimator, rankAnimator, arcAnimator);
        animSet.start();
    }



    public static List<Integer> sizes = new ArrayList<>();

    /**
     * 重写onDraw方法进行绘画--使用贝塞尔曲线绘制圆弧
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 绘制原点为左上角(0,0)
         * moveTo(x,y):不会进行绘制，只用于移动移动画笔，确定起点坐标，与其他方法配合使用；
         lineTo(x,y):一阶贝塞尔曲线，坐标为终点坐标，配合moveTo方法用于进行直线绘制；
         quadTo (x1,y1,x2,y2):二阶贝塞尔曲线，(x1,y1) 为控制点，(x2,y2)为结束点，用于绘制圆滑的曲线；
         */
        //绘制最底层的背景
        radiusBg = widthBg / 20;
        pathBg.moveTo(0, heightBg);//起点为左下角
        pathBg.lineTo(0, radiusBg);//直线画到左上角
        //二次贝塞尔
        pathBg.quadTo(0, 0, radiusBg, 0);

        pathBg.lineTo(widthBg - radiusBg, 0);//右上角
        pathBg.quadTo(widthBg, 0, widthBg, radiusBg);

        pathBg.lineTo(widthBg, heightBg);//右下角
        pathBg.lineTo(0, heightBg);

        backgroundPaint.setColor(Color.WHITE);//背景色为白色
        //在画布上绘制出来
        canvas.drawPath(pathBg, backgroundPaint);

        /**
         * 绘制圆弧先确定圆弧的范围，传入的四个参数就是圆弧所在圆的外接矩形的坐标。canvas.drawArc的五个参数依次是圆弧范围；
         * 开始的角度；圆弧的角度；第四个为True时，在绘制圆弧时会将圆心包括在内，通常用来绘制扇形，我们这里选false；圆弧的画笔
         */
        //绘制圆弧
        arcPaint.setStrokeWidth(widthBg / 20);
        //设置空心
        arcPaint.setStyle(Paint.Style.STROKE);
        //防抖动
        arcPaint.setDither(true);
        //链接处为圆弧
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        //画笔的笔触为圆角
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setColor(lineColor);
        //圆弧范围
        arcRect = new RectF(widthBg * 1 / 4, widthBg * 1 / 4, widthBg * 3 / 4, widthBg * 3 / 4);
        //绘制背景大圆弧
        canvas.drawArc(arcRect, 120, 300, false, arcPaint);

        arcPaint.setColor(textColor);
        //绘制分数小圆弧
        canvas.drawArc(arcRect, 120, arcNum, false, arcPaint);

        //绘制圆圈内的数字
        textPaint.setColor(textColor);
        textPaint.setTextSize(widthBg / 10);
        //绘制步数
        canvas.drawText(String.valueOf(walkNum), widthBg * 3 / 8, widthBg * 1 / 2 + 20, textPaint);
        //绘制排名
        textPaint.setTextSize(widthBg / 15);
        canvas.drawText(String.valueOf(rankNum), widthBg * 1 / 2 - 15, widthBg * 3 / 4 + 10, textPaint);

        //绘制其他文字
        textPaint.setColor(lineColor);
        textPaint.setTextSize(widthBg / 25);
        canvas.drawText("截止13:45已走", widthBg * 3 / 8 - 10, widthBg * 5 / 12 - 10, textPaint);
        canvas.drawText("好友平均2781步", widthBg * 3 / 8 - 10, widthBg * 2 / 3 - 20, textPaint);
        canvas.drawText("第", widthBg * 1 / 2 - 50, widthBg * 3 / 4 + 10, textPaint);
        canvas.drawText("名", widthBg * 1 / 2 + 30, widthBg * 3 / 4 + 10, textPaint);

        //绘制圆圈外的文字
        canvas.drawText("最近7天", widthBg * 1 / 15, widthBg, textPaint);
        myaverageTxt = String.valueOf(averageSize);
        canvas.drawText("平均", widthBg * 10 / 15 - 15, widthBg, textPaint);
        canvas.drawText(myaverageTxt, widthBg * 11 / 15, widthBg, textPaint);
        canvas.drawText("步/天", widthBg * 12 / 15 + 20, widthBg, textPaint);

        //绘制虚线
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        linePaint.setColor(lineColor);
        linePath.moveTo(widthBg * 1 / 15, widthBg + 80);
        linePath.lineTo(widthBg * 14 / 15, widthBg + 80);
        linePaint.setPathEffect(effects);
        canvas.drawPath(linePath, linePaint);


        rectSize = widthBg / 12;
        rectAgHeight = widthBg / 10;
        //绘制虚线上的圆角竖线
        sizes.add(1234);
        sizes.add(2234);
        sizes.add(4234);
        sizes.add(6234);
        sizes.add(3834);
        sizes.add(7234);
        sizes.add(5436);
        for (int i = 1; i < sizes.size(); i++) {
            rectPaint.setStrokeWidth(widthBg / 25);
            rectPaint.setStyle(Paint.Style.STROKE);
            rectPaint.setStrokeJoin(Paint.Join.ROUND);
            rectPaint.setStrokeCap(Paint.Cap.ROUND);
            float startHeight = widthBg + 90 + rectAgHeight;
            rectPath.moveTo(rectSize, startHeight);
            double percentage = Double.valueOf(sizes.get(i)) / Double.valueOf(averageSize);
            double height = percentage * rectAgHeight;
            rectPath.lineTo(rectSize, (float) (startHeight - height));
            rectPaint.setColor(textColor);
            canvas.drawPath(rectPath, rectPaint);
            //绘制下方的文字
            textPaint.setColor(lineColor);
            canvas.drawText("0" + (i + 1) + "日", rectSize - 25, startHeight + 50, textPaint);
            rectSize += widthBg / 7;
        }

        /**
         * cubicTo(x1, y1, x2, y2, x3, y3)：三阶贝塞尔曲线， (x1,y1) 为控制点，(x2,y2)为控制点，(x3,y3) 为结束点，用于绘制复杂的曲线。
         */
        //绘制底部波纹
        weavPaint.setColor(textColor);
        weavPath.moveTo(0, heightBg);
        weavPath.lineTo(0, heightBg * 10 / 12);
        weavPath.cubicTo(widthBg * 1 / 10, heightBg * 10 / 12, widthBg * 3 / 10, heightBg * 11 / 12, widthBg, heightBg * 10 / 12);
        weavPath.lineTo(widthBg, heightBg);
        weavPath.lineTo(0, heightBg);
        canvas.drawPath(weavPath, weavPaint);

        //绘制底部文字
        weavPaint.setColor(Color.WHITE);
        weavPaint.setTextSize(widthBg / 20);
        canvas.drawText("成绩不错,继续努力哟!", widthBg * 1 / 10 - 20, heightBg * 11 / 12 + 50, weavPaint);

        Logger.d("4444");

    }


    public void setMySize(int mySize) {
        this.mySize = mySize;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setAverageSize(int averageSize) {
        this.averageSize = averageSize;
    }

    public void reSet(int mysize, int myrank, int myaverageSize) {
        walkNum = 0;
        arcNum = 0;
        rankNum = 0;
        mySize = mysize;
        rank = myrank;
        averageSize = myaverageSize;
        startAnim();
    }
}
