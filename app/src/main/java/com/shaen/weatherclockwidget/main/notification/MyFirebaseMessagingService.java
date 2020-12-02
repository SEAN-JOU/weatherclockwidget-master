package com.shaen.weatherclockwidget.main.notification;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.main.MainActivity;
import com.shaen.weatherclockwidget.main.notification.adapter.DataAdapter;
import com.shaen.weatherclockwidget.other.ActivityController;


/**
 * Created by jou on 2017/12/25.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

   String title,content,video,web,photo,cover;
    public DataAdapter dbAdapter;
    SharedPreferences sp;
   public static final int CHANNEL_ID= 101;

    public void onCreate(){
        super.onCreate();
        sp = getSharedPreferences("token",MODE_PRIVATE);

        //aaaaaa
//        if (sp.getString("token","") == null) {
//        DataDetail dataDetail = new DataDetail("","","","","","");
//        dbAdapter=new DataAdapter(MyFirebaseMessagingService.this);
//        dbAdapter.add(dataDetail);}
    }


    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        SharedPreferences.Editor ed = sp.edit();
        ed.commit();
        sp.edit().putString("token","token");

        title = remoteMessage.getData().get("title");
        content =remoteMessage.getData().get("content");
        video =remoteMessage.getData().get("video");
        web=remoteMessage.getData().get("web");
        photo=remoteMessage.getData().get("photo");
        cover=remoteMessage.getData().get("cover");

        sendNotification(content,title);

        if(!String.valueOf(title).equals(String.valueOf("null")))
        {
            DataDetail dataDetail = new DataDetail(title,content,video,web,photo,cover);
            dbAdapter=new DataAdapter(ActivityController.mainActivity);
            dbAdapter.add(dataDetail);
        }
        else {
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void sendNotification(String content, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification.Builder newMessageNotification = new Notification.Builder(MyFirebaseMessagingService.this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(0 /* ID of notification */,newMessageNotification.build());
    }

}







