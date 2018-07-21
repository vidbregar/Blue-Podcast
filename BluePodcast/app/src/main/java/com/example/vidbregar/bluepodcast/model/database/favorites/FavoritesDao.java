package com.example.vidbregar.bluepodcast.model.database.favorites;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Insert
    void insertFavorite(FavoriteEntity favoriteEntity);

    @Query("SELECT * FROM favorites_table")
    LiveData<List<FavoriteEntity>> getAllFavorites();

    @Query("SELECT * FROM favorites_table WHERE episode_title = :title")
    FavoriteEntity getFavorite(String title);

    @Query("DELETE FROM favorites_table WHERE episode_title = :title")
    void removeFavorite(String title);

}
