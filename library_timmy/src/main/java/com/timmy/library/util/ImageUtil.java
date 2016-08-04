package com.timmy.library.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.timmy.library.R;

import java.io.ByteArrayOutputStream;

/**
 *
 */
public class ImageUtil {

    public static void showRoundHeadImage(ImageView imageView, String path, int placeholderImg) {
        try {
//            if (!TextUtils.isEmpty(path)) {
//                Picasso.with(imageView.getContext()).load(path)
//                        .transform(new CircleTransform())
//                        .error(placeholderImg)
//                        .placeholder(placeholderImg).into(imageView);
//            } else {
//                imageView.setImageResource(placeholderImg);
//            }
        } catch (Exception e) {
            Logger.d("图片加载有问题" + path);
            e.printStackTrace();
        }
    }

//    static class CircleTransform implements Transformation {
//        @Override
//        public Bitmap transform(Bitmap source) {
//            int size = Math.min(source.getWidth(), source.getHeight());
//
//            int x = (source.getWidth() - size) / 2;
//            int y = (source.getHeight() - size) / 2;
//
//            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
//            if (squaredBitmap != source) {
//                source.recycle();
//            }
//
//            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
//
//            Canvas canvas = new Canvas(bitmap);
//            Paint paint = new Paint();
//            BitmapShader shader = new BitmapShader(squaredBitmap,
//                    BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
//            paint.setShader(shader);
//            paint.setAntiAlias(true);
//
//            float r = size / 2f;
//            canvas.drawCircle(r, r, r, paint);
//
//            squaredBitmap.recycle();
//            return bitmap;
//        }
//
//        @Override
//        public String key() {
//            return "circle";
//        }
//    }

    public static void imageLoad(ImageView view, String url) {
        imageLoad(view, url, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
    }

    public static void imageLoad(ImageView view, String url, int errId) {
        imageLoad(view, url, errId, R.mipmap.ic_launcher);
    }

    public static void imageLoad(ImageView view, String url, int errId, int placeholder) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(placeholder)//加载之前的默认图片
                .error(errId)//加载失败时显示的图片
                .crossFade()//加载前到加载成功的透明度过度
                .into(view);
    }



//    public static void load(String path, final boolean normal, Context mContext
//            , final TabMainActivity.CallBack callBack,final int position){
//        //            Picasso.with(mContext).load(url) .into(view);
//        Glide.with(mContext)
//                .load(path)
//                .crossFade()
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model
//                            , Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model,
//                                                   Target<GlideDrawable> target,
//                                                   boolean isFromMemoryCache,
//                                                   boolean isFirstResource) {
//                        callBack.callBack(position,normal,resource);
//                        return false;
//                    }
//                });
//    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    public static byte[] getBitmapBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap = Bitmap.createScaledBitmap(bitmap, 180, 180, true);
        bitmap.compress(Bitmap.CompressFormat.PNG, 30, baos);
        byte[] imageBytes = baos.toByteArray();
        return imageBytes;
    }

}
