package com.timmy.advance.citySelect;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 带热门城市选择的界面展示
 */
public class HotCityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_city);
        ButterKnife.bind(this);
        initToolBar();
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
