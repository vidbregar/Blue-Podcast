package com.example.vidbregar.bluepodcast.dagger.module;

import com.example.vidbregar.bluepodcast.model.database.episode.EpisodeDatabase;
import com.example.vidbregar.bluepodcast.model.database.favorites.FavoritesDatabase;
import com.example.vidbregar.bluepodcast.model.network.PodcastService;
import com.example.vidbregar.bluepodcast.util.SharedPreferencesUtil;
import com.example.vidbregar.bluepodcast.viewmodel.FavoritesViewModelFactory;
import com.example.vidbregar.bluepodcast.viewmodel.PlayerViewModelFactory;
import com.example.vidbregar.bluepodcast.viewmodel.PodcastViewModelFactory;
import com.example.vidbregar.bluepodcast.viewmodel.SearchViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {DatabaseModule.class,
        NetworkModule.class})
public class ViewModelFactoryModule {

    @Singleton
    @Provides
    FavoritesViewModelFactory provideFavoritesViewModelFactory(FavoritesDatabase favoritesDatabase) {
        return new FavoritesViewModelFactory(favoritesDatabase);
    }

    @Singleton
    @Provides
    PlayerViewModelFactory providePlayerViewModelFactory(EpisodeDatabase episodeDatabase,
                                                         FavoritesDatabase favoritesDatabase) {
        return new PlayerViewModelFactory(episodeDatabase, favoritesDatabase);
    }

    @Singleton
    @Provides
    SearchViewModelFactory provideSearchViewModelFactory(PodcastService podcastService) {
        return new SearchViewModelFactory(podcastService);
    }

    @Singleton
    @Provides
    PodcastViewModelFactory providePodcastViewModelFactory(PodcastService podcastService, SharedPreferencesUtil sharedPreferencesUtil) {
        return new PodcastViewModelFactory(podcastService, sharedPreferencesUtil);
    }
}
