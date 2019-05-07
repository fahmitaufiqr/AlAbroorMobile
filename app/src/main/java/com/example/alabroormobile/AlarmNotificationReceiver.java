package com.example.alabroormobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.alabroormobile.homeMenu.JadwalPetugas.JadwalPetugasActivity;
import com.example.alabroormobile.homeMenu.Ramadhan.JadwalRamadhanActivity;

public class AlarmNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        String muazin = intent.getStringExtra("muazin");
        String imam = intent.getStringExtra("imam");
        String qultim= intent.getStringExtra("qultum");
        String text = "Muazin: " + muazin + "\n" +
                        "Imam: " + imam + "\n" +
                        "Qultum: " + qultim;

        builder.setAutoCancel(false)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_alabroor_round)
                .setContentTitle("5 Menit Lagi Adzan Berkumandang")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.android_sms));

        Intent notificationIntent = new Intent(context, JadwalRamadhanActivity.class);
        PendingIntent intent1 = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(intent1);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
