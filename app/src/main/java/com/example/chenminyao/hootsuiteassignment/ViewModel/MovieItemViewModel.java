package com.example.chenminyao.hootsuiteassignment.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.example.chenminyao.hootsuiteassignment.Model.Movie;

import io.reactivex.Observable;

/**
 * Created by chenminyao on 2017-07-09.
 */

public class MovieItemViewModel extends BaseObservable {
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();


    public MovieItemViewModel(Movie movie) {
        title.set(movie.title);
        description.set(movie.overview);
        imageUrl.set(movie.imageUrl);
    }
}
