package com.example.chenminyao.hootsuiteassignment;

import java.util.List;

/**
 * Created by chenminyao on 2017-06-02.
 */

public interface DownloadListener {
    void downloadFinished(List<Movie> results, boolean noMoreContent);
}

