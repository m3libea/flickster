package com.m3libea.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by m3libea on 3/7/17.
 */

@Parcel
public class Movie {

    String posterPath;
    String originalTitle;
    String overview;
    String backdropPath;
    int stars;
    String releaseDate;
    String youtubeKey;

    int ID;


    public Movie(){

    }
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w1000/%s", backdropPath);
    }

    public int getStars() {
        return stars;
    }
    public int getID() {
        return ID;
    }
    public String getYoutubeKey() {
        return youtubeKey;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.stars = jsonObject.getInt("vote_average");
        this.releaseDate = jsonObject.getString("release_date");
        this.ID = jsonObject.getInt("id");
        this.getTrailerAPI();

    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> results = new ArrayList<>();

        for(int i = 0; i < array.length(); i++) {
            try {
                results.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    public void getTrailerAPI() {
        String url = String.format("https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", ID);
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                JSONArray youtubeTrailerArray = null;

                try {
                    String data = response.body().string();
                    JSONObject json = new JSONObject(data);
                    youtubeTrailerArray = json.getJSONArray("results");

                    if (youtubeTrailerArray.length() > 0) {
                        JSONObject trailer= (JSONObject) youtubeTrailerArray.get(0);
                        youtubeKey = trailer.getString("key");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });

    }
}
