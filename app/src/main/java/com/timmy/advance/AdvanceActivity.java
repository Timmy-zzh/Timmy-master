package com.timmy.advance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.timmy.R;
import com.timmy.advance.animation.RubberIndicatiorActivity;
import com.timmy.advance.citySelect.CitySelectActivity;
import com.timmy.advance.citySelect.HotCityActivity;
import com.timmy.advance.customView.CustomImageViewActicity;
import com.timmy.advance.customView.FloatWindowActivity;
import com.timmy.advance.customView.MyToggleButtonActivity;
import com.timmy.advance.customView.RingViewActivity;
import com.timmy.advance.customView.TextImageViewActivity;
import com.timmy.advance.customView.VerificationCodeActivity;
import com.timmy.advance.customView.VolumeViewActivity;
import com.timmy.advance.customView.XiuViewActivity;
import com.timmy.advance.customViewGroup.AdapterScrollViewActivity;
import com.timmy.advance.customViewGroup.ArcMenuActivity;
import com.timmy.advance.customViewGroup.CustomViewPagerActivity;
import com.timmy.advance.customViewGroup.DragViewActivity;
import com.timmy.advance.customViewGroup.FlowLayoutActivity;
import com.timmy.advance.customViewGroup.GestureLockViewActivity;
import com.timmy.highUI.stretchList.ReboundScrollViewActivity;
import com.timmy.highUI.dialog.DialogActivity2;
import com.timmy.highUI.motionEvent.MotionEventActivity;
import com.timmy.advance.screenAdapter.ImageAdapterActivity;
import com.timmy.advance.slideList.FollowSlideListActivity;
import com.timmy.advance.viewpager.AnimationViewPagerActivity;
import com.timmy.advance.viewpager.ViewPagerIndicatiorActivity;
import com.timmy.advance.win8metro.FollowWin8Activity;
import com.timmy.base.BaseActivity;
import com.timmy.highUI.recyclerview.adapter.BaseRecyclerViewAdapter;
import com.timmy.highUI.recyclerview.adapter.BaseViewHolder;
import com.timmy.advance.animation.AnimationActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvanceActivity extends BaseActivity {

    @BindView(R.id.rv_recycleView)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerViewAdapter adapter;
    private List<String> mData = new ArrayList<>();
//    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);
        ButterKnife.bind(this);
//        mContext = this;
        initToolBar();
        initView();
        initData();
    }

    private void initView() {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new AdvanceAdapter(this);
        mRecyclerView.setAdapter(adapter);
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
        mData.add("自定义ViewGroup--可拖拽控件");
        mData.add("3秒倒计时广告页面");
        mData.add("自定义控件--支付宝咻一咻");


        adapter.setData(mData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_advance, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_dialog) {
            startActivity(new Intent(this, DialogActivity2.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class AdvanceAdapter extends BaseRecyclerViewAdapter<String> {
        public AdvanceAdapter(Context context) {
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
            switch (position) {
                case 0:
                    AdvanceActivity.this.openActivity(FollowSlideListActivity.class);
                    break;
                case 1:
                    AdvanceActivity.this.openActivity(DialogActivity2.class);
                    break;
                case 2:
                    AdvanceActivity.this.openActivity(FollowWin8Activity.class);
                    break;
                case 3:
//                    AdvanceActivity.this.openActivity(com.timmy.advance.scrollVertical.VerticalScrollActivity.class);
                    break;
                case 4:
                    AdvanceActivity.this.openActivity(MyToggleButtonActivity.class);
                    break;
                case 5:
//                    AdvanceActivity.this.openActivity(com.timmy.advance.waterRipple.WaterRippleActivity.class);
                    break;
                case 6:
                    AdvanceActivity.this.openActivity(CustomViewPagerActivity.class);
                    break;
                case 7:
                    AdvanceActivity.this.openActivity(ImageAdapterActivity.class);
                    break;
                case 8:
                    AdvanceActivity.this.openActivity(ViewPagerIndicatiorActivity.class);
                    break;
                case 9:
                    AdvanceActivity.this.openActivity(CitySelectActivity.class);
                    break;
                case 10:
                    AdvanceActivity.this.openActivity(HotCityActivity.class);
                    break;
                case 11:
                    AdvanceActivity.this.openActivity(VerificationCodeActivity.class);
                    break;
                case 12:
                    AdvanceActivity.this.openActivity(TextImageViewActivity.class);
                    break;
                case 13:
                    AdvanceActivity.this.openActivity(RingViewActivity.class);
                    break;
                case 14:
                    AdvanceActivity.this.openActivity(VolumeViewActivity.class);
                    break;
                case 15:
                    AdvanceActivity.this.openActivity(CustomImageViewActicity.class);
                    break;
                case 16:
                    AdvanceActivity.this.openActivity(ArcMenuActivity.class);
                    break;
                case 17:
                    AdvanceActivity.this.openActivity(ReboundScrollViewActivity.class);
                    break;
                case 18:
                    AdvanceActivity.this.openActivity(AnimationViewPagerActivity.class);
                    break;
                case 19:
                    AdvanceActivity.this.openActivity(GestureLockViewActivity.class);
                    break;
                case 20:
                    AdvanceActivity.this.openActivity(AnimationActivity.class);
                    break;
                case 21:
                    AdvanceActivity.this.openActivity(RubberIndicatiorActivity.class);
                    break;
                case 22:
                    AdvanceActivity.this.openActivity(FlowLayoutActivity.class);
                    break;
                case 23:
                    AdvanceActivity.this.openActivity(AdapterScrollViewActivity.class);
                    break;
                case 24:
                    AdvanceActivity.this.openActivity(MotionEventActivity.class);
                    break;
                case 25:
                    AdvanceActivity.this.openActivity(FloatWindowActivity.class);
                    break;
                case 26:
                    AdvanceActivity.this.openActivity(DragViewActivity.class);
                    break;
                case 27:
                    AdvanceActivity.this.openActivity(AdvertisementActivity.class);
                    break;
                case 28:
                    AdvanceActivity.this.openActivity(XiuViewActivity.class);
                    break;

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
