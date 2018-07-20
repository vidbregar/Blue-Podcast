package com.example.vidbregar.bluepodcast.model.database.favorites;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = FavoriteEntity.class, version = 1, exportSchema = false)
public abstract class FavoritesDatabase extends RoomDatabase {

    private static FavoritesDatabase INSTANCE;

    public abstract FavoritesDao favoritesDao();


    public synchronized static FavoritesDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static FavoritesDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                FavoritesDatabase.class,
                "favorites.db")
                .build();
    }
}
