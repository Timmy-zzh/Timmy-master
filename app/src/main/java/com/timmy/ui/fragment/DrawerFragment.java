package com.timmy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.advance.AdvanceActivity;
import com.timmy.ui.TechnologyPointActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class DrawerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv_technology,R.id.tv_advance})
    public void drawerClick(View v) {
        switch (v.getId()) {
            case R.id.tv_technology:
                getActivity().startActivity(new Intent(getActivity(), TechnologyPointActivity.class));
                break;
            case R.id.tv_advance:
//                getActivity().startActivity(new Intent(getActivity(), AdvanceActivity.class));
                startActivity(new Intent(getActivity(), AdvanceActivity.class));
                break;
            default:
                break;
        }
    }


}
