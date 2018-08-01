package com.example.vidbregar.bluepodcast.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.ui.player.PlayerActivity;
import com.example.vidbregar.bluepodcast.ui.player.PlayerConstants;
import com.example.vidbregar.bluepodcast.ui.player.PlayerService;
import com.squareup.picasso.Picasso;

public class BluePodcastWidget extends AppWidgetProvider {

    public static final String WIDGET_EPISODE_TITLE_INTENT_EXTRA = "widget-episode-title-intent-extra";
    public static final String WIDGET_IS_PLAYING_INTENT_EXTRA = "widget-is-playing-intent-extra";
    public static final String WIDGET_THUMBNAIL_URL_INTENT_EXTRA = "widget-thumbnail-url-intent-extra";
    public static final String WIDGET_NO_EPISODE_PLAYING_INTENT_EXTRA = "widget-no-episode-playing-intent-extra";

    public static final String PLAY_BUTTON_CLICKED_ACTION = "com.example.vidbregar.bluepodcast.PLAY_BUTTON_CLICKED_ACTION";
    public static final String PAUSE_BUTTON_CLICKED_ACTION = "com.example.vidbregar.bluepodcast.PAUSE_BUTTON_CLICKED_ACTION";

    private String episodeTitle;
    private String thumbnailUrl;
    private boolean isPlaying;

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.blue_podcast_widget);
        Intent intent = new Intent(context, PlayerActivity.class);
        PendingIntent playerPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_root, playerPendingIntent);

        if (episodeTitle == null) {
            remoteViews.setViewVisibility(R.id.widget_episode_title, View.GONE);
            remoteViews.setViewVisibility(R.id.widget_play_episode, View.GONE);
            remoteViews.setViewVisibility(R.id.widget_pause_episode, View.GONE);
            remoteViews.setViewVisibility(R.id.widget_no_episode_playing, View.VISIBLE);
        } else {
            remoteViews.setViewVisibility(R.id.widget_no_episode_playing, View.GONE);
            remoteViews.setViewVisibility(R.id.widget_episode_title, View.VISIBLE);
            remoteViews.setTextViewText(R.id.widget_episode_title, episodeTitle);
            remoteViews.setViewVisibility(R.id.widget_play_episode, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.widget_pause_episode, View.VISIBLE);
            if (thumbnailUrl != null) {
                Picasso.get()
                        .load(thumbnailUrl)
                        .into(remoteViews, R.id.widget_episode_thumbnail, new int[]{appWidgetId});
            }
            if (isPlaying) {
                remoteViews.setViewVisibility(R.id.widget_play_episode, View.INVISIBLE);
                remoteViews.setViewVisibility(R.id.widget_pause_episode, View.VISIBLE);
                remoteViews.setOnClickPendingIntent(R.id.widget_pause_episode,
                        getPendingSelfIntent(context, PAUSE_BUTTON_CLICKED_ACTION));
            } else {
                remoteViews.setViewVisibility(R.id.widget_pause_episode, View.INVISIBLE);
                remoteViews.setViewVisibility(R.id.widget_play_episode, View.VISIBLE);
                remoteViews.setOnClickPendingIntent(R.id.widget_play_episode,
                        getPendingSelfIntent(context, PLAY_BUTTON_CLICKED_ACTION));
            }
        }

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    private PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action != null && action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

            if (intent.hasExtra(WIDGET_NO_EPISODE_PLAYING_INTENT_EXTRA)) {
                this.episodeTitle = null;
                this.isPlaying = false;
            } else if (intent.hasExtra(WIDGET_EPISODE_TITLE_INTENT_EXTRA) && intent.hasExtra(WIDGET_IS_PLAYING_INTENT_EXTRA)) {
                this.episodeTitle = intent.getStringExtra(WIDGET_EPISODE_TITLE_INTENT_EXTRA);
                this.thumbnailUrl = intent.getStringExtra(WIDGET_THUMBNAIL_URL_INTENT_EXTRA);
                this.isPlaying = intent.getBooleanExtra(WIDGET_IS_PLAYING_INTENT_EXTRA, false);
            }

            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }
        if (action.equals(PLAY_BUTTON_CLICKED_ACTION)) {
            Intent playEpisodeIntent = new Intent(context, PlayerService.class);
            playEpisodeIntent.setAction(PlayerConstants.ACTION_PLAY);
            context.startService(playEpisodeIntent);
        }
        if (action.equals(PAUSE_BUTTON_CLICKED_ACTION)) {
            Intent pauseEpisodeIntent = new Intent(context, PlayerService.class);
            pauseEpisodeIntent.setAction(PlayerConstants.ACTION_PAUSE);
            context.startService(pauseEpisodeIntent);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

