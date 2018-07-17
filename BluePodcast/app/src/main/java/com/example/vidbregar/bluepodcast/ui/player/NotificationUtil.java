package com.example.vidbregar.bluepodcast.ui.player;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.vidbregar.bluepodcast.R;

public class NotificationUtil {

    private static final int NOTIFICATION_ID = 1000;
    private static final String NOTIFICATION_CHANNEL_ID = "audio-controls-channel";

    private PlayerService service;
    private Resources resources;

    public NotificationUtil(PlayerService service) {
        this.service = service;
        resources = service.getResources();
    }

    public void startNotification() {
        NotificationManagerCompat.from(service).cancel(NOTIFICATION_ID);
        createNotificationChannel(service);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(service, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Service Running")
                .setContentText("Exoplayer service is running")
                .setSound(null)
                .setChannelId(NOTIFICATION_CHANNEL_ID);
        service.startForeground(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = context.getString(R.string.notification_channel_name);
            String description = context.getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            notificationChannel.setSound(null, null);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void cancelNotification() {
        service.stopForeground(true);
    }
}
