package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vidbregar.bluepodcast.model.database.EpisodeDatabase;

public class PlayerViewModelFactory implements ViewModelProvider.Factory {

    private final EpisodeDatabase episodeDatabase;

    public PlayerViewModelFactory(EpisodeDatabase episodeDatabase) {
        this.episodeDatabase = episodeDatabase;
    }

    @Override
    public PlayerViewModel create(Class modelClass) {
        return new PlayerViewModel(episodeDatabase);
    }
}