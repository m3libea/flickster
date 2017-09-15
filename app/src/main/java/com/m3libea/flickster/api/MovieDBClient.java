package com.m3libea.flickster.api;

import android.util.Log;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by m3libea on 9/13/17.
 */

public class MovieDBClient {

    private String key;
    private String baseURL = "https://api.themoviedb.org/3/";

    private OkHttpClient client;

    public MovieDBClient(String key) {
        this.key = key;
        client = new OkHttpClient();


    }

    public void getNowPlaying(Callback cb) {

        String url = String.format("%smovie/now_playing?api_key=%s",baseURL, key);
        Log.d("MovieClient", url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(cb);
    }

    public void getTrailer(int movieID, Callback cb) {
        String url = String.format("%smovie/%d/videos?api_key=%s", baseURL, movieID, key);
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(cb);

    }


}
