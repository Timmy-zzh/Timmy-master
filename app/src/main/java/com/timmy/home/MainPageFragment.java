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
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.removeItemDecoration(mDivider);
        mRecyclerView.addItemDecoration(mDivider);
        adapter = new MainContentAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        switch (mPage) {
            case 1:
                pageListOne.add(new MainModel(MainTag.TAG_XIUXIU, "自定义控件--支付宝咻一咻"));
                pageListOne.add(new MainModel(MainTag.TAG_COLLAPSING_TOOLBAR_LAYOUT, "CollapsingToolbarLayout使用"));
                pageListOne.add(new MainModel(MainTag.TAG_QQ_ZONE_STRETCH, "QQ空间可拉伸头部ListView控件"));
                pageListOne.add(new MainModel(MainTag.TAG_RECYCLER_VIEW, "RecyclerView使用"));
                pageListOne.add(new MainModel(MainTag.TAG_SLIDESLIP, "MD侧滑"));
                pageListOne.add(new MainModel(MainTag.TAG_SNACKBAR, "SnackBar解析"));
                pageListOne.add(new MainModel(MainTag.TAG_TEXT_INPUT_LAYOUT, "TextInputLayout使用"));
                pageListOne.add(new MainModel(MainTag.TAG_TOOLBAR, "Toolbar源码解析"));
                pageListOne.add(new MainModel(MainTag.TAG_SEARCH_VIEW, "SearchView源码解析"));
                pageListOne.add(new MainModel(MainTag.TAG_LINEAR_LAYOUT_COMPAT, "LinearLayoutCompat源码解析"));
                pageListOne.add(new MainModel(MainTag.TAG_TAB_LAYOUT, "TabLayout源码解析"));
                pageListOne.add(new MainModel(MainTag.TAG_PALETTE, "Palette调色板的使用"));

                adapter.setData(pageListOne);
                break;
            case 2:
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_ACTIVITY_LAUNCH, "App广告页3秒倒计时处理"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_ACTIVITY_SPLASH, "App闪屏页动画效果"));
                pageListTwo.add(new MainModel(MainTag.PROJECT.TAG_SVG, "SVG图片效果"));


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
                adapter.setData(pageListThree);
                break;
        }
    }
}
