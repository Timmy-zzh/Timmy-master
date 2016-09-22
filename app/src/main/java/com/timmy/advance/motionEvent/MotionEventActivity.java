package com.timmy.advance.motionEvent;

import android.os.Bundle;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

public class MotionEventActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event);
        initToolBar();
        MyButton myButton = (MyButton) findViewById(R.id.my_button);

        MyTextView myTextView = (MyTextView) findViewById(R.id.my_textView);

    }
}
