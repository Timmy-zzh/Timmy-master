package com.timmy.framework.imageLoaderFw.TimmyImageLoader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.utils.BitmapDecoder;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.utils.ImageViewHelper;

import java.io.File;

/**
 * Created by timmy1 on 17/6/14.
 * 加载本地图片
 */

public class LocalLoader extends AbsLoader {
    @Override
    protected Bitmap onLoadImage(BitmapRequest bitmapRequest) {
        final String path = Uri.parse(bitmapRequest.mImageUrl).getPath();
        File file = new File(path);
        if (!file.exists()){
            return null;
        }
        BitmapDecoder bitmapDecoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path,options);
            }
        };

        return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(bitmapRequest.getImageView()),
                ImageViewHelper.getImageViewHeight(bitmapRequest.getImageView()));
    }
}
