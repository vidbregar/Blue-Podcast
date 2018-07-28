package com.example.vidbregar.bluepodcast.ui.player;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.database.episode.EpisodeDatabase;
import com.example.vidbregar.bluepodcast.model.database.episode.EpisodeEntity;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;
import com.example.vidbregar.bluepodcast.ui.main.MainActivity;
import com.example.vidbregar.bluepodcast.util.SharedPreferencesUtil;
import com.example.vidbregar.bluepodcast.viewmodel.PlayerViewModel;
import com.example.vidbregar.bluepodcast.viewmodel.PlayerViewModelFactory;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayerActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_PODCAST = "intent-extra-podcast";
    public static final String INTENT_EXTRA_EPISODE = "intent-extra-episode";

    private static final String EPISODE_TITLE_FIREBASE_PARAM = "episode_title";
    private static final String ADD_TO_FAVORITES_FIREBASE_EVENT = "add_to_favorites";

    private EpisodeDatabase episodeDatabase;
    private FavoritesDatabase favoritesDatabase;
    private PlayerViewModel playerViewModel;
    private PlayerService playerService;
    private EpisodeEntity episodeEntity;
    private SharedPreferencesUtil sharedPreferencesUtil;
    private FirebaseAnalytics firebaseAnalytics;

    @BindView(R.id.player_view)
    PlayerControlView playerControlView;
    @BindView(R.id.episode_thumbnail)
    CircleImageView episodeThumbnail;
    @BindView(R.id.episode_title)
    TextView episodeTitle;
    @BindView(R.id.episode_publisher)
    TextView episodePublisher;
    @BindView(R.id.add_to_favorites)
    CheckBox addToFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        episodeDatabase = EpisodeDatabase.getInstance(getApplicationContext());
        favoritesDatabase = FavoritesDatabase.getInstance(getApplicationContext());
        PlayerViewModelFactory playerViewModelFactory = new PlayerViewModelFactory(episodeDatabase, favoritesDatabase);
        playerViewModel = ViewModelProviders.of(this, playerViewModelFactory).get(PlayerViewModel.class);
        sharedPreferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        sharedPreferencesUtil.setIsApplicationAlive(true);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        loadData();
    }

    public void loadData() {
        Intent intent = getIntent();
        if (intent != null &&
                intent.hasExtra(INTENT_EXTRA_PODCAST) &&
                intent.hasExtra(INTENT_EXTRA_EPISODE)) {
            playerViewModel.putEpisode(intent.getParcelableExtra(INTENT_EXTRA_PODCAST),
                    intent.getParcelableExtra(INTENT_EXTRA_EPISODE));
            getEpisode();
        } else {
            playerViewModel.notifyEpisodeLiveData();
            getEpisode();
        }
    }

    private void getEpisode() {
        playerViewModel.getEpisodeLiveData().observe(this, episodeEntity -> {
            if (episodeEntity != null) {
                this.episodeEntity = episodeEntity;
                bindViews();
                setUpAddToFavorites();
                preparePlayer();
            }
        });
    }

    private void setUpAddToFavorites() {
        restoreFavoritesState();
        addToFavorites.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                playerViewModel.addFavorite(episodeEntity);
                playerViewModel.setIsAddedToFavorites(true);
                logToFirebase(episodeEntity.getEpisodeTitle());
            } else {
                playerViewModel.removeFavorite(episodeEntity);
                playerViewModel.setIsAddedToFavorites(false);
            }
        });
    }

    private void logToFirebase(String episodeTitle) {
        Bundle bundle = new Bundle();
        bundle.putString(EPISODE_TITLE_FIREBASE_PARAM, episodeTitle);
        firebaseAnalytics.logEvent(ADD_TO_FAVORITES_FIREBASE_EVENT, bundle);
    }

    private void restoreFavoritesState() {
        AsyncTask.execute(() -> {
            if (playerViewModel.getFavorite(episodeEntity.getEpisodeTitle()) != null) {
                PlayerActivity.this.runOnUiThread(() -> addToFavorites.setChecked(true));
            }
        });
    }

    private void preparePlayer() {
        // Using startService() overrides the default service lifetime that is managed
        // by bindService(Intent, ServiceConnection, int): it requires the service to remain
        // running until stopService(Intent) is called, regardless of whether any clients
        // are connected to it.
        startService(new Intent(this, PlayerService.class));
        bindService(new Intent(this, PlayerService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void bindViews() {
        Picasso.get().load(episodeEntity.getThumbnailUrl()).into(episodeThumbnail);
        episodeTitle.setText(episodeEntity.getEpisodeTitle());
        episodePublisher.setText(episodeEntity.getPublisher());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (sharedPreferencesUtil.isMainActivityAlive()) {
                    // MainActivity is alive -> return to it
                    finish();
                } else {
                    // Launch MainActivity
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerViewModel.isBound()) {
            unbindService(serviceConnection);
            playerViewModel.setIsBound(false);
        }
        sharedPreferencesUtil.setIsApplicationAlive(false);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            PlayerService.PlayerBinder binder = (PlayerService.PlayerBinder) service;
            playerService = binder.getService();
            playerViewModel.setIsBound(true);
            startPlaying();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            playerViewModel.setIsBound(true);
        }
    };

    private void startPlaying() {
        if (playerViewModel.isBound()) {
            playerService.setEpisode(episodeEntity);
            playerService.playOrPause(episodeEntity.getAudioUrl());
            playerControlView.setPlayer(playerService.simpleExoPlayer);
        }
    }
}
