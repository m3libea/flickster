package com.m3libea.flickster.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.m3libea.flickster.FlicksterApplication;
import com.m3libea.flickster.R;
import com.m3libea.flickster.api.MovieDBClient;
import com.m3libea.flickster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private FlicksterApplication app;
    private MovieDBClient api;

    @BindView(R.id.player) YouTubePlayerView youTubePlayerView;

    private Movie movie;
    boolean play;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        ButterKnife.bind(this);

        app = (FlicksterApplication)this.getApplication();
        api = app.getApi();

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        play = getIntent().getBooleanExtra("play", false);

        youTubePlayerView.initialize(app.getYoutubeApiKey(), this);
    }

    public void insertKey(YouTubePlayer youTubePlayer, String key, boolean play){
        if (play) {
            youTubePlayer.loadVideo(key);
        } else {
            youTubePlayer.cueVideo(key);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
        if (movie.getYoutubeKey() == null) {
            api.getTrailer(movie.getID(), new Callback() {
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    JSONArray youtubeTrailerArray = null;

                    try {
                        String data = response.body().string();
                        JSONObject json = new JSONObject(data);
                        youtubeTrailerArray = json.getJSONArray("results");

                        if (youtubeTrailerArray.length() > 0) {
                            JSONObject trailer = (JSONObject) youtubeTrailerArray.get(0);
                            String key = trailer.getString("key");

                            insertKey(youTubePlayer, key, play);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    // TODO Snackbar with retry
                    e.printStackTrace();
                }
            });
        }else {
            insertKey(youTubePlayer,movie.getYoutubeKey(),play);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        // TODO replace with Snackbar
        Toast.makeText(TrailerActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
    }
}
