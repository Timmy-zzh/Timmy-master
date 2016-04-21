package com.timmy.ui.technologypoint;

import android.os.Bundle;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.ui.BaseActivity;
import com.timmy.util.ImageUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GlideImageActivity extends BaseActivity {

    @Bind(R.id.iv_image)
    ImageView iv_image;
    @Bind(R.id.iv_image2)
    ImageView iv_image2;

    private static final String url = "http://7xoop6.com2.z0.glb.qiniucdn.com/images/deal/deal20151125/D101037.jpg!thumbnail";

    private static final String url2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);
        initToolBar();

        ImageUtil.imageLoad(iv_image,url);

        ImageUtil.imageLoad(iv_image2,url2);

//        Glide.with(this)
//                .load(url)
//                .error(R.mipmap.ic_launcher)
//                .into(iv_image);
//
//        Glide.with(iv_image2.getContext())
//                .load(url2).centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .crossFade()
//                .into(iv_image2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
