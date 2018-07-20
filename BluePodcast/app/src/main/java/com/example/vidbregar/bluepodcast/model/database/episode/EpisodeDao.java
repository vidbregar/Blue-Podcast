package com.example.vidbregar.bluepodcast.model.database.episode;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface EpisodeDao {

    // Always call put
    // Used only for the first time to populate the database
    @Insert
    void insertEpisode(EpisodeEntity episodeEntity);

    @Query("SELECT * FROM episode_table")
    EpisodeEntity getEpisode();

    @Query("UPDATE episode_table SET thumbnail_url = :thumbnailUrl, episode_title = :episodeTitle, publisher = :publisher, audio_url = :audioUrl WHERE id = 1")
    void updateEpisode(String thumbnailUrl,
                       String episodeTitle,
                       String publisher,
                       String audioUrl);

}
