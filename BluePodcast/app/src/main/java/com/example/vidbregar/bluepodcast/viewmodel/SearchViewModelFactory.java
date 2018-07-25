package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vidbregar.bluepodcast.model.network.PodcastClient;

public class SearchViewModelFactory implements ViewModelProvider.Factory {

    private final PodcastClient podcastClient;

    public SearchViewModelFactory(PodcastClient podcastClient) {
        this.podcastClient = podcastClient;
    }

    @Override
    public SearchViewModel create(Class modelClass) {
        return new SearchViewModel(podcastClient);
    }
}