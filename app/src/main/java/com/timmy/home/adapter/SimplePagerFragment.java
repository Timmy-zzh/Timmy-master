package com.timmy.home.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseFragment;

import butterknife.BindView;


/**
 * Created by admin on 2017/3/1.
 */

public class SimplePagerFragment extends BaseFragment {

    private static final String KEY_PAGER_POSITION = "pager_position";
    private static final String KEY_PAGER_TITLE = "pager_title";
    private int pagerPosition;
    private String mTitle;
    @BindView(R.id.tv_text)
    TextView title;

    public static Fragment newInstance(int position, String s) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_PAGER_POSITION,position);
        bundle.putString(KEY_PAGER_TITLE,s);
        SimplePagerFragment fragment = new SimplePagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int inflaterFragment() {
        return R.layout.fragment_base;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerPosition = getArguments().getInt(KEY_PAGER_POSITION);
        mTitle = getArguments().getString(KEY_PAGER_TITLE);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title.setText(mTitle+":"+pagerPosition);
    }
}
