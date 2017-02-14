package com.timmy.project.svg;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.base.BaseActivity;

/**
 * 5.0以上直接在AS中使用SVG图片
 * 展示svg图片效果：ImagerView直接引用svg.xml文件
 * SVG动画效果: svg.xml每段path设置动画,然后展示
 */
public class SVGActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);
        initToolBar();

        ImageView svg = (ImageView) findViewById(R.id.iv_svg);
        AnimatedVectorDrawable animatedVectorDrawable =
                (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.heart_vector_animation);
        svg.setImageDrawable(animatedVectorDrawable);
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_svg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_svg:
                Util.gotoNextActivity(this, SVGMergeActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
