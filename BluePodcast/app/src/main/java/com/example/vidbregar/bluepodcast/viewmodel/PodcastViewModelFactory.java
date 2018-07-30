package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vidbregar.bluepodcast.model.network.PodcastService;
import com.example.vidbregar.bluepodcast.util.SharedPreferencesUtil;

public class PodcastViewModelFactory implements ViewModelProvider.Factory {

    private final PodcastService podcastService;
    private final SharedPreferencesUtil sharedPreferencesUtil;

    public PodcastViewModelFactory(PodcastService podcastService, SharedPreferencesUtil sharedPreferencesUtil) {
        this.podcastService = podcastService;
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public PodcastViewModel create(Class modelClass) {
        return new PodcastViewModel(podcastService, sharedPreferencesUtil);
    }
}
