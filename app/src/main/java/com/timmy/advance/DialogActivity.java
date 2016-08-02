package com.timmy.advance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timmy.R;

/**
 * 使用Activity展示提示框Dialog效果
 * 需要处理Activity主题--没有Title-背景透明
 */
public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog2);
    }
}