package com.m3libea.flickster.activities;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.m3libea.flickster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TrailerActivity extends YouTubeBaseActivity {

    OkHttpClient client;
    boolean play;

    @BindView(R.id.player) YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        ButterKnife.bind(this);

        String youtubeApiKey;
        final int movieId;


        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            youtubeApiKey = bundle.getString("YOUTUBE_API_KEY");

            client = new OkHttpClient();
            movieId = getIntent().getIntExtra("id", -1);
            play = getIntent().getBooleanExtra("play", false);

            youTubePlayerView.initialize(youtubeApiKey,
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {

                            String url = String.format("https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed", movieId);
                            Request request = new Request.Builder()
                                    .url(url)
                                    .build();

                            client.newCall(request).enqueue(initializeYoutube(youTubePlayer));


                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {
                            Toast.makeText(TrailerActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Trailer Activity", "Failed to load meta-data, NameNotFound: " + e.getMessage());
        }
    }

    private Callback initializeYoutube(final YouTubePlayer youTubePlayer) {

            return new Callback() {
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    JSONArray youtubeTrailerArray = null;

                    try {
                        String data = response.body().string();
                        JSONObject json = new JSONObject(data);
                        youtubeTrailerArray = json.getJSONArray("results");

                        if (youtubeTrailerArray.length() > 0) {
                            JSONObject trailer= (JSONObject) youtubeTrailerArray.get(0);
                            String key = trailer.getString("key");
                            if (play) {
                                youTubePlayer.loadVideo(key);
                            } else {
                                youTubePlayer.cueVideo(key);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
            };
    }
}
