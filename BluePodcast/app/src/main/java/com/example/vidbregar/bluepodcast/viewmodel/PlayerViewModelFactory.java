package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vidbregar.bluepodcast.model.database.episode.EpisodeDatabase;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;


public class PlayerViewModelFactory implements ViewModelProvider.Factory {

    private final EpisodeDatabase episodeDatabase;
    private final FavoritesDatabase favoritesDatabase;

    public PlayerViewModelFactory(EpisodeDatabase episodeDatabase, FavoritesDatabase favoritesDatabase) {
        this.episodeDatabase = episodeDatabase;
        this.favoritesDatabase = favoritesDatabase;
    }

    @Override
    public PlayerViewModel create(Class modelClass) {
        return new PlayerViewModel(episodeDatabase, favoritesDatabase);
    }
}