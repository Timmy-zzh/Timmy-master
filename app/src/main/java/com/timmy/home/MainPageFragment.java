package com.timmy.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.timmy.R;
import com.timmy.base.BaseFragment;

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
    private List<String> dataList = new ArrayList<String>();

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
        adapter = new MainContentAdapter(getActivity());
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        String str = "";
        for (int i = 0; i < 15; i++) {
            str = "内容信息" + i;
            if (i % 3 == 0)
                str = "内容信息内容信息内容信息内容信息内容信息内容信息内容信息内容信息内容信息内容信息内容信息内容信息" + i;

            dataList.add(str);
        }
        adapter.setData(dataList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
