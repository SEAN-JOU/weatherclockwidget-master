package com.shaen.weatherclockwidget.main.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.appwidget.NewAppWidget;


/**
 * Created by NB004 on 2018/6/1.
 */

public class WeatherFilter {

    public static Bitmap weatherchange(Context context, String s) {

        if (s.contains("雷")) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                return drawableToBitmap(context, R.drawable.wthunder);
            } else {
                return drawableToBitmap(R.drawable.wthunder, context);
            }
        } else if (s.contains("雨")) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                return drawableToBitmap(context, R.drawable.wrain);
            } else {
                return drawableToBitmap(R.drawable.wrain, context);
            }
        } else if (s.contains("大雨")) {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                return drawableToBitmap(context, R.drawable.wbigrain);
            } else {
                return drawableToBitmap(R.drawable.wbigrain, context);
            }
        }else if (s.contains("晴") && s.contains("雲")) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                return drawableToBitmap(context, R.drawable.wsuncloud);
            } else {
                return drawableToBitmap(R.drawable.wsuncloud, context);
            }
        }
        else if (s.contains("雲") || s.contains("陰")) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                return drawableToBitmap(context, R.drawable.wcloud);
            } else {
                return drawableToBitmap(R.drawable.wcloud, context);
            }
        } else if (s.contains("雪")) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                return drawableToBitmap(context, R.drawable.wsnow);
            } else {
                return drawableToBitmap(R.drawable.wsnow, context);
            }
        }  else if (s.contains("晴")) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                return drawableToBitmap(context, R.drawable.wsun);
            } else {
                return drawableToBitmap(R.drawable.wsun, context);
            }
        }

        return null;


    }

    public static Bitmap drawableToBitmap(int drawable, Context context) {

        return BitmapFactory.decodeResource(context.getResources(), drawable);
    }


    public static Bitmap drawableToBitmap(Context context, int drawable) {


        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), drawable, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), drawable, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > NewAppWidget.IMAGE_SIZE || width > NewAppWidget.IMAGE_SIZE) {

            final int halfHeight = height / 5;
            final int halfWidth = width / 5;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= NewAppWidget.IMAGE_SIZE
                    || (halfWidth / inSampleSize) >= NewAppWidget.IMAGE_SIZE) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


}
