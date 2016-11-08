package com.timmy.advance.customViewGroup;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.timmy.R;
import com.timmy.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

public class ReboundScrollViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebound_scroll_view);
        initToolBar();

        ReboundScrollView scrollView = (ReboundScrollView) findViewById(R.id.bound_scroll_view);
        MyListView listView = (MyListView) findViewById(R.id.listview);

        scrollView.setOnSlideHalfListener(new ReboundScrollView.OnSlideHalfListener() {
            @Override
            public void onSlideHalf() {
                Toast.makeText(ReboundScrollViewActivity.this, "滑动超过一半", Toast.LENGTH_SHORT).show();
            }
        });

        String[] strArr = new String[]{"Hello", "World", "Welcome", "Java",
                "Android", "Lucene", "C++", "C#", "HTML", "Welcome", "Java",
                "Android", "Lucene", "C++", "C#", "HTML"};
        List<String> list = Arrays.asList(strArr);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strArr));


    }
}
