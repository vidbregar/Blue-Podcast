package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.vidbregar.bluepodcast.model.data.Channel;
import com.example.vidbregar.bluepodcast.model.data.Episode;
import com.example.vidbregar.bluepodcast.model.database.EpisodeDatabase;
import com.example.vidbregar.bluepodcast.model.database.EpisodeEntity;

public class PlayerViewModel extends ViewModel {

    private EpisodeDatabase episodeDatabase;
    private MutableLiveData<EpisodeEntity> episodeEntityLiveData;

    public PlayerViewModel(EpisodeDatabase episodeDatabase) {
        this.episodeDatabase = episodeDatabase;
        this.episodeEntityLiveData = new MutableLiveData<>();
    }

    public void putEpisode(Channel podcast, Episode episode) {
        AsyncTask.execute(() -> {
            EpisodeEntity episodeEntity = new EpisodeEntity(1,
                    podcast.getThumbnailUrl(),
                    episode.getTitle(),
                    podcast.getPublisher(),
                    episode.getAudioUrl());

            episodeDatabase.episodeDao().updateEpisode(episodeEntity);
            episodeEntityLiveData.postValue(episodeDatabase.episodeDao().getEpisode());
        });
    }

    public LiveData<EpisodeEntity> getEpisodeLiveData() {
        return episodeEntityLiveData;
    }

    public void notifyEpisodeLiveData() {
        AsyncTask.execute(() -> episodeEntityLiveData.postValue(episodeDatabase.episodeDao().getEpisode()));
    }
}
