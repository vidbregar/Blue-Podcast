package com.example.vidbregar.bluepodcast.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.vidbregar.bluepodcast.model.data.Channel;
import com.example.vidbregar.bluepodcast.model.data.Episode;
import com.example.vidbregar.bluepodcast.model.database.episode.EpisodeDatabase;
import com.example.vidbregar.bluepodcast.model.database.episode.EpisodeEntity;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoriteEntity;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;
import com.example.vidbregar.bluepodcast.util.EntityConverterUtil;

public class PlayerViewModel extends ViewModel {

    private EpisodeDatabase episodeDatabase;
    private FavoritesDatabase favoritesDatabase;
    private MutableLiveData<EpisodeEntity> episodeEntityLiveData;
    private boolean isBound;

    public PlayerViewModel(EpisodeDatabase episodeDatabase, FavoritesDatabase favoritesDatabase) {
        this.episodeDatabase = episodeDatabase;
        this.favoritesDatabase = favoritesDatabase;
        this.episodeEntityLiveData = new MutableLiveData<>();
    }

    public void putEpisode(Channel podcast, Episode episode) {
        AsyncTask.execute(() -> {
            episodeDatabase.episodeDao().updateEpisode(podcast.getThumbnailUrl(),
                    episode.getTitle(),
                    podcast.getPublisher(),
                    episode.getAudioUrl());
            EpisodeEntity episodeEntity = episodeDatabase.episodeDao().getEpisode();
            episodeEntityLiveData.postValue(episodeEntity);
        });
    }

    public LiveData<EpisodeEntity> getEpisodeLiveData() {
        return episodeEntityLiveData;
    }

    public void notifyEpisodeLiveData() {
        AsyncTask.execute(() -> episodeEntityLiveData.postValue(episodeDatabase.episodeDao().getEpisode()));
    }

    public void addFavorite(EpisodeEntity episodeEntity) {
        AsyncTask.execute(() -> {
            if (getFavorite(episodeEntity.getEpisodeTitle()) == null) {
                favoritesDatabase.favoritesDao()
                        .insertFavorite(EntityConverterUtil.episodeEntityToFavoriteEntity(episodeEntity));
            }
        });
    }

    public FavoriteEntity getFavorite(String episodeTitle) {
        return favoritesDatabase.favoritesDao().getFavorite(episodeTitle);
    }

    public void removeFavorite(EpisodeEntity episodeEntity) {
        AsyncTask.execute(() -> {
            if (getFavorite(episodeEntity.getEpisodeTitle()) != null) {
                favoritesDatabase.favoritesDao()
                        .removeFavorite(EntityConverterUtil.episodeEntityToFavoriteEntity(episodeEntity)
                                .getEpisodeTitle());
            }
        });
    }

    public boolean isBound() {
        return isBound;
    }

    public void setIsBound(boolean isBound) {
        this.isBound = isBound;
    }
}
