package com.m3libea.flickster;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.m3libea.flickster.api.MovieDBClient;

import okhttp3.OkHttpClient;

/**
 * Created by m3libea on 9/13/17.
 */

public class FlicksterApplication extends Application {

    public static String TAG = "FlicksterApplication";

    private OkHttpClient httpClient;
    private MovieDBClient api;

    public static String youtubeApiKey;
    public static String movieDBApiKey;

    public MovieDBClient getApi() {
        return api;
    }

    public String getYoutubeApiKey() {
        return youtubeApiKey;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            youtubeApiKey = bundle.getString("YOUTUBE_API_KEY");
            movieDBApiKey = bundle.getString("MOVIEDB_API_KEY");

        } catch (PackageManager.NameNotFoundException e)  {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }

        httpClient = new OkHttpClient();
        api = new MovieDBClient(httpClient, movieDBApiKey);

    }
}
