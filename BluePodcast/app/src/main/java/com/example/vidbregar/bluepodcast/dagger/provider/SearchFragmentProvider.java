package com.example.vidbregar.bluepodcast.dagger.provider;

import com.example.vidbregar.bluepodcast.ui.main.search.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SearchFragmentProvider {

    @ContributesAndroidInjector
    abstract SearchFragment bindSearchFragment();

}
