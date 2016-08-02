package com.timmy.advance.scrollvertical;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;
import com.timmy.util.Toast;

public class VerticalScrollActivity extends BaseActivity {

    private VerticalScrollView mVerticalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_scroll);
        initToolBar();

        mVerticalScrollView = (VerticalScrollView) findViewById(R.id.vs_view);

        mVerticalScrollView.setOnPagerChangeListener(new VerticalScrollView.OnPagerChangeListener() {
            @Override
            public void onPagerChange(int currentPager) {
                Toast.toastShort("当前页-" + currentPager);
            }
        });
    }
}
