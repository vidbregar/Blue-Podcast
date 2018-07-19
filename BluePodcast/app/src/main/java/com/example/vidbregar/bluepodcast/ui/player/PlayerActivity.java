package com.example.vidbregar.bluepodcast.ui.player;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.data.Channel;
import com.example.vidbregar.bluepodcast.model.data.Episode;
import com.example.vidbregar.bluepodcast.util.SharedPreferencesUtil;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerActivity extends AppCompatActivity {

    private Channel podcast;
    private Episode episode;
    private PlayerService playerService;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private boolean isBound;

    @BindView(R.id.player_view)
    PlayerControlView playerControlView;
    @BindView(R.id.episode_thumbnail)
    CircleImageView episodeThumbnail;
    @BindView(R.id.episode_title)
    TextView episodeTitle;
    @BindView(R.id.episode_publisher)
    TextView episodePublisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        sharedPreferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        sharedPreferencesUtil.setIsApplicationAlive(true);
        setUpMocks();
        bindViews();
    }

    private void bindViews() {
        Picasso.get().load(podcast.getThumbnailUrl()).into(episodeThumbnail);
        episodeTitle.setText(episode.getTitle());
        episodePublisher.setText(podcast.getPublisher());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Using startService() overrides the default service lifetime that is managed
        // by bindService(Intent, ServiceConnection, int): it requires the service to remain
        // running until stopService(Intent) is called, regardless of whether any clients
        // are connected to it.
        startService(new Intent(this, PlayerService.class));
        bindService(new Intent(this, PlayerService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void preparePlayer() {
        if (isBound) {
            playerService.playOrPause(episode.getAudioUrl());
            playerControlView.setPlayer(playerService.simpleExoPlayer);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferencesUtil.setIsApplicationAlive(false);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            PlayerService.PlayerBinder binder = (PlayerService.PlayerBinder) service;
            playerService = binder.getService();
            isBound = true;
            preparePlayer();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound  = false;
        }
    };

    private void setUpMocks() {
        podcast = new Channel("66af84d930be41ceb24a8d7a7eaa363b",
                "https://d3sv2eduhewoas.cloudfront.net/channel/image/7631d83e3f86406abd022d107fac767f.jpg",
                "1530738134000",
                "https://www.risetogetherpodcast.com/episodes/?utm_source=listennotes.com&utm_campaign=Listen+Notes&utm_medium=website",
                "Rachel & Dave Hollis",
                "RISE Together",
                "RISE Together : A Couples Podcast with Rachel & Dave Hollis");
        episode = new Episode("The Set Up",
                "1530738134000",
                "<p>We have gotten so many requests about doing a podcast together that we needed to make it happen! In this episode we share a little bit of background of who we are and why we started to work together and move to Austin.<\\/p><p>Come to our conference: <a href=\\\"https://www.letsrise.co/together/\\\">https://www.letsrise.co/together/<\\/a><\\/p><p>Follow Rachel Hollis on Instagram: <a href=\\\"https://www.instagram.com/msrachelhollis/\\\">https://www.instagram.com/msrachelhollis/<\\/a><\\/p><p>Follow Dave Hollis on Instagram: <a href=\\\"https://www.instagram.com/mrdavehollis/\\\">https://www.instagram.com/mrdavehollis/<\\/a><\\/p><p><\\/p>",
                1640,
                "ba3d85bcdfc34b6c8cdef8729e695e69",
                "http://static1.squarespace.com/static/5b3a787d4eddec498cf87b6c/t/5b3d340403ce6479dfe3c42e/1530737792419/01+The+Set+Up.mp3");
    }
}
