package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vidbregar.bluepodcast.model.network.PodcastService;

public class PodcastViewModelFactory implements ViewModelProvider.Factory {

    private final PodcastService podcastService;

    public PodcastViewModelFactory(PodcastService podcastService) {
        this.podcastService = podcastService;
    }

    @Override
    public PodcastViewModel create(Class modelClass) {
        return new PodcastViewModel(podcastService);
    }
}
