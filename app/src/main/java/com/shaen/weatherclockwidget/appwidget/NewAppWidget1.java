package com.shaen.weatherclockwidget.appwidget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.main.AlarmActivity;
import com.shaen.weatherclockwidget.scan.ScanActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class NewAppWidget1 extends AppWidgetProvider {


    public static final int IMAGE_SIZE = 10;
    public static final String url =
            "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=e6831708-02b4-4ef8-98fa-4b4ce53459d9";
    public static final String ACTION_AUTO_UPDATE = "com.shaen.weatherclockwidget.AUTO_UPDATE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget1);
        Intent intent = new Intent(context, ScanActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.imv, pendingIntent);
        views.setImageViewBitmap(R.id.imv,BitmapFactory.decodeResource(context.getResources(),R.drawable.qrcode));
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}