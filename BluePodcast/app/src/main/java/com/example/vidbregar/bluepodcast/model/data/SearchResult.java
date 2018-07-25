
package com.example.vidbregar.bluepodcast.model.data;

import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("description_original")
    private String description;
    @SerializedName("thumbnailUrl")
    private String thumbnailUrl;
    @SerializedName("pub_date_ms")
    private long publicationDateMilliseconds;
    @SerializedName("audio_length")
    private String audioLength;
    @SerializedName("audio")
    private String audioUrl;
    @SerializedName("publisher_original")
    private String publisher;
    @SerializedName("title_original")
    private String episodeTitle;
    @SerializedName("podcast_id")
    private String podcastId;
    @SerializedName("podcast_title_original")
    private String podcastTitle;
    @SerializedName("id")
    private String episodeId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public long getPublicationDateMilliseconds() {
        return publicationDateMilliseconds;
    }

    public void setPublicationDateMilliseconds(long publicationDateMilliseconds) {
        this.publicationDateMilliseconds = publicationDateMilliseconds;
    }

    public String getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(String audioLength) {
        this.audioLength = audioLength;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getPodcastId() {
        return podcastId;
    }

    public void setPodcastId(String podcastId) {
        this.podcastId = podcastId;
    }

    public String getPodcastTitle() {
        return podcastTitle;
    }

    public void setPodcastTitle(String podcastTitle) {
        this.podcastTitle = podcastTitle;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }
}
