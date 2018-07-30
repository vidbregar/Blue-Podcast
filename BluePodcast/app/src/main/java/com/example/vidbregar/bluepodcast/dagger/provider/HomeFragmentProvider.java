package com.example.vidbregar.bluepodcast.dagger.provider;

import com.example.vidbregar.bluepodcast.ui.main.home.HomeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HomeFragmentProvider {

    @ContributesAndroidInjector
    abstract HomeFragment bindHomeFragment();

}
