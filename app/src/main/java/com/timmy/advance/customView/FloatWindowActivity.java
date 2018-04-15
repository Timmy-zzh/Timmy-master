package com.timmy.advance.customView;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.library.util.Logger;
import com.timmy.library.util.ScreenUtils;
import com.timmy.base.BaseActivity;
import com.timmy.highUI.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.timmy.highUI.recyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FloatWindowActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener {

    private static final java.lang.String TAG = FloatWindowActivity.class.getSimpleName();
    private int lastX;
    private int lastY;
    int left;
    int top;
    int right;
    int bottom;
    private boolean isMoved;

    @BindView(R.id.rv_recycleView)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerViewAdapter adapter;
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);
        ButterKnife.bind(this);
        initToolBar();

        ImageView floatWindow = (ImageView) findViewById(R.id.float_window);
//        floatWindow.setOnTouchListener(this);
//        floatWindow.setOnClickListener(this);
        initView();
        initData();
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new SimpleAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    private class SimpleAdapter extends BaseRecyclerViewAdapter<String> {

        public SimpleAdapter(Context context) {
            super(context);
        }

        @Override
        protected int inflaterItemLayout(int viewType) {
            return R.layout.item_tab_pager;
        }

        @Override
        protected void bindData(BaseViewHolder holder, int position, String s) {
            holder.setText(R.id.tv_content, s);
            holder.setVisibility(R.id.tv_type, View.GONE);
        }


        @Override
        protected void onItemClickListener(View itemView, int position, String s) {

        }
    }

    private void initData() {
        mData.add("仿腾讯List滑动删除");
        mData.add("使用Activity作为Dialog来展示");
        mData.add("高仿win8效果的界面展示");
        mData.add("Android自定义ViewGroup实现竖向引导界面");
        mData.add("自定义View(一)--开关按钮");
        mData.add("自定义View(二)--水波纹效果");
        mData.add("自定义ViewGroup(三)--实现ViewPager效果");
        mData.add("自定义View(四)--图片适配");
        mData.add("ViewPager指示器");
        mData.add("城市列表选择");
        mData.add("城市列表选择--带热门城市");
        mData.add("自定义View(五)--随机数验证码");
        mData.add("自定义View(六)--带文字的图片");
        mData.add("自定义View(七)--圆环交替");
        mData.add("自定义View(八)--音量控件");
        mData.add("自定义View(九)--带圆角ImageView");
        mData.add("自定义ViewGroup--圆形菜单");
        mData.add("自定义ViewGroup--反弹ScrollView");
        mData.add("自定义ViewPager--不一样的ViewPager滑动效果");
        mData.add("自定义ViewGroup--应用手势锁");
        mData.add("Android属性动画");
        mData.add("自定义动画指示器-RubberIndicatior");
        mData.add("自定义ViewGroup--流式布局");
        mData.add("自定义ScrollView--显示完成内部的ItemView");
        mData.add("从源码分析事件分发机制");
        mData.add("自定义View--悬浮窗");


        adapter.setData(mData);
    }


    /**
     * touch事件处理,实现效果为控件随着手指滑动而滑动
     * 1.获取初始手指按下的位置(为距离屏幕原点的位置)
     * 2.MOVE事件过程中,计算偏移量,和边界处理,根据偏移量从新设置控件的位置
     * 3.UP事件时,根据当前的位置判断要偏移的终点位置
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                Logger.d(TAG, "lastX:" + lastX + "--lastY:" + lastY);
                isMoved = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //偏移量
                int dx = (int) (event.getRawX() - lastX);
                int dy = (int) (event.getRawY() - lastY);
                Logger.d(TAG, "getRawX:" + event.getRawX() + "--getRawY:" + event.getRawY() + "--dx:" + dx + "--dy:" + dy);
                left = v.getLeft() + dx;
                top = v.getTop() + dy;
                right = v.getRight() + dx;
                bottom = v.getBottom() + dy;
                Logger.d(TAG, "left:" + left + "--top:" + top + "--right:" + right + "--bottom:" + bottom);
                if (left < 0) {
                    left = 0;
                    right = left + v.getWidth();
                }
                if (top < ScreenUtils.getStatusHeight(this)) {
                    top = 150;
                    bottom = top + v.getHeight();
                }
                if (right > ScreenUtils.getScreenWidth(this)) {
                    right = ScreenUtils.getScreenWidth(this);
                    left = right - v.getWidth();
                }
                if (bottom > ScreenUtils.getScreenHeight(this)) {
                    bottom = ScreenUtils.getScreenHeight(this);
                    top = bottom - v.getHeight();
                }

                v.layout(left, top, right, bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();

                isMoved = true;
                break;
            case MotionEvent.ACTION_UP:
                if (lastX > ScreenUtils.getScreenWidth(this) / 2) {
                    right = ScreenUtils.getScreenWidth(this);
                    left = right - v.getWidth();
                } else {
                    left = 0;
                    right = left + v.getWidth();
                }
                v.layout(left, top, right, bottom);
                if (isMoved) {
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
//        Toast.toastShort("点击事件");
        android.widget.Toast.makeText(this, "点击事件", android.widget.Toast.LENGTH_SHORT).show();
    }
}
