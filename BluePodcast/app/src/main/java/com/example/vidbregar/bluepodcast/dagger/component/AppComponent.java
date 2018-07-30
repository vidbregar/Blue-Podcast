package com.example.vidbregar.bluepodcast.dagger.component;

import android.app.Application;

import com.example.vidbregar.bluepodcast.BluePodcastApplication;
import com.example.vidbregar.bluepodcast.dagger.builder.ActivityBuilder;
import com.example.vidbregar.bluepodcast.dagger.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(BluePodcastApplication app);
}
