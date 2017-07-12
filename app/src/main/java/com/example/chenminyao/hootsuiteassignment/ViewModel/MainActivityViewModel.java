package com.example.chenminyao.hootsuiteassignment.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chenminyao.hootsuiteassignment.BR;
import com.example.chenminyao.hootsuiteassignment.Binding.ItemBinder;
import com.example.chenminyao.hootsuiteassignment.Binding.ItemBinderBase;
import com.example.chenminyao.hootsuiteassignment.Binding.ReplyCommand;
import com.example.chenminyao.hootsuiteassignment.Model.ApiBuilder;
import com.example.chenminyao.hootsuiteassignment.Model.ConfigurationData;
import com.example.chenminyao.hootsuiteassignment.Model.MovieApi;
import com.example.chenminyao.hootsuiteassignment.Model.MovieData;
import com.example.chenminyao.hootsuiteassignment.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenminyao on 2017-07-09.
 */

public class MainActivityViewModel extends BaseObservable {
    private Context context;
    private String apiKey;
    private int searchPage = 1;
    private String query = "big";
    private int totalPage;
    private boolean isLoading = false;


    public final ObservableField<RecyclerView.LayoutManager> layoutManager = new ObservableField<>();
    public final ObservableList<MovieItemViewModel> itemList = new ObservableArrayList<>();

    public ItemBinder<MovieItemViewModel> movieItemView() {
        return new ItemBinderBase<MovieItemViewModel>(BR.itemVM, R.layout.recycler_item_view);
    }

    public final ReplyCommand<String> textSubmitCommand = new ReplyCommand<String>(s -> {
        itemList.clear();
        searchPage = 1;
        query = s;
        loadData(s, searchPage);
    });

    public final ReplyCommand<Integer> onLoadMoreCommand = new ReplyCommand<Integer>(i -> {
        if (searchPage <= totalPage)
            loadData(query, searchPage);
    });


    public MainActivityViewModel(Context context) {
        this.context = context;
        apiKey = context.getString(R.string.API_key);
        layoutManager.set(new LinearLayoutManager(this.context));
        loadData(query, searchPage);
    }

    private void loadData(String query, int page) {
        if (isLoading) return;
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
                .subscribe(new Observer<MovieData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        isLoading = true;
                    }

                    @Override
                    public void onNext(@NonNull MovieData movieData) {
                        totalPage = movieData.totalPages;
                        searchPage++;
                        movieData.results.forEach(movie -> {
                            itemList.add(new MovieItemViewModel(movie));
                        });
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isLoading = false;
                    }

                    @Override
                    public void onComplete() {
                        isLoading = false;
                    }
                });

    }
}
