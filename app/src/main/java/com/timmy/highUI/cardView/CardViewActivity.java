package com.timmy.highUI.cardView;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class CardViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        initToolBar();
    }
}
