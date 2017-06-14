package com.timmy.framework.imageLoaderFw;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.framework.annotationsFramework.ViewInjectUtils;
import com.timmy.framework.annotationsFramework.annotations.ContentView;
import com.timmy.framework.annotationsFramework.annotations.OnViewClick;
import com.timmy.framework.annotationsFramework.annotations.ViewInject;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.TimmyImageLoader;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.cache.BitmapCache;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.config.DisplayConfig;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.config.ImageLoaderConfig;

@ContentView(R.layout.activity_image_loader)
public class ImageLoaderActivity extends BaseActivity {

    @ViewInject(R.id.btn_load_one)
    Button mBtnLoadOne;
    @ViewInject(R.id.btn_load_images)
    Button mBtnLoadMore;
    @ViewInject(R.id.iv_image)
    ImageView mImageView;
    @ViewInject(R.id.rv_recycleView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);
        initToolBar();

        initImageLoad();
    }

    private void initImageLoad() {
//        TimmyImageLoader.getInstance().displayImage();
        ImageLoaderConfig config = new ImageLoaderConfig();

        BitmapCache mBitmapCache = config.mBitmapCache;
        DisplayConfig mDisplayConfig = config.mDisplayConfig;


    }

    @OnViewClick({R.id.btn_load_one,R.id.btn_load_images})
    public void loadImage(View view){
        switch (view.getId()){
            case R.id.btn_load_one:

                break;
            case R.id.btn_load_images:

                break;
        }
    }
}
