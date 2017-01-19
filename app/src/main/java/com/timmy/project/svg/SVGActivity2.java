package com.timmy.project.svg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.timmy.R;

/**
 * 为兼容5.0一下版本
 * 1.先获取svg图片的path信息
 * 2.根据path路劲信息,绘制动画
 * SVG的解析-》解析后进行绘制
 *
 */
public class SVGActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg2);
    }
}
