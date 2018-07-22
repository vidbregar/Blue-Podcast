package com.example.vidbregar.bluepodcast.ui.main.favorites;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vidbregar.bluepodcast.R;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;
import com.example.vidbregar.bluepodcast.viewmodel.FavoritesViewModel;
import com.example.vidbregar.bluepodcast.viewmodel.FavoritesViewModelFactory;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    private FavoritesDatabase favoritesDatabase;

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
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }
}
