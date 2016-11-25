package com.timmy.highUI.stretchList;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class StretchListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stretch_list);
        initToolBar();

        View headerView  = View.inflate(this,R.layout.view_stretch_image,null);
        ImageView stretchIv = (ImageView) headerView.findViewById(R.id.iv1);

        StretchListView listview = (StretchListView) findViewById(R.id.slv);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.item_list,
                new String[]{
                        "起床",
                        "洗脸刷牙",
                        "跑步",
                        "冲凉",
                        "早餐",
                        "地铁",
                        "工作",
                        "午餐",
                        "休息",
                        "开会",
                        "总结",
                        "班车",
                        "聚会",
                        "充电",
                        "晚安",
                        "..."
                });

        listview.addHeaderView(headerView);
        listview.setStretchImageView(stretchIv);
        listview.setAdapter(adapter);
    }
}
