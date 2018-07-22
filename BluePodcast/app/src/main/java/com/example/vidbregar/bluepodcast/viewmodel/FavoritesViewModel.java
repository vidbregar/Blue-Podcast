package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.vidbregar.bluepodcast.model.database.favorites.FavoriteEntity;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;

import java.util.List;

public class FavoritesViewModel extends ViewModel {

    private FavoritesDatabase favoritesDatabase;

    public FavoritesViewModel(FavoritesDatabase favoritesDatabase) {
        this.favoritesDatabase = favoritesDatabase;
    }

    public LiveData<List<FavoriteEntity>> getFavorites() {
        return favoritesDatabase.favoritesDao().getAllFavorites();
    }
}
