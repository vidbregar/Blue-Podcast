package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vidbregar.bluepodcast.model.network.PodcastService;
import com.google.firebase.analytics.FirebaseAnalytics;

public class SearchViewModelFactory implements ViewModelProvider.Factory {

    private final PodcastService podcastService;
    private final FirebaseAnalytics firebaseAnalytics;

    public SearchViewModelFactory(PodcastService podcastService,
                                  FirebaseAnalytics firebaseAnalytics) {
        this.podcastService = podcastService;
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public SearchViewModel create(Class modelClass) {
        return new SearchViewModel(podcastService, firebaseAnalytics);
    }
}