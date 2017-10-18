package com.timmy.framework.annotationCompile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.timmy.R;

/**
 * 编译时注解框架--注解处理器
 */
public class CompileAnnotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotations);

    }
}
