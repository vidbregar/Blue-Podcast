package com.example.vidbregar.bluepodcast.dagger.provider;

import com.example.vidbregar.bluepodcast.ui.main.favorites.FavoritesFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FavoritesFragmentProvider {

    @ContributesAndroidInjector
    abstract FavoritesFragment bindFavoritesFragment();

}
