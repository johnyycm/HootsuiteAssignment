package com.example.chenminyao.hootsuiteassignment.Model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by chenminyao on 2017-07-03.
 */

public interface MovieApi {
    @GET("search/movie")
    Observable<MovieData> getMovies(@Query("query") String query, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("configuration")
    Observable<ConfigurationData> getConfiguration(@Query("api_key") String apiKey);

}
