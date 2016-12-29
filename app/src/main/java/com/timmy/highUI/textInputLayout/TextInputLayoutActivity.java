package com.timmy.highUI.textInputLayout;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class TextInputLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input_layout);
        initToolBar();

        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);

        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError("error info");
        textInputLayout.setCounterEnabled(true);
        textInputLayout.setCounterMaxLength(10);


    }
}
