package com.example.vidbregar.bluepodcast.dagger.module;

import dagger.Module;

@Module(includes = {ViewModelFactoryModule.class,
        DatabaseModule.class})
public class AppModule {

}
