package com.example.chenminyao.hootsuiteassignment.Binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Collection;

import io.reactivex.Observable;

/**
 * Created by chenminyao on 2017-07-08.
 */

public class MyBindingAdapter {
    @android.databinding.BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }

    private static final int KEY_ITEMS = -123;

    @SuppressWarnings("unchecked")
    @android.databinding.BindingAdapter("items")
    public static <T> void setItems(RecyclerView recyclerView, Collection<T> items) {
        BindingRecyclerViewAdapter<T> adapter = (BindingRecyclerViewAdapter<T>) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItems(items);
        } else {
            recyclerView.setTag(KEY_ITEMS, items);
        }
    }

    @SuppressWarnings("unchecked")
    @android.databinding.BindingAdapter("itemView")
    public static <T> void setItemViewBinder(RecyclerView recyclerView, ItemBinder<T> itemViewMapper) {
        Collection<T> items = (Collection<T>) recyclerView.getTag(KEY_ITEMS);
        BindingRecyclerViewAdapter<T> adapter = new BindingRecyclerViewAdapter<>(itemViewMapper, items);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("textSubmit")
    public static void textSubmit(SearchView searchView, SearchView.OnQueryTextListener listener){
        searchView.setOnQueryTextListener(listener);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"onLoadMoreCommand"})
    public static<T> void onLoadMoreCommand(final RecyclerView recyclerView, Observable observable) {
        BindingRecyclerViewAdapter<T> adapter = (BindingRecyclerViewAdapter<T>) recyclerView.getAdapter();
        int itemLeft = adapter.getItemLeft().get();
//        if(itemLeft>7)
    }

}
