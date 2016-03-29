package com.timmy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.timmy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/23.
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
    }

    @OnClick(R.id.btn_auto_images)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_auto_images:
                startActivity(new Intent(this,AutoPlayPicturesActivity.class));
                break;
            default:
                break;
        }
    }


}
