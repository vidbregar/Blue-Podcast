package com.example.vidbregar.bluepodcast.model.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Podcast {

    @SerializedName("id")
    private String id;
    @SerializedName("thumbnail")
    private String thumbnailUrl;
    @SerializedName("episodes")
    private List<Episode> episodes;
    @SerializedName("website")
    private String website;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;

    public Podcast(String id, String thumbnailUrl, List<Episode> episodes, String website,
                   String publisher, String title, String description) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.episodes = episodes;
        this.website = website;
        this.publisher = publisher;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
