package com.timmy.highUI.tabLayout;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.library.util.Logger;

public abstract class BasePagerFragment extends Fragment {

    private String TAG = this.getClass().getSimpleName();

    public BasePagerFragment() {
        Logger.d(TAG,"构造函数");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.d(TAG,"onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d(TAG,"onDetach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.d(TAG,"onCreateView");
        return inflater.inflate(inflateView(),container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d(TAG,"onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG,"onDestroy");
    }

    protected abstract int inflateView();

}
