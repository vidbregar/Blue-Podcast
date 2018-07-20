package com.example.vidbregar.bluepodcast.model.database.episode;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "episode_table")
public class EpisodeEntity {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "thumbnail_url")
    private String thumbnailUrl;

    @ColumnInfo(name = "episode_title")
    private String episodeTitle;

    @ColumnInfo(name = "publisher")
    private String publisher;

    @ColumnInfo(name = "audio_url")
    private String audioUrl;

    public EpisodeEntity(int id, String thumbnailUrl, String episodeTitle, String publisher, String audioUrl) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.episodeTitle = episodeTitle;
        this.publisher = publisher;
        this.audioUrl = audioUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
