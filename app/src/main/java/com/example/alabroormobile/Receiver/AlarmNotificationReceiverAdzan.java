package com.example.alabroormobile.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.alabroormobile.HomeMenu.JadwalPetugas.JadwalPetugasActivity;
import com.example.alabroormobile.R;

public class AlarmNotificationReceiverAdzan extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(false)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_alabroor_round)
                .setContentTitle("Adzan Sedang Berkumandang")
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.adzan2));

        Intent notificationIntent = new Intent(context, JadwalPetugasActivity.class);
        PendingIntent intent1 = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(intent1);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
