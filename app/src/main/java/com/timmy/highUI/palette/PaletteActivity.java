package com.timmy.highUI.palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.library.util.Logger;

import java.util.List;

public class PaletteActivity extends BaseActivity {

    private java.lang.String TAG = this.getClass().getSimpleName();
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);
        initToolBar();

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);

        //根据Bitmap本地图片，再使用Palette调色板获取到图片颜色信息，在进行展示
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_meinv);

        //同步获取
        Palette palette = Palette.from(bitmap).generate();
        //获取全部的颜色信息
//        List<Palette.Swatch> swatches = palette.getSwatches();



        //异步获取Palette调色板
        AsyncTask<Bitmap, Void, Palette> generate = Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                if (palette != null) {
                    Logger.d(TAG, "palette:" + palette.getVibrantColor(0));
                    setTextViewBackgroundColor(palette);
                }
            }
        });


    }

    private void setTextViewBackgroundColor(Palette palette) {

        //充满活力的颜色
        int vibrantColor = palette.getVibrantColor(0);
        Logger.d(TAG, "vibrantColor:" + vibrantColor);
        tv1.setText("vibrantColor");
        tv1.setBackgroundColor(vibrantColor);

        //
//        Palette.Swatch vibrantSwatch = palette.getLightVibrantSwatch();
        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        int vibrantColor2 = vibrantSwatch.getRgb();
        Logger.d(TAG, "vibrantColor2:" + vibrantColor2);
        tv7.setText("vibrantColor2");
        tv7.setBackgroundColor(vibrantColor2);

        //充满活力的黑
        int darkVibrantColor = palette.getDarkVibrantColor(0);
        Logger.d(TAG, "darkVibrantColor:" + darkVibrantColor);
        tv2.setText("darkVibrantColor");
        tv2.setBackgroundColor(darkVibrantColor);

        //充满活力的亮
        int lightVibrantColor = palette.getLightVibrantColor(0);
        Logger.d(TAG, "lightVibrantColor:" + lightVibrantColor);
        tv3.setText("lightVibrantColor");
        tv3.setBackgroundColor(lightVibrantColor);
        //柔和的
        int mutedColor = palette.getMutedColor(0);
        tv4.setText("mutedColor");
        tv4.setBackgroundColor(mutedColor);

        //柔和的黑
        int darkMutedColor = palette.getDarkMutedColor(0);
        tv5.setText("darkMutedColor");
        tv5.setBackgroundColor(darkMutedColor);
        //柔和的亮
        int lightMutedColor = palette.getLightMutedColor(0);
        tv6.setText("lightMutedColor");
        tv6.setBackgroundColor(lightMutedColor);



        //
    }


}
