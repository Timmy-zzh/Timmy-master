package com.timmy.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.timmy.R;
import com.timmy.api.recyclerview.DividerItemDecoration;
import com.timmy.base.BaseFragment;
import com.timmy.home.model.MainModel;
import com.timmy.home.model.MainTag;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * TabLayout+ViewPager+Fragment
 */
public class MainPageFragment extends BaseFragment {

    @Bind(R.id.rv_recycleView)
    RecyclerView mRecyclerView;

    private static final String KEY_PAGE_TAB = "key_page_tab";
    private int mPage;
    private RecyclerView.LayoutManager layoutManager;
    private MainContentAdapter adapter;
    private List<MainModel> pageListOne = new ArrayList<MainModel>();
    private List<MainModel> pageListTwo = new ArrayList<MainModel>();
    private List<MainModel> pageListThree = new ArrayList<MainModel>();

    public static MainPageFragment newInstance(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PAGE_TAB, page);
        MainPageFragment fragment = new MainPageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int inflaterFragment() {
        return R.layout.fragment_tab_page;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(KEY_PAGE_TAB);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycleView();
        initData();
    }

    private void initRecycleView() {
        switch (mPage) {
            case 1:
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                break;
            case 2:
                layoutManager = new GridLayoutManager(getActivity(), 2);
                break;
            case 3:
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        adapter = new MainContentAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        switch (mPage) {
            case 1:
                pageListOne.add(new MainModel(MainTag.TAG_QQ_ZONE_STRETCH, "QQ空间可拉伸头部ListView控件"));
                pageListOne.add(new MainModel(MainTag.TAG_XIUXIU, "自定义控件--支付宝咻一咻"));
                adapter.setData(pageListOne);
                break;
            case 2:
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "仿腾讯List滑动删除"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "使用Activity作为Dialog来展示"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "高仿win8效果的界面展示"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "Android自定义ViewGroup实现竖向引导界面"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(一)--开关按钮"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(二)--水波纹效果"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup(三)--实现ViewPager效果"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(四)--图片适配"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "ViewPager指示器"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "城市列表选择"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "城市列表选择--带热门城市"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(五)--随机数验证码"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(六)--带文字的图片"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(七)--圆环交替"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(八)--音量控件"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(九)--带圆角ImageView"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup--圆形菜单"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup--反弹ScrollView"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewPager--不一样的ViewPager滑动效果"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup--应用手势锁"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "Android属性动画"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义动画指示器-RubberIndicatior"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup--流式布局"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ScrollView--显示完成内部的ItemView"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "从源码分析事件分发机制"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View--悬浮窗"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup--可拖拽控件"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "3秒倒计时广告页面"));
                pageListTwo.add(new MainModel(MainTag.TAG_XIUXIU, "自定义控件--支付宝咻一咻"));
                adapter.setData(pageListTwo);
                break;
            case 3:
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "仿腾讯List滑动删除"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "使用Activity作为Dialog来展示"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "高仿win8效果的界面展示"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "Android自定义ViewGroup实现竖向引导界面"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(一)--开关按钮"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(二)--水波纹效果"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup(三)--实现ViewPager效果"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(四)--图片适配"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "ViewPager指示器"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "城市列表选择"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "城市列表选择--带热门城市"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(五)--随机数验证码"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(六)--带文字的图片"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(七)--圆环交替"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(八)--音量控件"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View(九)--带圆角ImageView"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup--圆形菜单"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup--反弹ScrollView"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewPager--不一样的ViewPager滑动效果"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup--应用手势锁"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "Android属性动画"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义动画指示器-RubberIndicatior"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup--流式布局"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ScrollView--显示完成内部的ItemView"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "从源码分析事件分发机制"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义View--悬浮窗"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义ViewGroup--可拖拽控件"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "3秒倒计时广告页面"));
                pageListThree.add(new MainModel(MainTag.TAG_XIUXIU, "自定义控件--支付宝咻一咻"));
                adapter.setData(pageListThree);
                break;
        }
    }
}
