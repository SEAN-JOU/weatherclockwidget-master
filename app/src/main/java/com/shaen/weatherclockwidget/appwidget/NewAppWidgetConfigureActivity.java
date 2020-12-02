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
public class NewAppWidgetConfigureActivity extends Activity {

    public NewAppWidgetConfigureActivity() {
        super();
    }

    public static final int BGCOLOR_TYPE = 100;
    public static final int STICK_TYPE = 101;
    private static final String PREFS_NAME = "com.shaen.weatherclockwidget.appwidget.NewAppWidget";
    static final int gallery_request = 101;
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;
    //  TextView txvplease;
    Spinner bgcolor, stick, city;
    String cityitem;
    Button cancelset, set;
    public int bgcoloritem, stickitem;
    int[] bgcolors = {R.color.gray, R.color.blue, R.color.transparent1, R.color.red, R.color.purple, R.color.brown};

    int[] sticks = {R.drawable.run, R.drawable.confidence, R.drawable.expect,
            R.drawable.happy, R.drawable.plus, R.drawable.love, R.drawable.handsomeboy};

    String[] citys = {"臺北市", "基隆市", "新北市", "高雄市", "南投縣", "桃園市", "嘉義縣", "新竹市"
            , "雲林縣", "金門縣", "苗栗縣", "新竹縣", "嘉義市", "澎湖縣", "屏東縣", "臺中市", "彰化縣",
            "臺東縣", "花蓮縣", "宜蘭縣", "臺南市", "連江縣"};
    String widgetText = null;
    String pictureurl = null;
    ImageView imv;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.new_app_widget_configure);

        imv = findViewById(R.id.imv);
        bgcolor = findViewById(R.id.bgcolor);
        stick = findViewById(R.id.stick);
        city = findViewById(R.id.city);
        cancelset = findViewById(R.id.cancelset);
        set = findViewById(R.id.set);

        WidgetAdapter bgColorAdapter = new WidgetAdapter(this, bgcolors, BGCOLOR_TYPE);
        bgcolor.setAdapter(bgColorAdapter);

        WidgetAdapter stickAdapter = new WidgetAdapter(this, sticks, STICK_TYPE);
        stick.setAdapter(stickAdapter);

        ArrayAdapter cityAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, citys);
        city.setAdapter(cityAdapter);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cityitem = citys[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cityitem = citys[0];
            }
        });


        stick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stickitem = sticks[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                stickitem = sticks[0];
            }
        });

        bgcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bgcoloritem = bgcolors[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                bgcoloritem = bgcolors[0];
            }
        });

        mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.cancel).setOnClickListener(mOnClickListener);
        set.setOnClickListener(mOnClickListener);
        cancelset.setOnClickListener(mOnClickListener);


        set.setVisibility(View.GONE);
        cancelset.setVisibility(View.GONE);

        findViewById(R.id.imv).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                set.setVisibility(View.VISIBLE);
                cancelset.setVisibility(View.VISIBLE);
                return false;
            }});

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == gallery_request && resultCode == RESULT_OK) {

            pictureurl = data.getData().toString();
            imv.setImageURI(Uri.parse(pictureurl));

        }
    }


    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.add_button:
                    final Context context = NewAppWidgetConfigureActivity.this;
                    // When the button is clicked, store the string locally
                    widgetText = mAppWidgetText.getText().toString();

                    saveMotto(context, mAppWidgetId, widgetText);
                    saveBgcolor(context, mAppWidgetId, bgcoloritem);
                    saveStick(context, mAppWidgetId, stickitem);
                    saveCity(context, mAppWidgetId, cityitem);
                    savePictureUrl(context, mAppWidgetId, pictureurl);


                    // It is the responsibility of the configuration activity to update the app widget
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    NewAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
                    // Make sure we pass back the original appWidgetId
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    setResult(RESULT_OK, resultValue);
                    finish();
                    break;

                case R.id.cancel:
                    finish();
                    break;

                case R.id.set:
                    Intent galleryview = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryview, gallery_request);
                    break;

                case R.id.cancelset:
                    pictureurl = null;
                    imv.setImageBitmap(null);
                    break;
            }
        }
    };


    // Write the prefix to the SharedPreferences object for this widget
    static void saveMotto(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor mottoprefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        mottoprefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        mottoprefs.apply();
    }


    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadMotto(Context context, int appWidgetId) {
        SharedPreferences mottoprefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = mottoprefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return null;
        }
    }

    static void savePictureUrl(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor pictureurlprefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        pictureurlprefs.putString(4 + PREF_PREFIX_KEY + appWidgetId, text);
        pictureurlprefs.apply();
    }


    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadPictureUrl(Context context, int appWidgetId) {
        SharedPreferences pictureurlprefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = pictureurlprefs.getString(4 + PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return null;
        }
    }


    static void saveCity(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor cityprefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        cityprefs.putString(3 + PREF_PREFIX_KEY + appWidgetId, text);
        cityprefs.apply();
    }


    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadCity(Context context, int appWidgetId) {
        SharedPreferences cityprefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = cityprefs.getString(3 + PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return null;
        }
    }


    static void saveStick(Context context, int appWidgetId, int text) {
        SharedPreferences.Editor stickprefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        stickprefs.putInt(2 + PREF_PREFIX_KEY + appWidgetId, text);
        stickprefs.apply();
    }

    static int loadStick(Context context, int appWidgetId) {
        SharedPreferences stickprefs = context.getSharedPreferences(PREFS_NAME, 0);
        int titleValue = stickprefs.getInt(2 + PREF_PREFIX_KEY + appWidgetId, 0);
        if (titleValue != 0) {
            return titleValue;
        } else {
            return 0;
        }
    }


    static void saveBgcolor(Context context, int appWidgetId, int text) {
        SharedPreferences.Editor bgcolotprefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        bgcolotprefs.putInt(1 + PREF_PREFIX_KEY + appWidgetId, text);
        bgcolotprefs.apply();
    }

    static int loadBgcolor(Context context, int appWidgetId) {
        SharedPreferences bgcolotprefs = context.getSharedPreferences(PREFS_NAME, 0);
        int titleValue = bgcolotprefs.getInt(1 + PREF_PREFIX_KEY + appWidgetId, 0);
        if (titleValue != 0) {
            return titleValue;
        } else {
            return 0;
        }
    }


    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.remove(1 + PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }


}

