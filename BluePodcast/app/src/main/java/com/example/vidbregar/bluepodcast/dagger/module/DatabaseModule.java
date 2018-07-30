package com.example.vidbregar.bluepodcast.dagger.module;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.example.vidbregar.bluepodcast.model.database.episode.EpisodeDatabase;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;
import com.example.vidbregar.bluepodcast.util.SharedPreferencesUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Singleton
    @Provides
    FavoritesDatabase provideFavoritesDatabase(Application applicationContext) {
        return Room.databaseBuilder(applicationContext,
                FavoritesDatabase.class,
                "favorites.db")
                .build();
    }

    @Singleton
    @Provides
    EpisodeDatabase provideEpisodeDatabase(Application applicationContext) {
        return Room.databaseBuilder(applicationContext,
                EpisodeDatabase.class,
                "episode.db")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        String sql =
                                "INSERT INTO episode_table (id, thumbnail_url, episode_title, publisher, audio_url) VALUES (1, '', '', '', '')";
                        new Thread(() -> db.execSQL(sql)).run();
                    }
                })
                .build();
    }

    @Singleton
    @Provides
    SharedPreferencesUtil provideSharedPreferencesUtil(Application applicationContext) {
        return new SharedPreferencesUtil(applicationContext);
    }
}
