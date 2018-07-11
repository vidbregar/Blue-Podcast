
package com.example.vidbregar.bluepodcast.model.data;

import com.google.gson.annotations.SerializedName;

public class Channel {

    @SerializedName("id")
    public String id;
    @SerializedName("thumbnail")
    public String thumbnailUrl;
    @SerializedName("lastest_pub_date_ms")
    public int lastestPubDateMs;
    @SerializedName("website")
    public String website;
    @SerializedName("publisher")
    public String publisher;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;

    public Channel(String id, String thumbnailUrl, int lastestPubDateMs, String website,
                   String publisher, String title, String description) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.lastestPubDateMs = lastestPubDateMs;
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

    public int getLastestPubDateMs() {
        return lastestPubDateMs;
    }

    public void setLastestPubDateMs(int lastestPubDateMs) {
        this.lastestPubDateMs = lastestPubDateMs;
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
