package com.shaen.weatherclockwidget.appwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.appwidget.adapter.WidgetAdapter;


/**
 * The configuration screen for the {@link NewAppWidget NewAppWidget} AppWidget.
 */
public class NewAppWidgetConfigureActivity1 extends Activity {

    public NewAppWidgetConfigureActivity1() {
        super();
    }
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    final Context context = NewAppWidgetConfigureActivity1.this;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setResult(RESULT_CANCELED);
        setContentView(R.layout.new_app_widget_configure1);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        NewAppWidget1.updateAppWidget(context, appWidgetManager, mAppWidgetId);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            finish();
        }
    }
}

