package com.example.chenminyao.hootsuiteassignment.Binding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

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
    public static void textSubmit(SearchView searchView, ReplyCommand<String> textSubmitCommand){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                textSubmitCommand.execute(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(final RecyclerView recyclerView, final ReplyCommand<Integer> onLoadMoreCommand) {
        RecyclerView.OnScrollListener listener = new OnScrollListener(onLoadMoreCommand);
        recyclerView.addOnScrollListener(listener);

    }

    public static class OnScrollListener extends RecyclerView.OnScrollListener {
        int i=1;
        private PublishSubject<Integer> methodInvoke = PublishSubject.create();

        private ReplyCommand<Integer> onLoadMoreCommand;

        public OnScrollListener(ReplyCommand<Integer> onLoadMoreCommand) {
            this.onLoadMoreCommand = onLoadMoreCommand;
            methodInvoke.throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(c -> {
                        onLoadMoreCommand.execute(c);
                        Log.d("debug", "OnScrollListener: "+i++);
                    });
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                if (onLoadMoreCommand != null) {
                    methodInvoke.onNext(recyclerView.getAdapter().getItemCount());
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }


    }
}
