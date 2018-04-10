package com.example.tanishqsaluja.remindme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.os.Vibrator;
import java.net.URI;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by tanishqsaluja on 16/3/18.
 */

public class MyReceiver extends BroadcastReceiver {
    NotesDB notesDB;
    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
       // v.vibrate(50000);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(5000,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(5000);
        }
        Log.e("TEST","inside receive method");
        Intent i = new Intent(context, MainActivity.class);
        int id=intent.getIntExtra("noteid",0);
        PendingIntent pendingIntent=PendingIntent.getActivity(context, (int) System.currentTimeMillis(),i,0);
        Notification notification = new NotificationCompat.Builder(context, "test")
                .setContentTitle("Time for the task : "+intent.getStringExtra("title"))
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[] { 0, 5000,1000,5000,1000 })
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(false)
                .setCategory(Notification.CATEGORY_CALL)
                .setDefaults(Notification.FLAG_INSISTENT)
                //.setOngoing(true)
                .build();
        notification.sound = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.ring);
        notification.defaults|= Notification.DEFAULT_VIBRATE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(123, notification);
    }
}
