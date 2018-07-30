package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vidbregar.bluepodcast.model.network.PodcastService;

public class SearchViewModelFactory implements ViewModelProvider.Factory {

    private final PodcastService podcastService;

    public SearchViewModelFactory(PodcastService podcastService) {
        this.podcastService = podcastService;
    }

    @Override
    public SearchViewModel create(Class modelClass) {
        return new SearchViewModel(podcastService);
    }
}