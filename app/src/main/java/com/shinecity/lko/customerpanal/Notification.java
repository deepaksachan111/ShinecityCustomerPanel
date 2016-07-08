package com.shinecity.lko.customerpanal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by QServer on 6/24/2016.
 */
public class Notification extends Service {

  private   String ramdom;

  /*  @Override
    public void onCreate() {
        super.onCreate();

        Intent mainIntent = new Intent(this, MainActivity.class);

        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        android.app.Notification noti = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle("Title")
                .setContentText("It's been so Long!!!")
                .setSubText("Please return back to App")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setSmallIcon(R.mipmap.splash)
                .setTicker("Important Notification")
                .setWhen(System.currentTimeMillis())
                .build();

        notificationManager.notify(0, noti);


    }*/

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        ArrayList<String> getdatas = getdatalist();

        for(String s : getdatas){

            ramdom = s;
        }

       Intent mainIntent = new Intent(this, MainActivity.class);

        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        android.app.Notification noti = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle("ShineCity")
                .setContentText(ramdom)
                .setSubText("Please return back to App")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setSmallIcon(R.mipmap.splash)
                .setTicker("Shinecity Notification")
                .setWhen(System.currentTimeMillis())
                .build();

        notificationManager.notify(0, noti);
        return START_STICKY;
    }

private ArrayList<String> getdatalist(){
    ArrayList<String> getdata = new ArrayList<>();

    getdata .add("Welcome User");
    getdata.add("Have a Nice Day");
    getdata.add("Get a bonous");
    getdata.add("Collect Coines");
    Collections.shuffle(getdata);

    return getdata;
}

}