package com.timmy.actionbar.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.timmy.actionbar.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by ps_an on 2016/3/11.
 */
public class ImageUtil {


    public static void load(ImageView view, String uri) {
        load(view, uri, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
    }

    public static void load(ImageView view, String uri, int errId) {
        load(view, uri, errId, errId);
    }

    public static void load(ImageView view, String uri, int errId, int placeholder) {
        //使用Picasso需要判空
        if (!TextUtils.isEmpty(uri)) {
            Picasso.with(view.getContext())
                    .load(uri)
                    .error(errId)
                    .placeholder(placeholder)
                    .into(view);
        } else {
            view.setImageResource(placeholder);
        }
    }

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
