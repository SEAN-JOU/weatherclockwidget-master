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
//import java.util.Calendar;
//import java.util.List;
//import static com.shaen.weatherclockwidget.appwidget.NewAppWidget.UpdateTimeService.getBitmapFormUri;


public class NewAppWidget1 extends AppWidgetProvider {


    public static final int IMAGE_SIZE = 10;
    public static final String url =
            "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=e6831708-02b4-4ef8-98fa-4b4ce53459d9";
    public static final String ACTION_AUTO_UPDATE = "com.shaen.weatherclockwidget.AUTO_UPDATE";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {


        CharSequence widgetText = NewAppWidgetConfigureActivity1.loadMotto(context, appWidgetId);
        CharSequence pictureurl = NewAppWidgetConfigureActivity1.loadPictureUrl(context, appWidgetId);
        String city = NewAppWidgetConfigureActivity1.loadCity(context, appWidgetId);
        int bgcolor = NewAppWidgetConfigureActivity1.loadBgcolor(context, appWidgetId);
        int stick = NewAppWidgetConfigureActivity1.loadStick(context, appWidgetId);

        SharedPreferences bgcolorpref = context.getSharedPreferences("bgcolorpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor bgcolored = bgcolorpref.edit();
        bgcolored.putInt("bgcolor", bgcolor);
        bgcolored.commit();
        SharedPreferences stickpref = context.getSharedPreferences("stickpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor sticked = stickpref.edit();
        sticked.putInt("stick", stick);
        sticked.commit();
        SharedPreferences widgetTextpref = context.getSharedPreferences("widgetTextpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor widgetTexted = widgetTextpref.edit();
        widgetTexted.putString("widgetText", String.valueOf(widgetText));
        widgetTexted.commit();

        SharedPreferences pictureurlpref = context.getSharedPreferences("pictureurlpref", Context.MODE_PRIVATE);
        SharedPreferences.Editor pictureurled = pictureurlpref.edit();
        pictureurled.putString("pictureurl", String.valueOf(pictureurl));
        pictureurled.commit();
        SharedPreferences cityprefs = context.getSharedPreferences("citypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor cityed = cityprefs.edit();
        cityed.putString("city", city);
        cityed.commit();


//        Calendar mCalendar = Calendar.getInstance();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        Intent intent = new Intent(context, ScanActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Intent intent1 = new Intent(context, AlarmActivity.class);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, intent1, 0);
        views.setOnClickPendingIntent(R.id.imv, pendingIntent);
        views.setOnClickPendingIntent(R.id.textclock, pendingIntent1);


        Log.d("pictureurl", String.valueOf(pictureurl));

        views.setTextViewText(R.id.textView, widgetText);
        views.setImageViewResource(R.id.imv1, bgcolor);
        if (pictureurl != null) {
            try {
                views.setImageViewBitmap(R.id.imv,getBitmapFormUri(context,Uri.parse(String.valueOf(pictureurl))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(pictureurl == null) {
            try {
                views.setImageViewBitmap(R.id.imv, BitmapFactory.decodeResource(context.getResources(), stick));
            }catch (Exception e){

            }
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            NewAppWidgetConfigureActivity1.deleteTitlePref(context, appWidgetId);
        }

//        context.stopService(new Intent(context, UpdateTimeService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

//        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
//        appWidgetAlarm.startAlarm();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, UpdateTimeService.class));
//        } else {
//            context.startService(new Intent(context, UpdateTimeService.class));
//        }


    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

//        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
//        appWidgetAlarm.startAlarm();
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, UpdateTimeService.class));
//        } else {
//            context.startService(new Intent(context, UpdateTimeService.class));
//        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);


//        context.stopService(new Intent(context, UpdateTimeService.class));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

//        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
//        appWidgetAlarm.startAlarm();


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(new Intent(context, UpdateTimeService.class));
//        } else {
//            context.startService(new Intent(context, UpdateTimeService.class));
//        }
    }

//    public static final class UpdateTimeService extends JobIntentService {
//
//
//        public List<WeatherData.WeatherDataResults> results = null;
//        public List<WeatherData.WeatherDataResults> location = null;
//
//
//        public Handler handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                switch (msg.what) {
//                    case SystemCode.OkhttpFailure:
//
//                        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//
//                        if (mNetworkInfo != null) {
//                            if (mNetworkInfo.isConnected()) {
//                                Toast.makeText(UpdateTimeService.this, "fail", Toast.LENGTH_LONG).show();
//                                stopUpdate();
//                            } else {
//                                Toast.makeText(UpdateTimeService.this, "檢查網路是否連線", Toast.LENGTH_LONG).show();
//                                stopUpdate();
//                            }
//                        }
//
//                        break;
//                    case SystemCode.OkhttpSuccess:
//
//                        SharedPreferences citypref = getSharedPreferences("citypref", Context.MODE_PRIVATE);
//                        String city = citypref.getString("city", "");
//
//                        WeatherData weatherData = new Gson().fromJson(String.valueOf(msg.obj), WeatherData.class);
//                        WeatherData.WeatherDataResult result = weatherData.result;
//
//                        results = result.results;
//
//                        location = null;
//                        results = null;
//
//                        try {
//                            for (int i = 0; i < results.size(); i++) {
//
//                                if (city.toString().equals(results.get(i).locationName.toString())) {
//
//                                    location.add(results.get(i));
//                                }
//                            }
//
//                            break;
//                        } catch (Exception e) {
//                        }
//                }
//
//                return false;
//            }
//        });

//        private Calendar mCalendar;
//        private final static IntentFilter mIntentFilter = new IntentFilter();
//
//        static {
//            mIntentFilter.addAction(Intent.ACTION_TIME_TICK);
//            mIntentFilter.addAction(Intent.ACTION_TIME_CHANGED);
//            mIntentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
//        }
//
//        @Override
//        public void onCreate() {
//            super.onCreate();
//
//            mCalendar = Calendar.getInstance();
//
//            try {
//                updateTime();}
//            catch (Exception e){}


//            try {
//                OkhttpUtils.getInstance().getmethod(handler, url);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            updateWeather();


//            registerReceiver(mTimeChangedReceiver, mIntentFilter);
//
//        }
//
//        @Override
//        public void onDestroy() {
//            super.onDestroy();
//            unregisterReceiver(mTimeChangedReceiver);
//
//        }
//
//        @Override
//        public int onStartCommand(Intent intent, int flags, int startId) {
//            super.onStartCommand(intent, flags, startId);
//            AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(this.getApplicationContext());
//            appWidgetAlarm.startAlarm();

//            try {
//                updateTime();}
//            catch (Exception e){}
//            try {
//                OkhttpUtils.getmethod(handler, url);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            updateWeather();

//            return START_STICKY;
//        }

//        @Override
//        protected void onHandleWork(@NonNull Intent intent) {
//            AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(this.getApplicationContext());
//            appWidgetAlarm.startAlarm();
//            try {
//                updateTime();}
//            catch (Exception e){}

//            location = null;
//            results = null;
//            results = new ArrayList<>();
//            location = new ArrayList<>();
//            try {
//
//                Thread.sleep(20000);
//                OkhttpUtils.getmethod(handler, url);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                updateWeather();
//            }

//        }
//
//        private final BroadcastReceiver mTimeChangedReceiver = new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                try {
//                    updateTime();}
    //               catch (Exception e){}
//                try {
//                    OkhttpUtils.getmethod(handler, url);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                updateWeather();
//


//       } };

//        private void updateTime() {
//
//
//            mCalendar.setTimeInMillis(System.currentTimeMillis());
//            RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.new_app_widget);
//
//            SharedPreferences bgcolorpref = getSharedPreferences("bgcolorpref", Context.MODE_PRIVATE);
//            SharedPreferences widgetTextpref = getSharedPreferences("widgetTextpref", Context.MODE_PRIVATE);
//            SharedPreferences stickpref = getSharedPreferences("stickpref", Context.MODE_PRIVATE);
//            SharedPreferences citypref = getSharedPreferences("citypref", Context.MODE_PRIVATE);
//            SharedPreferences pictureurlpref = getSharedPreferences("pictureurlpref", Context.MODE_PRIVATE);
//
//            int stick = stickpref.getInt("stick", R.drawable.run);
//            int bgcolor = bgcolorpref.getInt("bgcolor", R.color.whiteword);
//            String widgetText = widgetTextpref.getString("widgetText", "");
//            String city = citypref.getString("city", "");
//            String pictureurl = pictureurlpref.getString("pictureurl", "");
//
//            mRemoteViews.setTextViewText(R.id.location, city);
//            mRemoteViews.setTextViewText(R.id.textView, widgetText);
//            mRemoteViews.setImageViewResource(R.id.imv1, bgcolor);
//
//            if (pictureurl != null) {
//                try {
//                    mRemoteViews.setImageViewBitmap(R.id.imv,getBitmapFormUri(UpdateTimeService.this,Uri.parse(String.valueOf(pictureurl))));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            } else {
//                try {
//                    mRemoteViews.setImageViewBitmap(R.id.imv, BitmapFactory.decodeResource(getResources(), stick));
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            mRemoteViews.setViewVisibility(R.id.progressBar, View.INVISIBLE);
//            Intent intent = new Intent(UpdateTimeService.this, ChatActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(UpdateTimeService.this, 0, intent, 0);
//            mRemoteViews.setOnClickPendingIntent(R.id.imv, pendingIntent);
//
//            ComponentName mComponentName = new ComponentName(this, NewAppWidget.class);
//            AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(this);
//            mAppWidgetManager.updateAppWidget(mComponentName, mRemoteViews);
//
//
//        }
//
//
//        public class TimeAsyncTask extends AsyncTask<Integer, Integer, String> {
//            @Override
//            protected String doInBackground(Integer... integers) {
//                int n = integers[0];
//                int i;
//
//                try {
//                    OkhttpUtils.getmethod(handler, url);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                for (i = n; i >= 0; i--) {
//                    try {
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return "OK";
//            }
//
//            @Override
//            protected void onProgressUpdate(Integer... values) {
//                super.onProgressUpdate(values);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                updateTime();
//                updateWeather();
//                TimeAsyncTask timeAsyncTask = new TimeAsyncTask();
//                timeAsyncTask.execute(1);
//
//            }
//
//            @Override
//            protected void onCancelled(String s) {
//                super.onCancelled(s);
//            }
//
//            @Override
//            protected void onCancelled() {
//                super.onCancelled();
//            }
//        }
//
//
        public static Bitmap getBitmapFormUri(Context ac,Uri uri) throws IOException {
            InputStream input = ac.getContentResolver().openInputStream(uri);
            BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
            onlyBoundsOptions.inJustDecodeBounds = true;
            onlyBoundsOptions.inDither = true;//optional
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
            input.close();
            int originalWidth = onlyBoundsOptions.outWidth;
            int originalHeight = onlyBoundsOptions.outHeight;
            if ((originalWidth == -1) || (originalHeight == -1))
                return null;
            //图片分辨率以480x800为标准
            float hh = 800f;//这里设置高度为800f
            float ww = 480f;//这里设置宽度为480f
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (originalWidth / ww);
            } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (originalHeight / hh);
            }
            if (be <= 0)
                be = 1;
            //比例压缩
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = be;//设置缩放比例
            bitmapOptions.inDither = true;//optional
            bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
            input = ac.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
            input.close();

            return compressImage(bitmap);//再进行质量压缩
        }

        public static Bitmap compressImage(Bitmap image) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            return bitmap;
        }




//        private void updateWeather() {
//
//            RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.new_app_widget);
//
//            try {
//                mRemoteViews.setTextViewText(R.id.temperatureday1, location.get(1).parameterName3 + "/" + location.get(0).parameterName2 + "°C");
//                mRemoteViews.setTextViewText(R.id.day1, "today");
//                mRemoteViews.setImageViewBitmap(R.id.weatherday1, WeatherFilter.weatherchange(UpdateTimeService.this, location.get(1).parameterName1));
//
//
//                mRemoteViews.setTextViewText(R.id.temperatureday2, location.get(3).parameterName3 + "/" + location.get(2).parameterName2 + "°C");
//                String[] g = location.get(2).startTime.split("T");
//                String[] describe1 = g[0].split("-");
//                mRemoteViews.setTextViewText(R.id.day2, describe1[1] + "-" + describe1[2]);
//                mRemoteViews.setImageViewBitmap(R.id.weatherday2, WeatherFilter.weatherchange(UpdateTimeService.this, location.get(3).parameterName1));
//
//
//                mRemoteViews.setTextViewText(R.id.temperatureday3, location.get(5).parameterName3 + "/" + location.get(4).parameterName2 + "°C");
//                String[] g1 = location.get(4).startTime.split("T");
//                String[] describe2 = g1[0].split("-");
//                mRemoteViews.setTextViewText(R.id.day3, describe2[1] + "-" + describe2[2]);
//                mRemoteViews.setImageViewBitmap(R.id.weatherday3, WeatherFilter.weatherchange(UpdateTimeService.this, location.get(5).parameterName1));
//
//
//                mRemoteViews.setTextViewText(R.id.temperatureday4, location.get(7).parameterName3 + "/" + location.get(6).parameterName2 + "°C");
//                String[] g2 = location.get(6).startTime.split("T");
//                String[] describe3 = g2[0].split("-");
//                mRemoteViews.setTextViewText(R.id.day4, describe3[1] + "-" + describe3[2]);
//                mRemoteViews.setImageViewBitmap(R.id.weatherday4, WeatherFilter.weatherchange(UpdateTimeService.this, location.get(7).parameterName1));
//
//
//                mRemoteViews.setTextViewText(R.id.temperatureday5, location.get(9).parameterName3 + "/" + location.get(8).parameterName2 + "°C");
//                String[] g3 = location.get(8).startTime.split("T");
//                String[] describe4 = g3[0].split("-");
//                mRemoteViews.setTextViewText(R.id.day5, describe4[1] + "-" + describe4[2]);
//                mRemoteViews.setImageViewBitmap(R.id.weatherday5, WeatherFilter.weatherchange(UpdateTimeService.this, location.get(9).parameterName1));
//
//                ComponentName mComponentName = new ComponentName(this, NewAppWidget.class);
//                AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(this);
//                mAppWidgetManager.updateAppWidget(mComponentName, mRemoteViews);
//            } catch (Exception e) {}}

//        private void stopUpdate() {
//
//            RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.new_app_widget);
//            mRemoteViews.setViewVisibility(R.id.progressBar, View.INVISIBLE);
//            ComponentName mComponentName = new ComponentName(this, NewAppWidget.class);
//            AppWidgetManager mAppWidgetManager = AppWidgetManager.getInstance(this);
//            mAppWidgetManager.updateAppWidget(mComponentName, mRemoteViews);
//
//
//        }
//
}