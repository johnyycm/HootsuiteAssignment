package com.example.chenminyao.hootsuiteassignment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by chenminyao on 2017-06-02.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView overView;
        NetworkImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_textView);
            overView = (TextView) itemView.findViewById(R.id.overview_textView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.my_image_view);
        }
    }

    private List<Movie> movies;
    private Context mContext;
    private LoadMoreListener loadMoreListener;

    public MyRecyclerViewAdapter(Context context, List<Movie> movies, LoadMoreListener loadMoreListener) {
        this.movies = movies;
        mContext = context;
        this.loadMoreListener = loadMoreListener;
    }

    public void updateDataSource(List<Movie> movies, boolean append) {
        if (!append) {
            this.movies = movies;
        } else {
            this.movies.addAll(movies);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Movie movie = movies.get(position);
        myViewHolder.title.setText(movie.title);
        myViewHolder.overView.setText(movie.overview);
        myViewHolder.imageView.setImageUrl(movie.getImageUrl(), MySingleton.getInstance(mContext.getApplicationContext()).getImageLoader());
        if (movies.size() < position + 8) {
            loadMoreListener.loadMore(position);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size() > 0 ? movies.size() : 0;
    }
}
