package com.example.alabroormobile.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.alabroormobile.R;
import com.example.alabroormobile.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseInstanceIdService extends FirebaseMessagingService {
    private String TAG = FirebaseInstanceIdService.class.getSimpleName();

    @Override
    public void onNewToken(String s) {
        Log.d("TAG", "onNewToken: " + s);
        sendRegistrationToServer(s);
    }
    private void sendRegistrationToServer(String token) {

    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        try {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d(TAG, "onMessageReceived: "+title+body);
            notifShow(title,body);
        }catch (Exception e){
            Log.e(TAG, "onMessageReceived: "+e.getMessage());
        }
    }

    private void notifShow(String title, String body){
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("notif","notif");
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.drawable.ic_launcher_background) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(body)// message for notification
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setFullScreenIntent(pi, false)
                .setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }
}