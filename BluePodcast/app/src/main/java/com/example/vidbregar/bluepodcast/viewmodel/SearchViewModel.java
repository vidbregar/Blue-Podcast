package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.vidbregar.bluepodcast.BuildConfig;
import com.example.vidbregar.bluepodcast.model.data.Search;
import com.example.vidbregar.bluepodcast.model.network.PodcastClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.API_KEY;

    private PodcastClient podcastClient;
    private MutableLiveData<Search> searchLiveData;

    public SearchViewModel(PodcastClient podcastClient) {
        this.podcastClient = podcastClient;
        this.searchLiveData = new MutableLiveData<>();
    }

    public void search(String query) {
        Call<Search> searchCall = podcastClient.getPodcastService().search(API_KEY, query);
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
}
