package com.timmy.customeView.myViewPager;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class MyViewPagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_pager);
        initToolBar();
//        ViewPager
        final MyViewPager myViewPager = (MyViewPager) findViewById(R.id.my_view_pager);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_grouop);
        int childCount = myViewPager.getChildCount();

        for (int i = 0; i < childCount; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i);
            radioGroup.addView(radioButton);
            if (i == 0) {
                //默认第一个选中
                radioButton.setChecked(true);
            }
        }

        myViewPager.setOnItemPagerSelectedListener(new MyViewPager.OnItemPagerSelectedListener() {
            @Override
            public void onItemPagerSelected(int position) {
                radioGroup.check(position);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                myViewPager.setCurrentItem(checkedId);
            }
        });
    }
}
