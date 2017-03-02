package com.timmy.customeView.myIndicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timmy.library.util.Logger;

/**
 * Created by admin on 2017/3/1.
 * 自定自己的ViewPager指示器
 * 1.指示器的文本item个数超过一屏时可以左右滑动
 * 2.指示器与下面的ViewPager进行联动
 * 3.指示器文本和下面的横线标识随着ViewPager改变（文本颜色和下面横线的位置）
 * 4.下面ViewPager滑动时，上面的指示器滑动到中间位置
 * <p>
 * 处理：
 * 1.指示器继承自HorizaontalScrollView，第一层子控件为一个水平的LinearLayout，然后往里面添加TextView文本
 * （这里可以使用xml文件导入，包含两部分-文本控件LinearLayout和下面的横线滑动指示器LinearLayout）
 * 2.单个指示器TextView文本设置点击事件，在onClick事件中设置ViewPager.setCurrentItem()指定位置
 * 3.持有ViewPager的应用，然后设置ViewPager的滑动监听,在onScroll方法中设置指示器的位置和背景颜色等处理
 */
public class MyIndicatorLayout extends HorizontalScrollView {

    private String TAG = "MyIndicatorLayout";
    private LinearLayout mIndicatorLayout;//指示器文本容器
    private ViewPager mViewPager;
    private int defaultColor = Color.BLACK, selectedColor = Color.RED;
    private int currentPosition = 0;
    private int mIndicatorHeight = 5;//指示符高度
    private Paint paint;
    private int left = 0;
    private int mPreWidth;
    private int childCount;

    public MyIndicatorLayout(Context context) {
        this(context, null);
    }

    public MyIndicatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyIndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);//隐藏滚动条
        mIndicatorLayout = new LinearLayout(context);
        mIndicatorLayout.setOrientation(LinearLayout.HORIZONTAL);

        addView(mIndicatorLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
//        paint.setStrokeWidth();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Logger.d(TAG,"onSizeChanged");
        childCount = mIndicatorLayout.getChildCount();
        mPreWidth = getMeasuredWidth() / childCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.d(TAG,"onMeasure");

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画出文本下面的横线指示符
        int top = getHeight() - mIndicatorHeight;
        int right = left + mPreWidth;
        int bottom = getHeight();
        Logger.d(TAG, "left:" + left + ",top:" + top + ",right:" + right + ",right:" + right+",preWidth:"+mPreWidth);
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawRect(rect, paint);

    }

    public void setViewPager(ViewPager viewPager) {
        if (mViewPager == viewPager) {
            return;
        }
        mViewPager = viewPager;
        PagerAdapter pagerAdapter = mViewPager.getAdapter();
        if (pagerAdapter == null) {
            throw new IllegalArgumentException("ViewPager must setAdapter first");
        }

        int itemCount = pagerAdapter.getCount();
        mIndicatorLayout.removeAllViews();
        for (int i = 0; i < itemCount; i++) {
            String itemTitle = (String) pagerAdapter.getPageTitle(i);
            //往指示器容器中添加view
            Logger.d(TAG, "title:" + itemTitle);
            TextView tabTextView = getItemTabTextView(itemTitle, i);
            mIndicatorLayout.addView(tabTextView);
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Logger.d(TAG, "onPageScrolled--position:" + position + ",positionOffset:" + positionOffset + ",positionOffsetPixels:" + positionOffsetPixels);
                //根据ViewPager滑动的位置,确定指示符左侧的位置
                setIndicatorViewPosition(position, positionOffset);

            }

            @Override
            public void onPageSelected(int position) {
                Logger.d(TAG, "onPageSelected--position:" + position);
                currentPosition = position;
                setSelectedTextColor(position);
                //在ViewPager滑动到摸个页面时，顶部的HorizontalScrollView也要滑动到可以查看的位置
//                smoothScrollTo();
                scrollToSelectedIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIndicatorViewPosition(int position, float positionOffset) {
        View childView = mIndicatorLayout.getChildAt(position);
        left = (int) (childView.getLeft() + childView.getWidth() * positionOffset);
        invalidate();
    }

    private void scrollToSelectedIndicator(int position) {
        View childView = mIndicatorLayout.getChildAt(position);
        int left = childView.getLeft();//子控件距离ScrollView左边界的距离
        int scrollPos = left - (getWidth() - childView.getWidth()) / 2;
        Logger.d(TAG, "left:" + left + ",scrollPos:" + scrollPos);
        smoothScrollTo(scrollPos, 0);
    }

    private void setSelectedTextColor(int position) {
        int childCount = mIndicatorLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            TextView childView = (TextView) mIndicatorLayout.getChildAt(i);
            if (i != position) {
                childView.setTextColor(defaultColor);
            } else {
                childView.setTextColor(selectedColor);
            }
        }
    }

    private TextView getItemTabTextView(String title, int i) {
        TextView textView = new TextView(getContext());
        textView.setTextColor(defaultColor);
        if (i == currentPosition) {
            textView.setTextColor(selectedColor);
        }
        textView.setGravity(Gravity.CENTER);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setPadding(20, 10, 20, 10);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setOnClickListener(new TabTextClickListener(i));
        return textView;
    }

    class TabTextClickListener implements OnClickListener {

        private final int index;

        public TabTextClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(index);
        }
    }

}
