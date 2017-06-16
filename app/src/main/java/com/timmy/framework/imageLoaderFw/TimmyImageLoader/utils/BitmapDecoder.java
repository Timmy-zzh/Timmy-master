package com.timmy.framework.imageLoaderFw.TimmyImageLoader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by admin on 2017/6/16.
 * 图片解码类
 */
public abstract class BitmapDecoder {

    public Bitmap decodeBitmap(int reqWidth, int reqHeight) {
        //初始化Options
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只是使用到Bitmap宽高,无需将图片加载到内存
        options.inJustDecodeBounds = true;
        //抽象方法,根据options判断是否去真是加载Bitmap
        decodeBitmapWithOptions(options);
        //计算图片需要的真实宽高
        calculateSampleSizeWithOption(options, reqWidth, reqHeight);

        return decodeBitmapWithOptions(options);
    }

    private void calculateSampleSizeWithOption(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //图片的原始宽高
        int width = options.outWidth;
        int height = options.outHeight;

        //通过原图宽高和真实需要的图片宽高,判断options是否需要缩放
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            //宽高缩放比
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            //图片缩放比,根据宽高最大比
            inSampleSize = Math.max(heightRatio, widthRatio);
        }

        //缩放比
        options.inSampleSize = inSampleSize;

        //每个像素2个字节
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //bitmap占用内存
        options.inJustDecodeBounds = false;
        //内存不足时可以回收bitmap
        options.inPurgeable = true;
        options.inInputShareable = true;
    }

    public abstract Bitmap decodeBitmapWithOptions(BitmapFactory.Options options);
}
