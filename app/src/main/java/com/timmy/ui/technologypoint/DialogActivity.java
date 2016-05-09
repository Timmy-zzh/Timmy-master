package com.timmy.ui.technologypoint;

import android.os.Bundle;
import android.view.View;

import com.timmy.R;
import com.timmy.ui.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        initToolBar();
    }

    @OnClick({R.id.tv_date})
    public void onDialog(View view){

    }

}
