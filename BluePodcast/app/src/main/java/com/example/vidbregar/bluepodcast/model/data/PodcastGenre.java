package com.example.vidbregar.bluepodcast.model.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PodcastGenre {

    @SerializedName("name")
    public String name;
    @SerializedName("channels")
    public List<Channel> channels;
    @SerializedName("id")
    public int id;

    public PodcastGenre(String name, List<Channel> channels, int id) {
        this.name = name;
        this.channels = channels;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
