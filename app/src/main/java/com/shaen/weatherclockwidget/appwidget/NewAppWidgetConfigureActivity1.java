package com.shaen.weatherclockwidget.appwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    ProgressBar pbr;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setResult(RESULT_CANCELED);
        setContentView(R.layout.new_app_widget_configure1);
        pbr = findViewById(R.id.pbr);
        pbr.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() {
                    finish();
                }
            }.start();
            return;
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        NewAppWidget1.updateAppWidget(context, appWidgetManager, mAppWidgetId);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);

        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                finish();
            }
        }.start();
    }
}

