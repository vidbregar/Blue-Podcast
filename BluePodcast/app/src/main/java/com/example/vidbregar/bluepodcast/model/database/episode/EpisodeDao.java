package com.example.vidbregar.bluepodcast.model.database.episode;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


@Dao
public interface EpisodeDao {

    // Always call put
    // Used only for the first time to populate the database
    @Insert
    void insertEpisode(EpisodeEntity episodeEntity);

    @Query("SELECT * FROM episode_table")
    EpisodeEntity getEpisode();

    @Update
    void updateEpisode(EpisodeEntity episodeEntity);

}
