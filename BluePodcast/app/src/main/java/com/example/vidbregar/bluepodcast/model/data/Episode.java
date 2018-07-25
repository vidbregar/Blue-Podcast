package com.example.vidbregar.bluepodcast.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Episode implements Parcelable {

    @SerializedName("title")
    private String title;
    @SerializedName("pub_date_ms")
    private String publicationDateMilliseconds;
    @SerializedName("description")
    private String description;
    @SerializedName("audio_length")
    private int audioLength;
    @SerializedName("id")
    private String id;
    @SerializedName("audio")
    private String audioUrl;

    public Episode(String title, String publicationDateMilliseconds, String description,
                   int audioLength, String id, String audioUrl) {
        this.title = title;
        this.publicationDateMilliseconds = publicationDateMilliseconds;
        this.description = description;
        this.audioLength = audioLength;
        this.id = id;
        this.audioUrl = audioUrl;
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicationDateMilliseconds() {
        return publicationDateMilliseconds;
    }

    public void setPublicationDateMilliseconds(String publicationDateMilliseconds) {
        this.publicationDateMilliseconds = publicationDateMilliseconds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(int audioLength) {
        this.audioLength = audioLength;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    protected Episode(Parcel in) {
        title = in.readString();
        publicationDateMilliseconds = in.readString();
        description = in.readString();
        audioLength = in.readInt();
        id = in.readString();
        audioUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(publicationDateMilliseconds);
        parcel.writeString(description);
        parcel.writeInt(audioLength);
        parcel.writeString(id);
        parcel.writeString(audioUrl);
    }
}
