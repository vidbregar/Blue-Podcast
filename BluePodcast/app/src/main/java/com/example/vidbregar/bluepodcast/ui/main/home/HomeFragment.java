package com.example.vidbregar.bluepodcast.ui.main.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.data.Channel;
import com.example.vidbregar.bluepodcast.model.data.Episode;
import com.example.vidbregar.bluepodcast.model.network.PodcastClient;
import com.example.vidbregar.bluepodcast.model.network.PodcastService;
import com.example.vidbregar.bluepodcast.ui.main.MainActivity;
import com.example.vidbregar.bluepodcast.ui.main.home.adapter.BestPodcastsAdapter;
import com.example.vidbregar.bluepodcast.ui.main.home.adapter.EpisodesAdapter;
import com.example.vidbregar.bluepodcast.ui.main.home.adapter.GenrePodcastsAdapter;
import com.example.vidbregar.bluepodcast.ui.main.home.listener.EpisodeClickListener;
import com.example.vidbregar.bluepodcast.ui.main.home.listener.PodcastClickListener;
import com.example.vidbregar.bluepodcast.util.SharedPreferencesUtil;
import com.example.vidbregar.bluepodcast.viewmodel.PodcastViewModel;
import com.example.vidbregar.bluepodcast.viewmodel.PodcastViewModelFactory;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements PodcastClickListener,
        EpisodeClickListener,
        MainActivity.OnBackPressedListener {

    private Context context;
    private PodcastViewModel podcastViewModel;
    private PodcastService podcastService;
    private SharedPreferencesUtil sharedPreferencesUtil;

    // Podcasts container
    @BindView(R.id.podcasts_container)
    ConstraintLayout podcastsContainer;
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
    // Loading indicator
    @BindView(R.id.loading_container)
    ConstraintLayout loadingIndicatorContainer;
    // Podcast detail
    @BindView(R.id.podcast_detail_container)
    ConstraintLayout podcastDetailContainer;
    @BindView(R.id.podcast_thumbnail)
    ImageView podcastThumbnailImageView;
    @BindView(R.id.podcast_title)
    TextView podcastTitleTextView;
    @BindView(R.id.podcast_publisher)
    TextView podcastPublisherTextView;
    @BindView(R.id.podcast_website)
    ImageButton podcastWebsiteButton;
    @BindView(R.id.podcast_description)
    TextView podcastDescriptionTextView;
    @BindView(R.id.podcast_episodes_rv)
    RecyclerView podcastEpisodeRecyclerView;
    private EpisodesAdapter episodesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        podcastService = new PodcastClient().getPodcastService();
        sharedPreferencesUtil = new SharedPreferencesUtil(getActivity().getApplicationContext());
        PodcastViewModelFactory podcastViewModelFactory = new PodcastViewModelFactory(podcastService, sharedPreferencesUtil);
        podcastViewModel = ViewModelProviders.of(getActivity(), podcastViewModelFactory).get(PodcastViewModel.class);
        // Used for handling back button when podcast detail layout is displayed
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = root.getContext();
        ButterKnife.bind(this, root);

        loadHomePodcasts();
        observeEpisodes();
        restoreCorrectLayout();
        return root;
    }

    private void loadHomePodcasts() {
        loadBestPodcasts();
        loadComedyPodcasts();
        loadBusinessPodcasts();
        loadHealthPodcasts();
    }

    private void restoreCorrectLayout() {
        if (podcastViewModel.getIsOnPodcastDetailLayout()) {
            podcastsContainer.setVisibility(View.GONE);
            podcastDetailContainer.setVisibility(View.VISIBLE);
            loadPodcastData(podcastViewModel.getSelectedPodcast());
            displayUpNavigation();
        } else {
            podcastDetailContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPodcastClickListener(Channel podcast) {
        podcastViewModel.setIsOnPodcastDetailLayout(true);
        podcastViewModel.getEpisodesFromApi(podcast.getId());
        podcastsContainer.setVisibility(View.GONE);
        podcastDetailContainer.setVisibility(View.VISIBLE);
        podcastViewModel.setSelectedPodcast(podcast);
        loadPodcastData(podcast);
        displayUpNavigation();
    }

    @Override
    public void onEpisodeClickListener(Episode episode) {

    }

    private void loadPodcastData(Channel podcast) {
        Picasso.get().load(podcast.getThumbnailUrl()).into(podcastThumbnailImageView);
        podcastTitleTextView.setText(podcast.getTitle());
        podcastPublisherTextView.setText(podcast.getPublisher());
        podcastDescriptionTextView.setText(Jsoup.parse(podcast.getDescription()).text());
        podcastWebsiteButton.setOnClickListener(view -> {
            Intent podcastWebsiteIntent = new Intent(Intent.ACTION_VIEW);
            podcastWebsiteIntent.setData(Uri.parse(podcast.getWebsite()));
            startActivity(podcastWebsiteIntent);
        });
    }

    private void displayUpNavigation() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                displayPodcastsList();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        displayPodcastsList();
    }

    private void displayPodcastsList() {
        podcastDetailContainer.setVisibility(View.GONE);
        podcastsContainer.setVisibility(View.VISIBLE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        podcastViewModel.setIsOnPodcastDetailLayout(false);
        podcastViewModel.setSelectedPodcast(null);
        setHasOptionsMenu(false);
    }

    private void observeEpisodes() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context);
        podcastEpisodeRecyclerView.setNestedScrollingEnabled(false);
        podcastEpisodeRecyclerView.setLayoutManager(linearLayoutManager);
        episodesAdapter = new EpisodesAdapter(this);
        podcastEpisodeRecyclerView.setAdapter(episodesAdapter);
        podcastViewModel.getEpisodesLiveData().observe(this,
                episodes -> episodesAdapter.swapEpisodes(episodes));
    }

    private void loadBestPodcasts() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        bestPodcastsRecyclerView.setLayoutManager(linearLayoutManager);
        bestPodcastsAdapter = new BestPodcastsAdapter(this);
        bestPodcastsRecyclerView.setAdapter(bestPodcastsAdapter);
        podcastViewModel.getBestPodcastsLiveData().observe(this,
                podcasts -> {
                    bestPodcastsAdapter.swapPodcasts(podcasts);
                    loadingIndicatorContainer.setVisibility(View.GONE);
                    if (!podcastViewModel.getIsOnPodcastDetailLayout()) {
                        podcastsContainer.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void loadComedyPodcasts() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context);
        comedyPodcastsRecyclerView.setNestedScrollingEnabled(false);
        comedyPodcastsRecyclerView.setLayoutManager(linearLayoutManager);
        comedyPodcastsAdapter = new GenrePodcastsAdapter(this);
        comedyPodcastsRecyclerView.setAdapter(comedyPodcastsAdapter);
        podcastViewModel.getComedyPodcastsLiveData().observe(this,
                podcasts -> comedyPodcastsAdapter.swapPodcasts(podcasts));
    }

    private void loadBusinessPodcasts() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context);
        businessPodcastsRecyclerView.setNestedScrollingEnabled(false);
        businessPodcastsRecyclerView.setLayoutManager(linearLayoutManager);
        businessPodcastsAdapter = new GenrePodcastsAdapter(this);
        businessPodcastsRecyclerView.setAdapter(businessPodcastsAdapter);
        podcastViewModel.getBusinessPodcastsLiveData().observe(this,
                podcasts -> businessPodcastsAdapter.swapPodcasts(podcasts));
    }

    private void loadHealthPodcasts() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context);
        healthPodcastsRecyclerView.setNestedScrollingEnabled(false);
        healthPodcastsRecyclerView.setLayoutManager(linearLayoutManager);
        healthPodcastsAdapter = new GenrePodcastsAdapter(this);
        healthPodcastsRecyclerView.setAdapter(healthPodcastsAdapter);
        podcastViewModel.getHealthPodcastsLiveData().observe(this,
                podcasts -> healthPodcastsAdapter.swapPodcasts(podcasts));
    }
}
