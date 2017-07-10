package com.example.chenminyao.hootsuiteassignment.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.example.chenminyao.hootsuiteassignment.BR;
import com.example.chenminyao.hootsuiteassignment.Binding.ItemBinder;
import com.example.chenminyao.hootsuiteassignment.Binding.ItemBinderBase;
import com.example.chenminyao.hootsuiteassignment.Model.ApiBuilder;
import com.example.chenminyao.hootsuiteassignment.Model.ConfigurationData;
import com.example.chenminyao.hootsuiteassignment.Model.MovieApi;
import com.example.chenminyao.hootsuiteassignment.Model.MovieData;
import com.example.chenminyao.hootsuiteassignment.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenminyao on 2017-07-09.
 */

public class MainActivityViewModel extends BaseObservable {
    public final ObservableField<RecyclerView.LayoutManager> layoutManager = new ObservableField<>();
    public final ObservableList<MovieItemViewModel> itemList = new ObservableArrayList<>();

    public ItemBinder<MovieItemViewModel> movieItemView() {
        return new ItemBinderBase<MovieItemViewModel>(BR.itemVM, R.layout.recycler_item_view);
    }
    public SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            itemList.clear();
            loadData(query, 1);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    private Context context;
    String apiKey;

    public MainActivityViewModel(Context context) {
        this.context = context;
        apiKey = context.getString(R.string.API_key);
        layoutManager.set(new LinearLayoutManager(this.context));
        loadData("big", 1);
    }

    private void loadData(String query, int page) {
        MovieApi movieApi = new ApiBuilder().getMovieApi();
        Observable<MovieData> movieDataObservable = movieApi.getMovies(query, apiKey, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observable<ConfigurationData> configurationDataObservable = movieApi.getConfiguration(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Observable.zip(movieDataObservable, configurationDataObservable, (movieData, configurationData) -> {
            movieData.setImageUrl(configurationData.images.baseUrl, configurationData.images.backdropSizes.get(0));
            return movieData;
        })
                .subscribe(movieData -> {
                    movieData.results.forEach(movie -> {
                        itemList.add(new MovieItemViewModel(movie));
                    });
                });

    }
}
