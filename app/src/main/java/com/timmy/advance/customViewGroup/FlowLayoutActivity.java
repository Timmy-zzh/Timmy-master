package com.timmy.advance.customViewGroup;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;

/**
 * 流式布局实现:用于热门标签的展示
 */
public class FlowLayoutActivity extends BaseActivity {

    private String[] mTags = new String[]{"Android", "孙子兵法","IOS", "Python", "Java", "Kotlin", "RxJava", "RxAndroid", "Spark","React Native"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        initToolBar();

        FlowLayout mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        for (String mTag : mTags) {
            TextView textView = new TextView(this);
            textView.setText(mTag);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.flow_text_bg);
            textView.setTextColor(getResources().getColor(R.color.colorAccent));
            mFlowLayout.addView(textView);
        }
    }
}
