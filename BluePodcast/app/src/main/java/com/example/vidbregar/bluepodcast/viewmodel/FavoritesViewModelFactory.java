package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;

public class FavoritesViewModelFactory implements ViewModelProvider.Factory {

    private final FavoritesDatabase favoritesDatabase;

    public FavoritesViewModelFactory(FavoritesDatabase favoritesDatabase) {
        this.favoritesDatabase = favoritesDatabase;
    }

    @Override
    public FavoritesViewModel create(Class modelClass) {
        return new FavoritesViewModel(favoritesDatabase);
    }

}
