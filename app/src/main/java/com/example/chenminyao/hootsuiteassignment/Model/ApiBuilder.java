package com.example.chenminyao.hootsuiteassignment.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenminyao on 2017-07-03.
 */

public class ApiBuilder {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public MovieApi getMovieApi() {
        return movieApi;
    }

    private final MovieApi movieApi;
    final static Gson gson = new GsonBuilder()
            .serializeNulls()
            .create();
    public ApiBuilder() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(12, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        movieApi = retrofit.create(MovieApi.class);
    }
}
