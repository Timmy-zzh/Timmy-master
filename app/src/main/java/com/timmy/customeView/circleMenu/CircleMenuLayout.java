package com.timmy.customeView.circleMenu;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.library.util.Logger;

/**
 * 圆形菜单控件
 * 1.自定义属性: 圆形背景
 * 圆形菜单的背景色
 * 内园半径大小
 * <p>
 * 2.放置多个item进行圆形围绕
 * 3.点击事件
 * 4.使用adapter模式扩展不同item view样式
 */
public class CircleMenuLayout extends ViewGroup {

    private int mItemCount = 6;
    private int mRadius = 60;
    private int[] mItemImgs;//单个菜单icon
    private String[] mItemTexts;//单个菜单文本
    private OnMenuItemClickListener mMenuItemClickListener;
    private int screenWidth;
    private int mStartAngle;
    MenuAdapter mAdapter;

    public CircleMenuLayout(Context context) {
        super(context);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm1 = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm1.getDefaultDisplay();
        Point outSize = new Point();
        display.getSize(outSize);//不能省略,必须有
        //得到屏幕的宽度
        screenWidth = outSize.x;
        int screenHeight = outSize.y;//得到屏幕的高度
        Logger.d("screenWidth:" + screenWidth);
    }

//    public void setMenuItemIconsAndTexts(int[] images, String[] texts) {
//        this.mItemImgs = images;
//        this.mItemTexts = texts;
//        mItemCount = Math.min(images.length, texts.length);
//        //构建单个菜单,并添加到容器中
//        buildMenuItems();
//    }


    public void setAdapter(MenuAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    protected void onAttachedToWindow() {
        if (mAdapter != null) {
            buildMenuItems();
        }
        super.onAttachedToWindow();
    }

    /**
     * 1.创建item菜单控件
     * 2.为item菜单控件填充数据
     * 3.添加addView()
     */
    private void buildMenuItems() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View itemView = mAdapter.getView(i, null, this);
            mAdapter.setItemView(itemView, i);
            final int position = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMenuItemClickListener != null) {
                        mMenuItemClickListener.onMenuItemClick(v, position);
                    }
                }
            });
//            initMenuItem(itemView, i);
            addView(itemView);
        }
    }


    //使用Inflate创建Menu控件,并添加点击事件
//    private View inflateMenuView(final int i) {
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_text_image, this, false);
//        view.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mMenuItemClickListener != null) {
//                    mMenuItemClickListener.onMenuItemClick(v, i);
//                }
//            }
//        });
//        return view;
//    }

//    private void initMenuItem(View itemView, int index) {
//        ImageView imageView = itemView.findViewById(R.id.iv_image);
//        TextView textView = itemView.findViewById(R.id.tv_text);
//        imageView.setImageResource(mItemImgs[index]);
//        textView.setText(mItemTexts[index]);
//    }

    /**
     * 测量:需要测量自身和childView
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureSelf(widthMeasureSpec, heightMeasureSpec);
        measureChildViews();
    }

    private void measureChildViews() {
        //获取半径
        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight()) / 2;
        //childCount
        mItemCount = getChildCount();
        //确定childView的mode和size
        int childSize = mRadius / 2;
        int childMode = MeasureSpec.EXACTLY;
        //遍历测量
        for (int i = 0; i < mItemCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) {
                continue;
            }
            int childeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize, childMode);
            childView.measure(childeMeasureSpec, childeMeasureSpec);
        }
    }

    //容器为宽高相等
    private void measureSelf(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth;
        int measureHeight;
        //获取mode和size
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //如果有一个为固定值
        if (widthMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.EXACTLY) {
            measureWidth = measureHeight = Math.min(widthSize, heightSize);
        } else {
            //考虑背景图
            measureWidth = getSuggestedMinimumWidth();
            //未设置背景图,则设置为屏幕宽高的默认值
            measureWidth = measureWidth == 0 ? screenWidth : measureWidth;

            //高度
            measureHeight = getSuggestedMinimumHeight();
            measureHeight = measureHeight == 0 ? screenWidth : measureWidth;
        }
        setMeasuredDimension(measureWidth, measureHeight);
    }

    /**
     * ChildView的摆放: 圆环排放位置的核心算法
     * 随着旋转角度,摆放位置变换
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left;
        int top;
        int right;
        int bottom;
        int childCount = getChildCount();
        //获取单个childView的大小
        int childSize = mRadius / 2;
        //childView到Menu中心的距离
        float distanceFromCenter = mRadius - childSize / 2;
        //每个Item占用的角度范围
        float angleDelay = 360 / childCount;

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) {
                continue;
            }
            mStartAngle %= 360;
            left = (int) (mRadius + Math.round(distanceFromCenter * Math.cos(Math.toRadians(mStartAngle))) - childSize / 2);
            top = (int) (mRadius + Math.round(distanceFromCenter * Math.sin(Math.toRadians(mStartAngle))) - childSize / 2);
            childView.layout(left, top, left + childSize, top + childSize);
            //叠加
            mStartAngle += angleDelay;
        }
    }


    public interface OnMenuItemClickListener {
        void onMenuItemClick(View v, int position);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mMenuItemClickListener = listener;
    }
}
