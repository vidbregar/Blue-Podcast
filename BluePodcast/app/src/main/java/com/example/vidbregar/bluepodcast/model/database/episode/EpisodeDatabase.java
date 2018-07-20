package com.example.vidbregar.bluepodcast.model.database.episode;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

@Database(entities = EpisodeEntity.class, version = 1, exportSchema = false)
public abstract class EpisodeDatabase extends RoomDatabase {

    private static EpisodeDatabase INSTANCE;

    public abstract EpisodeDao episodeDao();


    public synchronized static EpisodeDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static EpisodeDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                EpisodeDatabase.class,
                "episode.db")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
                            getInstance(context).episodeDao()
                                    .insertEpisode(new EpisodeEntity(1,
                                            null,
                                            null,
                                            null,
                                            null));
                        });
                    }
                })
                .build();
    }

}
