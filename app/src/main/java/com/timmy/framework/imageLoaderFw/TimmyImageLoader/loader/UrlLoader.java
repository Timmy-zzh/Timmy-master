package com.timmy.framework.imageLoaderFw.TimmyImageLoader.loader;

import android.graphics.Bitmap;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by timmy1 on 17/6/14.
 * 加载网络图片
 */
public class UrlLoader extends AbsLoader {
    @Override
    protected Bitmap onLoadImage(BitmapRequest bitmapRequest) {
        String imageUrl = bitmapRequest.mImageUrl;

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();

            //获取到流，从流中解析到图片返回


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
