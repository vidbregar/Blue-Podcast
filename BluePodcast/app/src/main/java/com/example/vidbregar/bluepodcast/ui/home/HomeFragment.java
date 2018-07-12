package com.example.vidbregar.bluepodcast.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.network.PodcastClient;
import com.example.vidbregar.bluepodcast.model.network.PodcastService;
import com.example.vidbregar.bluepodcast.viewmodel.PodcastViewModel;
import com.example.vidbregar.bluepodcast.viewmodel.PodcastViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private Context context;
    private PodcastViewModel podcastViewModel;

    // Best podcasts
    @BindView(R.id.best_podcasts_rv)
    RecyclerView bestPodcastsRecyclerView;
    private BestPodcastsAdapter bestPodcastsAdapter;
    // Comedy podcasts
    @BindView(R.id.comedy_podcasts_rv)
    RecyclerView comedyPodcastsRecyclerView;
    private GenrePodcastsAdapter comedyPodcastsAdapter;
    // Business podcasts
    @BindView(R.id.business_podcasts_rv)
    RecyclerView businessPodcastsRecyclerView;
    private GenrePodcastsAdapter businessPodcastsAdapter;
    // Health podcasts
    @BindView(R.id.health_podcasts_rv)
    RecyclerView healthPodcastsRecyclerView;
    private GenrePodcastsAdapter healthPodcastsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PodcastService podcastService = new PodcastClient().getPodcastService();
        PodcastViewModelFactory podcastViewModelFactory = new PodcastViewModelFactory(podcastService);
        podcastViewModel = ViewModelProviders.of(getActivity(), podcastViewModelFactory).get(PodcastViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = root.getContext();
        ButterKnife.bind(this, root);

        loadBestPodcasts();
        loadComedyPodcasts();
        loadBusinessPodcasts();
        loadHealthPodcasts();
        return root;
    }

    private void loadBestPodcasts() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        bestPodcastsRecyclerView.setLayoutManager(linearLayoutManager);
        bestPodcastsAdapter = new BestPodcastsAdapter();
        bestPodcastsRecyclerView.setAdapter(bestPodcastsAdapter);
        podcastViewModel.getBestPodcastsLiveData().observe(this,
                podcasts -> bestPodcastsAdapter.swapPodcasts(podcasts));
    }

    private void loadComedyPodcasts() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context);
        comedyPodcastsRecyclerView.setNestedScrollingEnabled(false);
        comedyPodcastsRecyclerView.setLayoutManager(linearLayoutManager);
        comedyPodcastsAdapter = new GenrePodcastsAdapter();
        comedyPodcastsRecyclerView.setAdapter(comedyPodcastsAdapter);
        podcastViewModel.getComedyPodcastsLiveData().observe(this,
                podcasts -> comedyPodcastsAdapter.swapPodcasts(podcasts));
    }

    private void loadBusinessPodcasts() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context);
        businessPodcastsRecyclerView.setNestedScrollingEnabled(false);
        businessPodcastsRecyclerView.setLayoutManager(linearLayoutManager);
        businessPodcastsAdapter = new GenrePodcastsAdapter();
        businessPodcastsRecyclerView.setAdapter(businessPodcastsAdapter);
        podcastViewModel.getBusinessPodcastsLiveData().observe(this,
                podcasts -> businessPodcastsAdapter.swapPodcasts(podcasts));
    }

    private void loadHealthPodcasts() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context);
        healthPodcastsRecyclerView.setNestedScrollingEnabled(false);
        healthPodcastsRecyclerView.setLayoutManager(linearLayoutManager);
        healthPodcastsAdapter = new GenrePodcastsAdapter();
        healthPodcastsRecyclerView.setAdapter(healthPodcastsAdapter);
        podcastViewModel.getHealthPodcastsLiveData().observe(this,
                podcasts -> healthPodcastsAdapter.swapPodcasts(podcasts));
    }
}
