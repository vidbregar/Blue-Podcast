package com.example.vidbregar.bluepodcast.ui.main.favorites;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.data.Channel;
import com.example.vidbregar.bluepodcast.model.data.Episode;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoriteEntity;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;
import com.example.vidbregar.bluepodcast.ui.main.favorites.adapter.FavoritesAdapter;
import com.example.vidbregar.bluepodcast.ui.main.favorites.listener.FavoriteClickListener;
import com.example.vidbregar.bluepodcast.ui.player.PlayerActivity;
import com.example.vidbregar.bluepodcast.viewmodel.FavoritesViewModel;
import com.example.vidbregar.bluepodcast.viewmodel.FavoritesViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesFragment extends Fragment implements FavoriteClickListener {

    private Context context;
    private FavoritesViewModel favoritesViewModel;
    private FavoritesDatabase favoritesDatabase;

    @BindView(R.id.favorites_rv)
    RecyclerView favoritesRecyclerView;
    private FavoritesAdapter favoritesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoritesDatabase = FavoritesDatabase.getInstance(getActivity().getApplicationContext());
        FavoritesViewModelFactory favoritesViewModelFactory = new FavoritesViewModelFactory(favoritesDatabase);
        favoritesViewModel = ViewModelProviders.of(this, favoritesViewModelFactory).get(FavoritesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        context = root.getContext();
        ButterKnife.bind(this, root);
        loadFavorites();
        return root;
    }

    private void loadFavorites() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(context);
        favoritesRecyclerView.setNestedScrollingEnabled(false);
        favoritesRecyclerView.setLayoutManager(linearLayoutManager);
        favoritesAdapter = new FavoritesAdapter(this);
        favoritesRecyclerView.setAdapter(favoritesAdapter);
        favoritesViewModel.getFavorites().observe(this,
                favorites -> favoritesAdapter.swapFavorites(favorites));
    }

    @Override
    public void onFavoriteClickListener(FavoriteEntity favorite) {
        Intent playerActivityIntent = new Intent(getActivity(), PlayerActivity.class);
        Channel podcast = new Channel(null,
                favorite.getThumbnailUrl(),
                null,
                null,
                favorite.getPublisher(),
                favorite.getEpisodeTitle(),
                null);
        Episode episode = new Episode(favorite.getEpisodeTitle(),
                null,
                null,
                0,
                null,
                favorite.getAudioUrl());
        playerActivityIntent.putExtra(PlayerActivity.INTENT_EXTRA_PODCAST, podcast);
        playerActivityIntent.putExtra(PlayerActivity.INTENT_EXTRA_EPISODE, episode);
        startActivity(playerActivityIntent);
    }
}
