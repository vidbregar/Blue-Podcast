package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.vidbregar.bluepodcast.BuildConfig;
import com.example.vidbregar.bluepodcast.model.data.Channel;
import com.example.vidbregar.bluepodcast.model.network.PodcastService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PodcastViewModel extends ViewModel {

    private PodcastService podcastService;

    public PodcastViewModel(PodcastService podcastService) {
        this.podcastService = podcastService;
    }

    public void getBestPodcasts() {
        Call<List<Channel>> bestPodcastsCall = podcastService.getBestPodcasts(BuildConfig.API_KEY);
        bestPodcastsCall.enqueue(new Callback<List<Channel>>() {
            @Override
            public void onResponse(Call<List<Channel>> call, Response<List<Channel>> response) {
            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {

            }
        });
    }
}
