package com.timmy.customeView.hotTag;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;

import java.util.Random;

public class HotTagActivity extends BaseActivity {

    private static final String temp = "案件代理法";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_tag);
        initToolBar();

        TagLayout tagLayout = (TagLayout) findViewById(R.id.tag_layout);

        for (int i = 0; i < 11; i++) {
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.item_list, tagLayout, false);
            textView.setText("Item:" + i + temp.substring(0, new Random().nextInt(temp.length())));
            textView.setBackgroundResource(R.drawable.flow_text_bg);
            textView.setTextSize(16);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.white));

            tagLayout.addView(textView);
        }

    }
}
