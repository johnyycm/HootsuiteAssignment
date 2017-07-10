package com.example.chenminyao.hootsuiteassignment.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by chenminyao on 2017-06-02.
 */

public class Movie implements Serializable{
     @SerializedName("poster_path") public String posterPath;
     @SerializedName("overview") public String overview;
     @SerializedName("title") public String title;
     public String imageUrl;

}
