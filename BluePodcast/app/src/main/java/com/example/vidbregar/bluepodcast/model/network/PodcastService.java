package com.example.vidbregar.bluepodcast.model.network;

import com.example.vidbregar.bluepodcast.model.data.Podcast;
import com.example.vidbregar.bluepodcast.model.data.PodcastGenre;
import com.example.vidbregar.bluepodcast.model.data.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PodcastService {

    @GET("api/v1/best_podcasts")
    Call<PodcastGenre> getBestPodcasts(@Header("X-Mashape-Key") String xMashapeKey);

    @GET("api/v1/best_podcasts")
    Call<PodcastGenre> getGenrePodcasts(@Header("X-Mashape-Key") String xMashapeKey,
                                        @Query("genre_id") int genreId);

    @GET("api/v1/podcasts/{podcastId}")
    Call<Podcast> getPodcast(@Header("X-Mashape-Key") String xMashapeKey,
                             @Path("podcastId") String podcastId);


    @GET("api/v1/search")
    Call<Search> search(@Header("X-Mashape-Key") String xMashapeKey,
                        @Query(value = "q", encoded = true) String query);

}
