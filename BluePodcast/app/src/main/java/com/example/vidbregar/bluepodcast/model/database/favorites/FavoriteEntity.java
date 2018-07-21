package com.example.vidbregar.bluepodcast.model.database.favorites;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favorites_table")
public class FavoriteEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "episode_title")
    private String episodeTitle;

    @ColumnInfo(name = "thumbnail_url")
    private String thumbnailUrl;

    @ColumnInfo(name = "publisher")
    private String publisher;

    @ColumnInfo(name = "audio_url")
    private String audioUrl;

    public FavoriteEntity(@NonNull String episodeTitle, String thumbnailUrl, String publisher, String audioUrl) {
        this.episodeTitle = episodeTitle;
        this.thumbnailUrl = thumbnailUrl;
        this.publisher = publisher;
        this.audioUrl = audioUrl;
    }

    @NonNull
    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(@NonNull String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
