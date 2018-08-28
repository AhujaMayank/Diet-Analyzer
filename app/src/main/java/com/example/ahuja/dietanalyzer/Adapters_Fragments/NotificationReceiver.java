package com.example.ahuja.dietanalyzer.Adapters_Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.example.ahuja.dietanalyzer.AddWeight;
import com.example.ahuja.dietanalyzer.R;

public class NotificationReceiver extends BroadcastReceiver {
    final int NOTIF_ID=121234;
    final String CHANNEL_ID="1";
    @Override
    public void onReceive(Context context, Intent intent1) {
        Intent intent = new Intent(context, AddWeight.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        android.support.v4.app.NotificationCompat.Builder cBuilder=new android.support.v4.app.NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentTitle("Log Data")
                .setContentText("Seems you have forgotten to log data")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(android.support.v4.app.NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIF_ID,cBuilder.build());

    }

}
