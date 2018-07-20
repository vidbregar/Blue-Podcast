package com.example.vidbregar.bluepodcast.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Channel implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("thumbnail")
    private String thumbnailUrl;
    @SerializedName("lastest_pub_date_ms")
    private String latestPubDateMs;
    @SerializedName("website")
    private String website;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;

    public Channel(String id, String thumbnailUrl, String latestPubDateMs, String website,
                   String publisher, String title, String description) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.latestPubDateMs = latestPubDateMs;
        this.website = website;
        this.publisher = publisher;
        this.title = title;
        this.description = description;
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

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

    public String getLatestPubDateMs() {
        return latestPubDateMs;
    }

    public void setLatestPubDateMs(String latestPubDateMs) {
        this.latestPubDateMs = latestPubDateMs;
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

    protected Channel(Parcel in) {
        id = in.readString();
        thumbnailUrl = in.readString();
        latestPubDateMs = in.readString();
        website = in.readString();
        publisher = in.readString();
        title = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(thumbnailUrl);
        parcel.writeString(latestPubDateMs);
        parcel.writeString(website);
        parcel.writeString(publisher);
        parcel.writeString(title);
        parcel.writeString(description);
    }
}
