package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;

public class FavoritesViewModel extends ViewModel {

    private FavoritesDatabase favoritesDatabase;

    public FavoritesViewModel(FavoritesDatabase favoritesDatabase) {
        this.favoritesDatabase = favoritesDatabase;
    }
}
