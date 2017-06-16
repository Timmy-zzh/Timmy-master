package com.timmy.framework.imageLoaderFw.TimmyImageLoader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.timmy.framework.imageLoaderFw.TimmyImageLoader.request.BitmapRequest;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.utils.BitmapDecoder;
import com.timmy.framework.imageLoaderFw.TimmyImageLoader.utils.ImageViewHelper;

import java.io.File;
import java.io.FileOutputStream;
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
    protected Bitmap onLoadImage(final BitmapRequest bitmapRequest) {
        downLoadImage(bitmapRequest, getCacheFile(bitmapRequest.mImageUrlMd5));

        BitmapDecoder bitmapDecoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(getCacheFile(bitmapRequest.mImageUrlMd5).getAbsolutePath(), options);
            }
        };

        return bitmapDecoder.decodeBitmap(
                ImageViewHelper.getImageViewWidth(bitmapRequest.getImageView()),
                ImageViewHelper.getImageViewHeight(bitmapRequest.getImageView())
        );
    }

    /**
     * 下载后保存到固定的位置
     *
     * @param bitmapRequest
     */
    private boolean downLoadImage(BitmapRequest bitmapRequest, File file) {
        String imageUrl = bitmapRequest.mImageUrl;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(imageUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = urlConnection.getInputStream();
            //读取io流保存
            fos = new FileOutputStream(file);
            byte[] buff = new byte[512];
            int len = 0;
            //从流中读出来,再写入文件
            while ((len = inputStream.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 规定图片保存的路径
     *
     * @param md5Url
     * @return
     */
    private File getCacheFile(String md5Url) {
        File file = new File(Environment.getExternalStorageDirectory(), "imageLoader");
        if (!file.exists()) {
            file.mkdir();
        }
        return new File(file, md5Url);
    }

//    private HttpURLConnection urlConnection;
//    private InputStream inputStream;

//    @Override
//    protected Bitmap onLoadImage(BitmapRequest bitmapRequest) {
//        String imageUrl = bitmapRequest.mImageUrl;
//        try {
//            URL url = new URL(imageUrl);
//            urlConnection = (HttpURLConnection) url.openConnection();
//            inputStream = urlConnection.getInputStream();
//            /**
//             * 获取到流，从流中解析到图片返回-->直接从io流中解析获取到Bitmap,到图片很大时
//             * 直接加载会导致OOM,
//             * 2.第二个原因,IO流中的图片大小并不是我想要的图片大小,需要处理为,我想要的图片的大小
//             * ==>处理方案:先下载到本地,经过处理后再返回
//             */
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//
//            return bitmap;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                urlConnection.disconnect();
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
}
