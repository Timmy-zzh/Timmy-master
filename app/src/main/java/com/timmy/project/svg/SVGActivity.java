package com.timmy.project.svg;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.timmy.R;

/**
 * 5.0以上直接在AS中使用SVG图片
 */
public class SVGActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);

        ImageView svg = (ImageView) findViewById(R.id.iv_svg);
        AnimatedVectorDrawable animatedVectorDrawable =
                (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.heart_vector_animation);
        svg.setImageDrawable(animatedVectorDrawable);
        if (animatedVectorDrawable!= null){
            animatedVectorDrawable.start();
        }

    }
}
