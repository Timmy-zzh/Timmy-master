package com.timmy.highUI.coordinatorLayout;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.timmy.R;
import com.timmy.base.BaseFragment;
import com.timmy.highUI.recyclerview.decoration.DividerGridItemDecoration;
import com.timmy.highUI.recyclerview.decoration.DividerItemDecoration;
import com.timmy.home.MainContentAdapter;
import com.timmy.home.model.MainModel;
import com.timmy.home.model.MainTag;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * TabLayout+ViewPager+Fragment
 */
public class TabPageFragment extends BaseFragment {

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

    public static TabPageFragment newInstance(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PAGE_TAB, page);
        TabPageFragment fragment = new TabPageFragment();
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
        for (int i = 0; i < 100; i++) {
            pageListOne.add(new MainModel(MainTag.TAG_XIUXIU, "Item:"+i));
            adapter.setData(pageListOne);
        }
    }
}
