package com.example.vidbregar.bluepodcast.dagger.builder;

import com.example.vidbregar.bluepodcast.dagger.provider.FavoritesFragmentProvider;
import com.example.vidbregar.bluepodcast.dagger.provider.HomeFragmentProvider;
import com.example.vidbregar.bluepodcast.dagger.provider.SearchFragmentProvider;
import com.example.vidbregar.bluepodcast.ui.main.MainActivity;
import com.example.vidbregar.bluepodcast.ui.player.PlayerActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {HomeFragmentProvider.class,
            FavoritesFragmentProvider.class,
            SearchFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector()
    abstract PlayerActivity bindPlayerActivity();

}
