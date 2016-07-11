package com.timmy.ui.technologypoint;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;
import com.timmy.widget.TimmyHealthView;

/**
 * 自定义view
 * 1.定义view的属性--attrs.xml
 * 1.1在xml文件中记得引用命名空间
 * xmlns:app=”http://schemas.Android.com/apk/res-auto”
 * 2.在自定义view中获得自定义view的属性--attrs
 */
public class TimmyHealthActivity extends BaseActivity {

    private TimmyHealthView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        initToolBar();

        view = (TimmyHealthView) findViewById(R.id.health_view);
        view.setMySize(2345);
        view.setRank(11);
        view.setAverageSize(5436);

    }
}
