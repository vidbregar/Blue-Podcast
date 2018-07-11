package com.example.vidbregar.bluepodcast.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.network.PodcastClient;
import com.example.vidbregar.bluepodcast.model.network.PodcastService;
import com.example.vidbregar.bluepodcast.viewmodel.PodcastViewModel;
import com.example.vidbregar.bluepodcast.viewmodel.PodcastViewModelFactory;

public class HomeFragment extends Fragment {

    private PodcastViewModel podcastViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PodcastService podcastService = new PodcastClient().getPodcastService();
        PodcastViewModelFactory podcastViewModelFactory = new PodcastViewModelFactory(podcastService);
        podcastViewModel = ViewModelProviders.of(this, podcastViewModelFactory).get(PodcastViewModel.class);
        podcastViewModel.getBestPodcasts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
