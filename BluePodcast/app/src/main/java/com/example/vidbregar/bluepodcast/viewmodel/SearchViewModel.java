package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

import com.example.vidbregar.bluepodcast.BuildConfig;
import com.example.vidbregar.bluepodcast.model.data.Search;
import com.example.vidbregar.bluepodcast.model.network.PodcastService;
import com.google.firebase.analytics.FirebaseAnalytics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.LISTEN_NOTES_API_KEY;

    private PodcastService podcastService;
    private FirebaseAnalytics firebaseAnalytics;
    private MutableLiveData<Search> searchLiveData;

    public SearchViewModel(PodcastService podcastService,
                           FirebaseAnalytics firebaseAnalytics) {
        this.podcastService = podcastService;
        this.firebaseAnalytics = firebaseAnalytics;
        this.searchLiveData = new MutableLiveData<>();
    }

    public void search(String query) {
        Call<Search> searchCall = podcastService.search(API_KEY, query);
        searchCall.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if (response.isSuccessful()) {
                    searchLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {

            }
        });
    }

    public LiveData<Search> getSearchResults() {
        return searchLiveData;
    }

    public void logEventToFirebase(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, query);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
    }
}
