package com.timmy.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.timmy.R;
import com.timmy.ui.technologypoint.AnimationActivity;
import com.timmy.ui.technologypoint.AutoPlayPicturesActivity;
import com.timmy.ui.technologypoint.CameraPictureActivity;
import com.timmy.ui.technologypoint.GlideImageActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/23.
 * 测试提交
 */
public class TechnologyPointActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_auto_images)
    Button btn_autoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_point);
        ButterKnife.bind(this);
        initToolBar();
    }

    @OnClick({R.id.btn_auto_images, R.id.btn_camera_picture, R.id.btn_animation,R.id.btn_glide})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_auto_images:
                openActivity(AutoPlayPicturesActivity.class);
                break;
            case R.id.btn_camera_picture:
                openActivity(CameraPictureActivity.class);
                break;
            case R.id.btn_animation:
                openActivity(AnimationActivity.class);
                break;
            case R.id.btn_glide:
                openActivity(GlideImageActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
