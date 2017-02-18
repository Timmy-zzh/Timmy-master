package com.timmy.highUI.motionEvent;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.timmy.R;
import com.timmy.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ScrollListViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_list_view);
        initToolBar();

        initView();

    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.list_view);

        List<String> dataList = new ArrayList();
        for (int i = 0; i < 30; i++) {
            dataList.add("Item " + i);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1,dataList);
        listView.setAdapter(adapter);

    }
}
