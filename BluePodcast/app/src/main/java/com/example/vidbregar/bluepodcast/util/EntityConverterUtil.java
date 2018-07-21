package com.example.vidbregar.bluepodcast.util;

import com.example.vidbregar.bluepodcast.model.database.episode.EpisodeEntity;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoriteEntity;

public class EntityConverterUtil {

    public static FavoriteEntity episodeEntityToFavoriteEntity(EpisodeEntity episodeEntity) {
        return new FavoriteEntity(episodeEntity.getEpisodeTitle(),
                episodeEntity.getThumbnailUrl(),
                episodeEntity.getPublisher(),
                episodeEntity.getAudioUrl());
    }
}
