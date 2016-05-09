package com.timmy.ui.technologypoint;

import android.graphics.Outline;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.ui.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClipToOutlineActivity extends BaseActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip);
        ButterKnife.bind(this);
        imageView = (ImageView) findViewById(R.id.iv_image);
        initToolBar();
    }

    @OnClick(R.id.btn_clip1)
    public void clip(View view){

        ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int margin = 20;
                int size = view.getHeight();
//                outline.setRect(margin, margin, size - margin, size - margin);
                outline.setOval(margin, margin, size - margin, size - margin);
            }
        };
        imageView.setOutlineProvider(viewOutlineProvider);
        imageView.setClipToOutline(true);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
