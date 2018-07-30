package com.example.vidbregar.bluepodcast.dagger.module;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {

    @Singleton
    @Provides
    FirebaseAnalytics provideFirebaseAnalytics(Application applicationContext) {
        return FirebaseAnalytics.getInstance(applicationContext);
    }
}
