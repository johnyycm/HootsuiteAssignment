package com.example.chenminyao.hootsuiteassignment;

/**
 * Created by chenminyao on 2017-06-02.
 */

public class Movie {
     String poster_path;
     String overview;
     String title;
     String imageUrl;

     public String getImageUrl() {
          return "https://image.tmdb.org/t/p/w500/" + poster_path;
     }

}
