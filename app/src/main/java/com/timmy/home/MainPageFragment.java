package com.timmy.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.timmy.R;
import com.timmy.highUI.recyclerview.decoration.DividerGridItemDecoration;
import com.timmy.highUI.recyclerview.decoration.DividerItemDecoration;
import com.timmy.base.BaseFragment;
import com.timmy.home.model.MainModel;
import com.timmy.home.model.MainTag;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

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
    private List<MainModel> pageListOne = new ArrayList<>();
    private List<MainModel> pageListTwo = new ArrayList<>();
    private List<MainModel> pageListThree = new ArrayList<>();
    private List<MainModel> pageListFour = new ArrayList<>();
    private RecyclerView.ItemDecoration mDivider;

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
    public void onCreate(Bundle savedInstanceState) {
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
                mDivider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
//                layoutManager = new GridLayoutManager(getActivity(), 2);
//                mDivider = new DividerGridItemDecoration(getContext());

                break;
            case 2:
                layoutManager = new GridLayoutManager(getActivity(), 2);
                mDivider = new DividerGridItemDecoration(getContext());
                break;
            case 3:
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                mDivider = new DividerGridItemDecoration(getContext());
                break;
            default:
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                mDivider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
                break;
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.removeItemDecoration(mDivider);
        mRecyclerView.addItemDecoration(mDivider);
        adapter = new MainContentAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        switch (mPage) {
            case 1://高级ui
                pageListOne.add(new MainModel(MainTag.TAG_XIUXIU, "自定义控件--支付宝咻一咻"));
                pageListOne.add(new MainModel(MainTag.TAG_COLLAPSING_TOOLBAR_LAYOUT, "CollapsingToolbarLayout使用"));
                pageListOne.add(new MainModel(MainTag.TAG_QQ_ZONE_STRETCH, "QQ空间可拉伸头部ListView控件"));
                pageListOne.add(new MainModel(MainTag.TAG_RECYCLER_VIEW, "RecyclerView使用"));
                pageListOne.add(new MainModel(MainTag.TAG_SLIDESLIP, "MD侧滑"));
                pageListOne.add(new MainModel(MainTag.TAG_SNACKBAR, "SnackBar解析"));
                pageListOne.add(new MainModel(MainTag.TAG_TOAST, "Toast源码解析"));
                pageListOne.add(new MainModel(MainTag.TAG_DIALOG, "Dialog源码解析"));
                pageListOne.add(new MainModel(MainTag.TAG_TEXT_INPUT_LAYOUT, "TextInputLayout使用"));
                pageListOne.add(new MainModel(MainTag.TAG_TOOLBAR, "Toolbar源码解析"));
                pageListOne.add(new MainModel(MainTag.TAG_SEARCH_VIEW, "SearchView源码解析"));
                pageListOne.add(new MainModel(MainTag.TAG_LINEAR_LAYOUT_COMPAT, "LinearLayoutCompat源码解析"));
                pageListOne.add(new MainModel(MainTag.TAG_TAB_LAYOUT, "TabLayout源码解析"));
                pageListOne.add(new MainModel(MainTag.TAG_PALETTE, "Palette调色板的使用"));
                pageListOne.add(new MainModel(MainTag.TAG_CARD_VIEW, "CardView的使用"));
                pageListOne.add(new MainModel(MainTag.TAG_COORDINATOR_LAYOUT, "CoordinatorLayout的使用"));
                pageListOne.add(new MainModel(MainTag.TAG_ANIMATION_VIEW, "动画的使用"));
                pageListOne.add(new MainModel(MainTag.TAG_MOTION_EVENT, "事件分发"));
                pageListOne.add(new MainModel(MainTag.TAG_PATH, "Path高级使用"));
                pageListOne.add(new MainModel(MainTag.TAG_SHADER, "高级渲染"));

                adapter.setData(pageListOne);
                break;
            case 2://自定义控件
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_CLOCK_VIEW, "自定义钟表"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_HOT_TAG, "热门标签"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_LETTER_NAVIGATION, "右侧A-Z字母导航栏"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_NOTE_PAD, "自定义NotePad"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_MY_VIEWPAGER, "自定义ViewPager"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_MY_INDICATOR, "自定义ViewPager指示器"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_GUAGUA_WINNING, "刮刮乐"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_PHOTO_VIEW, "高仿今日头条图片功能"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_LOADING_LAYOUT, "加载控件"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_RADAR_VIEW, "雷达展示控件"));
                pageListFour.add(new MainModel(MainTag.CUSTOMEVIEW.TAG_IMOOC_RIPPLE, "仿慕课网下拉刷新水波纹"));


                adapter.setData(pageListFour);
                break;
            case 3://项目知识点总结
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_ACTIVITY_LAUNCH, "App广告页3秒倒计时处理"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_ACTIVITY_SPLASH, "App闪屏页动画效果"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_SVG, "SVG图片效果"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_INFLATE, "inflate()方法的使用和详解"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_SERVICE, "Service组件详解"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_AIDL_BINDER, "AIDL(Binder机制)"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_TWOCODE_DOWNLOAD, "第二行代码实例-通过服务进行下载"));


                adapter.setData(pageListTwo);
                break;
            case 4://框架学习
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_ANNOTATIONS, "自己实现注解框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_NETWORK_REQUEST, "网络请求框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_DATABASE, "数据库框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_IMAGE_LOADER, "图片加载框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_ANIMATOR, "动画框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_RAJAVA, "RxJava响应式编程"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_EVENT_BUS, "EventBus"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_HOT_FIX, "热更新-热修复框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_TENCENT_TINKER, "腾讯Tinker框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_AUTO_RECYCLER, "无限轮播"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_PULL_DOWN_REFRESH, "下拉刷新，上拉加载更多框架"));
                pageListThree.add(new MainModel(MainTag.FRAMEWORK.TAG_VIEWPAGER_SCROLL, "各种左右滑动页面ViewPager效果"));

                adapter.setData(pageListThree);
                break;
        }
    }
}
