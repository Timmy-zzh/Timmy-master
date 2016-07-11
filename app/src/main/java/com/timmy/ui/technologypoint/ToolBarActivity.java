package com.timmy.ui.technologypoint;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToolBarActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
        ButterKnife.bind(this);

        mToolbar.setTitle("title");
        mToolbar.setSubtitle("SubTitle");
        mToolbar.setNavigationIcon(R.mipmap.icon_back);
//        mToolbar.setLogo(R.mipmap.img_beauti);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }
}
