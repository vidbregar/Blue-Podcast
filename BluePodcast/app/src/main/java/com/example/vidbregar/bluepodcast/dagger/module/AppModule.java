package com.example.vidbregar.bluepodcast.dagger.module;

import dagger.Module;

@Module(includes = {ViewModelFactoryModule.class,
        FirebaseModule.class})
public class AppModule {

}
