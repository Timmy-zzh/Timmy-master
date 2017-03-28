package com.timmy.highUI.animatoion;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.library.util.DensityUtils;
import com.timmy.library.util.Logger;
import com.timmy.library.util.ScreenUtils;

/**
 * 属性动画
 */
public class PropertyAnimationActivity extends BaseActivity {

    private String TAG = this.getClass().getSimpleName();
    private ImageView imageView;
    private View point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        initToolBar();

        imageView = (ImageView) findViewById(R.id.image_view);
        point = findViewById(R.id.view_point);
    }

    public void animator() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Logger.d(TAG, "value:" + value);
            }
        });

        ObjectAnimator rotationX = ObjectAnimator
                .ofFloat(imageView, "rotationX", 0f, 360f);

        rotationX.setDuration(300);
        //重复次数
        rotationX.setRepeatCount(ValueAnimator.INFINITE);
        //重复执行方式
        rotationX.setRepeatMode(ValueAnimator.REVERSE);

        //设置插值器-->计算执行的百分比
//        rotationX.setInterpolator(new LinearInterpolator());
        rotationX.setInterpolator(new AccelerateDecelerateInterpolator());
        //设置估值器
        rotationX.setEvaluator(new IntEvaluator());
        rotationX.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                return null;
            }
        });
        rotationX.start();

        //设置动画更新监听
        rotationX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

        //设置动画监听
        rotationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void reset(View view) {
        imageView.clearAnimation();
    }

    //执行属性动画,旋转-一行代码搞定旋转动画
    public void playAnimator(View view) {
        ObjectAnimator
                .ofFloat(imageView, "rotationX", 0f, 360f)
                .setDuration(300)
                .start();
    }

    //想让控件同时执行 缩放和透明度变化的动画（scaleX ,scaleY,alpha）
    public void manyAnimator(View view) {

//        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "timmy", 1.0f, 0.1f);
        ValueAnimator animator = ValueAnimator.ofFloat( 1.0f, 0.1f);
        animator.setDuration(500);
        animator.start();

        //设置动画变化监听，回调方法里面可以获取到动画执行的到什么程度
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这个value值就是1.0～0.1变化的值
                float value = (float) animation.getAnimatedValue();
                imageView.setScaleX(value);
                imageView.setScaleY(value);
                imageView.setAlpha(value);
            }
        });
    }

    //想让控件同时执行 缩放和透明度变化的动画（scaleX ,scaleY,alpha）->采用另外一种方式
    public void manyAnimator2(View view) {
        PropertyValuesHolder scaleXPro = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0, 1.0f);
        PropertyValuesHolder scaleYPro = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0, 1.0f);
        PropertyValuesHolder alphaPro = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0, 1.0f);
        ObjectAnimator.ofPropertyValuesHolder(imageView, scaleXPro, scaleYPro, alphaPro).setDuration(1000).start();
    }

    //小球垂直降落
    public void vertical(View view) {
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int screenHeight = outMetrics.heightPixels;
        //小球距屏幕顶部的距离
        final int pointHeight = point.getTop();
//        final int pointHeight = point.getHeight();//获取的是小球本身的高度
        Logger.d("screenHeight:" + screenHeight + " pointHeight:" + pointHeight);
        int offset = screenHeight - pointHeight - 200;//降落距离
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, offset);
        valueAnimator.setTarget(point);
        valueAnimator.setDuration(2000).start();
        valueAnimator.setInterpolator(new BounceInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                long currentPlayTime = animation.getCurrentPlayTime();
//                Logger.d("getAnimatedValue():"+ animation.getAnimatedValue()+" currentPlayTime:"+currentPlayTime);
                //设置小球的y方向的位置-－？
                point.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
    }


    //抛物线
    public void curve(View view) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(2000);
        //设置object属性,和translateX等一样
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());

        //设置估值器
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                Logger.d("数率 fraction:" + fraction);
                PointF pointF = new PointF();
                //水平方向 s = vt
                pointF.x = 200 * (fraction * 3);
                //垂直方向 h = 0.5 * g * t*t
                pointF.y = (float) (0.5 * 200 * (fraction * 3) * (fraction * 3));
                return pointF;
            }
        });
        valueAnimator.start();

        //动画指定监听->根据估值器里面设置返回的值动态的设置小球的位置
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                point.setX(pointF.x);
                point.setY(pointF.y);
            }
        });
    }


    public void xmlAnimator(View view) {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.set_animator);
        animator.setTarget(imageView);
        animator.start();
    }
}
