package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vidbregar.bluepodcast.model.database.episode.EpisodeDatabase;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;
import com.google.firebase.analytics.FirebaseAnalytics;


public class PlayerViewModelFactory implements ViewModelProvider.Factory {

    private final EpisodeDatabase episodeDatabase;
    private final FavoritesDatabase favoritesDatabase;
    private final FirebaseAnalytics firebaseAnalytics;

    public PlayerViewModelFactory(EpisodeDatabase episodeDatabase,
                                  FavoritesDatabase favoritesDatabase,
                                  FirebaseAnalytics firebaseAnalytics) {
        this.episodeDatabase = episodeDatabase;
        this.favoritesDatabase = favoritesDatabase;
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public PlayerViewModel create(Class modelClass) {
        return new PlayerViewModel(episodeDatabase, favoritesDatabase, firebaseAnalytics);
    }
}