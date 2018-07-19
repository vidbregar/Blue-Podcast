package com.example.vidbregar.bluepodcast.ui.player;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import static com.example.vidbregar.bluepodcast.ui.player.PlayerConstants.*;

import com.example.vidbregar.bluepodcast.R;


public class NotificationUtil {

    private static final int NOTIFICATION_ID = 1000;
    private static final String NOTIFICATION_CHANNEL_ID = "audio-controls-channel";

    private PlayerService service;

    public NotificationUtil(PlayerService service) {
        this.service = service;
    }

    private PendingIntent createAction(String action, int requestCode) {
        Intent intent = new Intent(service, PlayerService.class);
        intent.setAction(action);
        return PendingIntent.getService(service, requestCode, intent, 0);
    }

    public void startNotification(String playerStatus) {
        int playPauseIcon;
        PendingIntent playPauseAction;

        if (playerStatus.equals(PAUSED)) {
            playPauseAction = createAction(ACTION_PAUSE, REQUEST_CODE_PAUSE);
            playPauseIcon = R.drawable.ic_notification_play;
        } else {
            playPauseAction = createAction(ACTION_PLAY, REQUEST_CODE_PLAY);
            playPauseIcon = R.drawable.ic_notification_pause;
        }

        int closeIcon = R.drawable.ic_notification_close;
        PendingIntent closeAction = createAction(ACTION_STOP, REQUEST_CODE_STOP);

        Intent intent = new Intent(service, PlayerActivity.class);
        PendingIntent playerPendingIntent = PendingIntent.getActivity(service, 0, intent, 0);

        // Dismiss previous notification
        NotificationManagerCompat.from(service).cancel(NOTIFICATION_ID);

        createNotificationChannel(service);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(service, NOTIFICATION_CHANNEL_ID)
                .setContentIntent(playerPendingIntent)
                .setSmallIcon(R.drawable.podcast_image)
                .setContentTitle("Service Running")
                .setContentText("Exoplayer service is running")
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setSound(null)
                .addAction(playPauseIcon, "play pause", playPauseAction)
                .addAction(closeIcon, "close", closeAction)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(service.getMediaSession().getSessionToken())
                        .setShowActionsInCompactView(0, 1));
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
            notificationChannel.setDescription(description);

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
