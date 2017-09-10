package com.m3libea.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

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

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.stars = jsonObject.getInt("vote_average");
        this.releaseDate = jsonObject.getString("release_date");
        this.ID = jsonObject.getInt("id");

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
}
