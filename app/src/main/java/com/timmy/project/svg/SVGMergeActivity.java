package com.timmy.project.svg;

import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.timmy.R;
import com.timmy.base.BaseActivity;

/**
 * 为兼容5.0一下版本
 * 1.先获取svg图片的path信息
 * 2.根据path路劲信息,绘制动画
 * SVG的解析-》解析后进行绘制
 */
public class SVGMergeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_merge);
        initToolBar();
        final PathView pathView = (PathView) findViewById(R.id.svg_path);

//        final Path path = makeConvexArrow(50, 100);
//        pathView.setPath(path);
//        pathView.setFillAfter(true);
//        pathView.useNaturalColors();
//        pathView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pathView.getPathAnimator().
////                        pathView.getSequentialPathAnimator().
//                                delay(100).
//                        duration(1500).
//                        interpolator(new AccelerateDecelerateInterpolator()).
//                        start();
//            }
//        });
    }

    private Path makeConvexArrow(float length, float height) {
        final Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(length / 4f, 0.0f);
        path.lineTo(length, height / 2.0f);
        path.lineTo(length / 4f, height);
        path.lineTo(0.0f, height);
        path.lineTo(length * 3f / 4f, height / 2f);
        path.lineTo(0.0f, 0.0f);
        path.close();
        return path;
    }
}
