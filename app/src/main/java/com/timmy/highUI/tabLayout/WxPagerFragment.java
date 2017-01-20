package com.timmy.highUI.tabLayout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;

public class WxPagerFragment extends BasePagerFragment {

    private static final String ARG_PARAM1 = "param1";
    private int position;

    public static WxPagerFragment newInstance(int position) {
        WxPagerFragment fragment = new WxPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1,0);
        }
    }

    @Override
    protected int inflateView() {
        return R.layout.fragment_wx_pager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText("Framgnet"+(position+1));
    }
}
