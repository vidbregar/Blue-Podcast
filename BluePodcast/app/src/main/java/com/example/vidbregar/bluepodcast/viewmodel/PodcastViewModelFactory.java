package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vidbregar.bluepodcast.model.network.PodcastClient;
import com.example.vidbregar.bluepodcast.model.network.PodcastService;
import com.example.vidbregar.bluepodcast.util.SharedPreferencesUtil;

public class PodcastViewModelFactory implements ViewModelProvider.Factory {

    private final PodcastClient podcastClient;
    private final SharedPreferencesUtil sharedPreferencesUtil;

    public PodcastViewModelFactory(PodcastClient podcastClient, SharedPreferencesUtil sharedPreferencesUtil) {
        this.podcastClient = podcastClient;
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public PodcastViewModel create(Class modelClass) {
        return new PodcastViewModel(podcastClient, sharedPreferencesUtil);
    }
}
