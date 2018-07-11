package com.example.vidbregar.bluepodcast.model.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PodcastClient {

    private static final String BASE_URL = "https://listennotes.p.mashape.com/";

    private Retrofit retrofit;
    private PodcastService podcastService;

    public PodcastClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        podcastService = retrofit.create(PodcastService.class);
    }

    public PodcastService getPodcastService() {
        return podcastService;
    }
}
