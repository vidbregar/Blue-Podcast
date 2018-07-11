package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.vidbregar.bluepodcast.BuildConfig;
import com.example.vidbregar.bluepodcast.model.data.Channel;
import com.example.vidbregar.bluepodcast.model.data.PodcastGenre;
import com.example.vidbregar.bluepodcast.model.network.PodcastService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PodcastViewModel extends ViewModel {

    private PodcastService podcastService;
    private MutableLiveData<List<Channel>> bestPodcastsLiveData;

    public PodcastViewModel(PodcastService podcastService) {
        this.podcastService = podcastService;
    }

    public MutableLiveData<List<Channel>> getBestPodcastsLiveData() {
        if (bestPodcastsLiveData == null) {
            bestPodcastsLiveData = new MutableLiveData<>();
            getBestPodcasts();
        }
        return bestPodcastsLiveData;
    }

    private void getBestPodcasts() {
        Call<PodcastGenre> bestPodcastsCall = podcastService.getBestPodcasts(BuildConfig.API_KEY);
        bestPodcastsCall.enqueue(new Callback<PodcastGenre>() {
            @Override
            public void onResponse(Call<PodcastGenre> call, Response<PodcastGenre> response) {
                if (response.isSuccessful()) {
                    Log.e("SUCCESS", response.body().name);
                    bestPodcastsLiveData.setValue(response.body().channels);
                }
            }

            @Override
            public void onFailure(Call<PodcastGenre> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }
}
