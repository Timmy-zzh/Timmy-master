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
import com.timmy.advance.citySelect.CitySelectActivity;
import com.timmy.advance.citySelect.HotCityActivity;
import com.timmy.advance.customView.CustomImageViewActicity;
import com.timmy.advance.customView.MyToggleButtonActivity;
import com.timmy.advance.customView.RingViewActivity;
import com.timmy.advance.customView.TextImageViewActivity;
import com.timmy.advance.customView.VerificationCodeActivity;
import com.timmy.advance.customView.VolumeViewActivity;
import com.timmy.advance.customViewGroup.CustomViewPagerActivity;
import com.timmy.advance.dialog.DialogActivity;
import com.timmy.advance.screenAdapter.ImageAdapterActivity;
import com.timmy.advance.scrollVertical.VerticalScrollActivity;
import com.timmy.advance.slideList.FollowSlideListActivity;
import com.timmy.advance.viewpagerIndicator.ViewPagerIndicatiorActivity;
import com.timmy.advance.waterRipple.WaterRippleActivity;
import com.timmy.advance.win8metro.FollowWin8Activity;
import com.timmy.ui.base.BaseActivity;
import com.timmy.ui.base.BaseRecyclerViewAdapter;
import com.timmy.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdvanceActivity extends BaseActivity {

    @Bind(R.id.rv_recycleView)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerViewAdapter adapter;
    private List<String> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);
        ButterKnife.bind(this);
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
            startActivity(new Intent(this, DialogActivity.class));
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
                    AdvanceActivity.this.openActivity(DialogActivity.class);
                    break;
                case 2:
                    AdvanceActivity.this.openActivity(FollowWin8Activity.class);
                    break;
                case 3:
                    AdvanceActivity.this.openActivity(VerticalScrollActivity.class);
                    break;
                case 4:
                    AdvanceActivity.this.openActivity(MyToggleButtonActivity.class);
                    break;
                case 5:
                    AdvanceActivity.this.openActivity(WaterRippleActivity.class);
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
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
