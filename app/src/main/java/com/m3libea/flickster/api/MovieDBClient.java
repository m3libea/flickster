package com.m3libea.flickster.api;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by m3libea on 9/13/17.
 */

public class MovieDBClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private String key;

    private OkHttpClient client;

    public MovieDBClient(OkHttpClient client, String key) {
        this.key = key;
        this.client = client;
    }

    public void getNowPlaying(Callback cb) {

        String url = String.format("%smovie/now_playing?api_key=%s",BASE_URL, key);
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(cb);
    }

    public void getTrailer(int movieID, Callback cb) {
        String url = String.format("%smovie/%d/videos?api_key=%s", BASE_URL, movieID, key);
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(cb);

    }


}
