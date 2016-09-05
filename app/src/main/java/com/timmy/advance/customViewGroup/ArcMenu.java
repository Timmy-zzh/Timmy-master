package com.timmy.advance.customViewGroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.timmy.R;
import com.timmy.library.util.Logger;

/**
 * 自定义圆形菜单ViewGroup,实现的效果为点击时圆形菜单分开收缩效果
 * 步骤:
 * 1.自定义属性,确定圆形菜单的位置和半径--在构造函数中获取
 * 2.onMeasure方法实现测量
 * 3.onLayout方法实现子类的布局
 * 4.动画的处理
 * 5.点击事件的处理
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener {

    private final java.lang.String TAG = ArcMenu.this.getClass().getSimpleName();
    private int radius = 100;
    private Position position = Position.LEFT_TOP;
    private View mButton;
    private Status currentStatus = Status.CLOSE;
    private OnItemMenuClickListener mListener;

    public enum Position {
        LEFT_TOP, RIGHT_TOP, RIGHT_BOTTOM, LEFT_BOTTOM;
    }

    public enum Status {
        OPEN, CLOSE;
    }

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Logger.d(TAG, "--ArcMenu--");
        //获取自定义属性值
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.ArcMenu_position:
                    int val = ta.getInt(attr, 0);
                    switch (val) {
                        case 0:
                            position = Position.LEFT_TOP;
                            break;
                        case 1:
                            position = Position.RIGHT_TOP;
                            break;
                        case 2:
                            position = Position.RIGHT_BOTTOM;
                            break;
                        case 3:
                            position = Position.LEFT_BOTTOM;
                            break;
                    }
                    break;
                case R.styleable.ArcMenu_arcRadius:
                    int defaultRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            radius, getResources().getDisplayMetrics());
                    radius = ta.getDimensionPixelSize(attr, defaultRadius);
                    Logger.d(TAG, "--radius--" + radius);
                    break;
            }
        }
        ta.recycle();
    }

    /**
     * 测量--子类测量方法onMeasure
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.d(TAG, "--onMeasure--");
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            //View的measure方法会调用onMeasure方法--但是MeasureSpec为什么要用UNSPECIFIED呢???
            getChildAt(i).measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.d(TAG, "--width--" + MeasureSpec.getSize(widthMeasureSpec) +
                "--height--" + MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * 布局--子类布局-根据需求,将第一个子类作为中间的点击进行布局
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Logger.d(TAG, "--onLayout--changed--" + changed);
        if (changed) {
            layoutButton();
            int childCount = getChildCount();
            for (int i = 0; i < childCount - 1; i++) {//从第二个子类获取
                View childView = getChildAt(i + 1);
                childView.setVisibility(View.GONE);
                int cl = (int) (radius * Math.sin(Math.PI / 2 / (childCount - 2) * i));
                int ct = (int) (radius * Math.cos(Math.PI / 2 / (childCount - 2) * i));
                //子类的测量宽高
                int cWidth = childView.getMeasuredWidth();
                int cHeight = childView.getMeasuredHeight();
                //右上,右下
                if (position == Position.RIGHT_TOP || position == Position.RIGHT_BOTTOM) {
                    cl = getMeasuredWidth() - cl - cWidth;
                }

                if (position == Position.LEFT_BOTTOM || position == Position.RIGHT_BOTTOM) {
                    ct = getMeasuredHeight() - ct - cHeight;
                }
                childView.layout(cl, ct, cl + cWidth, ct + cHeight);
            }
        }
    }

    /**
     * 第一个按钮的布局--就是第一个子类控件
     */
    private void layoutButton() {
        View cButton = getChildAt(0);
        cButton.setOnClickListener(this);
        //子类的测量宽高
        int cl = 0;
        int ct = 0;
        int cWidth = cButton.getMeasuredWidth();
        int cHeight = cButton.getMeasuredHeight();
        //整个控件的宽高getMeasuredWidth()
        switch (position) {
            case LEFT_TOP:
                cl = 0;
                ct = 0;
                break;
            case RIGHT_TOP:
                cl = getMeasuredWidth() - cWidth;
                ct = 0;
                break;
            case RIGHT_BOTTOM:
                cl = getMeasuredWidth() - cWidth;
                ct = getMeasuredHeight() - cHeight;
                break;
            case LEFT_BOTTOM:
                cl = 0;
                ct = getMeasuredHeight() - cHeight;
                break;
        }
        cButton.layout(cl, ct, cl + cWidth, ct + cHeight);
    }

    /**
     * 中间的按钮点击事件-->按钮旋转动画-->四周的子菜单项动画打开/关闭
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        mButton = findViewById(R.id.iv_center);
        if (mButton == null) {
            mButton = getChildAt(0);
        }
        rotateCenter(mButton, 0f, 270f, 3000);
        toggleMenus(3000);
    }

    /**
     * 中间按钮进行旋转动画
     *
     * @param view
     * @param fromDegree    开始角度
     * @param toDegree      目标角度
     * @param durationMills 动画时间
     */
    private void rotateCenter(View view, float fromDegree, float toDegree,
                              int durationMills) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegree, toDegree,
                Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(durationMills);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    /**
     * 四周的子菜单需要的操作:位移动画,旋转动画,状态改变,
     *
     * @param durationMills
     */
    private void toggleMenus(int durationMills) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(View.VISIBLE);
            int xFlag = 1;
            int yFlag = 1;
            if (position == Position.LEFT_TOP || position == Position.LEFT_BOTTOM)
                xFlag = -1;
            if (position == Position.LEFT_TOP || position == Position.RIGHT_TOP)
                yFlag = -1;

            int cl = (int) (radius * Math.sin(Math.PI / 2 / (childCount - 2) * i));
            int ct = (int) (radius * Math.cos(Math.PI / 2 / (childCount - 2) * i));

            AnimationSet animationSet = new AnimationSet(true);
            Animation translateAnimation = null;
            if (currentStatus == Status.CLOSE) {//to open-->位移动画
                //?有什么作用
                animationSet.setInterpolator(new OvershootInterpolator(2f));
                translateAnimation = new TranslateAnimation(xFlag * cl, 0, yFlag * ct, 0);
                Logger.d(TAG, "--xFlag * cl:" + xFlag * cl + "--yFlag * ct:" + yFlag * ct);
                childView.setClickable(true);
                childView.setFocusable(true);
            } else {//to close
                translateAnimation = new TranslateAnimation(0, xFlag * cl, 0, yFlag * ct);
                Logger.d(TAG, "--xFlag * cl:" + xFlag * cl + "--yFlag * ct:" + yFlag * ct);
                childView.setClickable(false);
                childView.setFocusable(false);
            }

            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (currentStatus == Status.CLOSE)
                        childView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            translateAnimation.setFillAfter(true);
            translateAnimation.setDuration(durationMills);
            //为动画设置延时
            translateAnimation.setStartOffset((i * 100) / (childCount - 1));
            //旋转动画
            RotateAnimation rotateAnimation = new RotateAnimation(0, 720,
                    Animation.RELATIVE_TO_SELF, 0.5F,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(durationMills);
            rotateAnimation.setFillAfter(true);

            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(rotateAnimation);

//            animationSet.setDuration(durationMills);
//            animationSet.setFillAfter(true);
            childView.startAnimation(animationSet);

            final int index = i;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //子菜单点击事件->子菜单做动画,状态改变
                    if (mListener != null) {
                        mListener.onItemMenuClick(childView, index);
                    }
                    changeStates();
                    menuItemAnim(index);
                }
            });
        }

        //中间按钮点击事件会将状态改变
        changeStates();
        Logger.d(TAG, "--status--" + currentStatus.toString());
    }

    /**
     * 子菜单点击时->动画;点击的子菜单放大,其他子菜单缩小
     *
     * @param index
     */
    private void menuItemAnim(int index) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View childView = getChildAt(i + 1);
            if (i == index) {
                childView.startAnimation(scaleBigAnim(300));
            } else {
                childView.startAnimation(scaleSmallAnim(300));
            }
            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    //放大-透明度
    private Animation scaleBigAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0f);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        animationSet.setFillAfter(true);
        animationSet.setDuration(duration);
        return animationSet;
    }

    //缩小
    private Animation scaleSmallAnim(int duration) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0f, 1.0f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(duration);
        return scaleAnimation;
    }

    private void changeStates() {
        currentStatus = (currentStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }

    public interface OnItemMenuClickListener {
        void onItemMenuClick(View view, int position);
    }

    public void setOnItemMenuClickListener(OnItemMenuClickListener listener) {
        this.mListener = listener;
    }
}
