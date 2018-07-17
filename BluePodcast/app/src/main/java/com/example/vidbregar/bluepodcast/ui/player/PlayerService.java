package com.example.vidbregar.bluepodcast.ui.player;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class PlayerService extends Service implements AudioManager.OnAudioFocusChangeListener, Player.EventListener {

    public static final String ACTION_PLAY = "action-play";
    public static final String ACTION_PAUSE = "action-pause";
    public static final String ACTION_STOP = "action-stop";

    private static final String IDLE = "player-idle";
    private static final String STOPPED = "player-stopped";
    private static final String PLAYING = "player-playing";
    private static final String LOADING = "player-loading";
    private static final String PAUSED = "player-paused";

    public SimpleExoPlayer simpleExoPlayer;
    private String audioUrl;
    private String playerStatus;
    private AudioManager audioManager;
    private NotificationUtil notificationUtil;
    private MediaSessionCompat mediaSession;
    private MediaControllerCompat.TransportControls transportControls;
    private final IBinder playerBinder = new PlayerBinder();

    public class PlayerBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }

    private MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback() {
        @Override
        public void onPause() {
            super.onPause();
            pause();
        }

        @Override
        public void onStop() {
            super.onStop();
            stop();
            notificationUtil.cancelNotification();
        }

        @Override
        public void onPlay() {
            super.onPlay();
            play();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        notificationUtil = new NotificationUtil(this);

        mediaSession = new MediaSessionCompat(this, getClass().getSimpleName());
        transportControls = mediaSession.getController().getTransportControls();
        mediaSession.setActive(true);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setCallback(mediaSessionCallback);

        RenderersFactory renderersFactory = new DefaultRenderersFactory(getApplicationContext());
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);
        simpleExoPlayer.addListener(this);

        playerStatus = IDLE;
    }

    public void playOrPause(String url) {
        if (audioUrl != null && audioUrl.equals(url)) {
            play();
        } else {
            initializePlayer(url);
        }
    }

    public void initializePlayer(String audioUrl) {
        this.audioUrl = audioUrl;

        MediaSource mediaSource = buildMediaSource(Uri.parse(audioUrl));
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private MediaSource buildMediaSource(Uri audioUrl) {
        final String userAgent = Util.getUserAgent(getApplicationContext(), "Blue Podcast");
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(audioUrl);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (TextUtils.isEmpty(action))
            return START_NOT_STICKY;

        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            stop();
            return START_NOT_STICKY;
        }

        if (action.equalsIgnoreCase(ACTION_PLAY)) {
            transportControls.play();

        } else if (action.equalsIgnoreCase(ACTION_PAUSE)) {

            if (playerStatus.equals(STOPPED)) {
                transportControls.stop();
            } else {
                transportControls.pause();
            }

        } else if (action.equalsIgnoreCase(ACTION_STOP)) {
            transportControls.stop();
            notificationUtil.cancelNotification();
        }

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return playerBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (playerStatus.equals(IDLE)) stopSelf();
        return super.onUnbind(intent);

    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                simpleExoPlayer.setVolume(1f);
                resume();
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                stop();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (isPlaying())
                    pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (isPlaying())
                    simpleExoPlayer.setVolume(0.1f);
                break;
        }
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                playerStatus = LOADING;
                break;
            case Player.STATE_ENDED:
                playerStatus = STOPPED;
                break;
            case Player.STATE_IDLE:
                playerStatus = IDLE;
                break;
            case Player.STATE_READY:
                playerStatus = playWhenReady ? PLAYING : PAUSED;
                break;
            default:
                playerStatus = IDLE;
                break;
        }

        if (!playerStatus.equals(IDLE))
            notificationUtil.startNotification();
    }

    public String getPlayerStatus() {
        return playerStatus;
    }

    public void play() {
        simpleExoPlayer.setPlayWhenReady(true);
    }

    public void pause() {
        simpleExoPlayer.setPlayWhenReady(false);
        audioManager.abandonAudioFocus(this);
    }

    public void resume() {
        if (audioUrl != null)
            play();
    }

    public void stop() {
        simpleExoPlayer.stop();
        audioManager.abandonAudioFocus(this);
    }

    @Override
    public void onDestroy() {
        pause();
        simpleExoPlayer.release();
        simpleExoPlayer.removeListener(this);
        mediaSession.release();
        notificationUtil.cancelNotification();
        super.onDestroy();
    }

    public MediaSessionCompat getMediaSession() {
        return mediaSession;
    }

    public boolean isPlaying() {
        return playerStatus.equals(PLAYING);
    }

    // @formatter:off
    @Override
    public void onPlayerError(ExoPlaybackException error) { }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) { }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) { }

    @Override
    public void onLoadingChanged(boolean isLoading) { }

    @Override
    public void onRepeatModeChanged(int repeatMode) { }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) { }

    @Override
    public void onPositionDiscontinuity(int reason) { }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) { }

    @Override
    public void onSeekProcessed() { }
    // @formatter:on
}