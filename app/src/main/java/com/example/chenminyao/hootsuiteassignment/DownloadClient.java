package com.example.chenminyao.hootsuiteassignment;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenminyao on 2017-06-02.
 */

public class DownloadClient {
    private DownloadListener downloadListener;
    private Gson gson;
    private Context mContext;

    public DownloadClient(Context context, DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
        gson = new Gson();
        mContext = context;
    }

    public void getMovies(String query, int page) {
        String url = "https://api.themoviedb.org/3/search/";
        url = url + "movie?query=" + query + "&api_key=5b2b06134bae31ee8534bd84fcf59eea" + "&page=" + page;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(JsonObjectRequest.Method.GET, url, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<Movie> listOfMovies = new ArrayList<>();
                            int total_results = response.getInt("total_results");
                            int page = response.getInt("page");
                            int totalPages = response.getInt("total_pages");
                            if (total_results > 0) {
                                JSONArray results = response.getJSONArray("results");
                                for (int i = 0; i < results.length(); i++) {
                                    JSONObject movieObj = results.getJSONObject(i);
                                    Movie movie = gson.fromJson(movieObj.toString(), Movie.class);
                                    listOfMovies.add(movie);
                                }
                                downloadListener.downloadFinished(listOfMovies, page == totalPages);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        MySingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
    }

    public void getConfig(){
        //Try to get Config, but returned
//                 "status_code": 7,
//                "status_message": "Invalid API key: You must be granted a valid key."

        String url = "https://api.themoviedb.org/3/5b2b06134bae31ee8534bd84fcf59eea";
    }
}
