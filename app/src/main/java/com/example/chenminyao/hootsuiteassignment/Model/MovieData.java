package com.example.chenminyao.hootsuiteassignment.Model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenminyao on 2017-07-03.
 */

public class MovieData implements Serializable {
    @SerializedName("total_results") public int totalResults;
    @SerializedName("page") public int page;
    @SerializedName("total_pages") public int totalPages;
    @SerializedName("results") public List<Movie> results;
    public void setImageUrl(String baseUrl, String size){
        for ( Movie movie : results){
            movie.imageUrl = baseUrl + size + movie.posterPath;
        }
    }
}
