package com.timmy.advance.customView;

import android.os.Bundle;
import android.view.View;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class XiuViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_view);
        initToolBar();

        final XiuXiuView xiuView = (XiuXiuView) findViewById(R.id.xiuxiu);

        xiuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiuView.startXiu();
            }
        });

    }
}
