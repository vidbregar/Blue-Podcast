package com.example.vidbregar.bluepodcast.model.network;

import com.example.vidbregar.bluepodcast.model.data.PodcastGenre;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PodcastService {

    @Headers("Accept: application/json")
    @GET("api/v1/best_podcasts")
    Call<PodcastGenre> getBestPodcasts(@Header("X-Mashape-Key") String xMashapeKey);

    @Headers("Accept: application/json")
    @GET("api/v1/best_podcasts")
    Call<PodcastGenre> getGenrePodcasts(@Header("X-Mashape-Key") String xMashapeKey,
                                        @Query("genre_id") int genreId);
}
