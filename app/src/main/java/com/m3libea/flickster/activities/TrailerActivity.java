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
import com.m3libea.flickster.models.Movie;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

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

            final Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

            client = new OkHttpClient();
            movieId = movie.getID();
            play = getIntent().getBooleanExtra("play", false);

            youTubePlayerView.initialize(youtubeApiKey,
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {

                            String key = movie.getYoutubeKey();
                            if (play) {
                                youTubePlayer.loadVideo(key);
                            } else {
                                youTubePlayer.cueVideo(key);
                            }
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
}
