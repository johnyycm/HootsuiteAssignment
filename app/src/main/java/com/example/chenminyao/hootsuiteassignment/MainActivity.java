package com.example.chenminyao.hootsuiteassignment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DownloadListener, LoadMoreListener, SearchView.OnQueryTextListener {
    private RecyclerView mRecyclerView;
    private SearchView searchView;
    private MyRecyclerViewAdapter mAdapter;
    private DownloadClient downloadClient;
    private int page = 1;
    private boolean isLoading = false;
    private boolean noMoreContent = false;
    private String query = "big";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = (SearchView) findViewById(R.id.my_search_view);
        searchView.setQuery(query, false);
        searchView.setOnQueryTextListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        downloadClient = new DownloadClient(getApplicationContext(), this);
        isLoading = true;
        downloadClient.getMovies(query, page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void downloadFinished(List<Movie> results, boolean noMoreContent) {
        isLoading = false;
        this.noMoreContent = noMoreContent;
        if (mAdapter == null) {
            mAdapter = new MyRecyclerViewAdapter(this, results, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateDataSource(results, page != 1);
        }
    }

    @Override
    public void loadMore(int position) {
        if (!isLoading && !noMoreContent) {
            isLoading = true;
            downloadClient.getMovies(query, ++page);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.query = query;
        this.page = 1;
        isLoading = true;
        noMoreContent = false;
        downloadClient.getMovies(query, page);
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
