package com.timmy.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;
import com.timmy.ui.technologypoint.AnimationActivity;
import com.timmy.ui.technologypoint.AutoPlayPicturesActivity;
import com.timmy.ui.technologypoint.CameraPictureActivity;
import com.timmy.ui.technologypoint.ClipToOutlineActivity;
import com.timmy.ui.technologypoint.DialogActivity;
import com.timmy.ui.technologypoint.GlideImageActivity;
import com.timmy.ui.technologypoint.RecycleHeaderViewActivity;
import com.timmy.ui.technologypoint.TimmyHealthActivity;
import com.timmy.ui.technologypoint.ToolBarActivity;

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

    @OnClick({R.id.btn_auto_images, R.id.btn_camera_picture,
            R.id.btn_animation, R.id.btn_glide, R.id.btn_clip,
            R.id.btn_dialog, R.id.btn_view, R.id.btn_recycleview,
            R.id.btn_toolbar})
    public void technology(View v) {
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
            case R.id.btn_clip:
                openActivity(ClipToOutlineActivity.class);
                break;
            case R.id.btn_dialog:
                openActivity(DialogActivity.class);
                break;
            case R.id.btn_view:
                openActivity(TimmyHealthActivity.class);
                break;
            case R.id.btn_recycleview:
                openActivity(RecycleHeaderViewActivity.class);
                break;
            case R.id.btn_toolbar:
                openActivity(ToolBarActivity.class);
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
