package com.m3libea.flickster.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.m3libea.flickster.FlicksterApplication;
import com.m3libea.flickster.R;
import com.m3libea.flickster.api.MovieDBClient;
import com.m3libea.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by m3libea on 3/8/17.
 */

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.tvSynopsis) TextView tvSynopsis;
    @BindView(R.id.ivMovie) ImageView ivPoster;
    @BindView(R.id.rbStars) RatingBar rbStars;
    @BindView(R.id.ivplay) ImageView ivPlay;

    private MovieDBClient api;

    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);

        ButterKnife.bind(this);

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        getSupportActionBar().setTitle(movie.getOriginalTitle());

        //Change stars color
        rbStars.setRating(movie.getStars()/2);
        LayerDrawable stars = (LayerDrawable) rbStars.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null), PorterDuff.Mode.SRC_ATOP);

        tvSynopsis.setText(movie.getOverview());

        Picasso.with(this).load(movie.getBackdropPath())
                .placeholder(R.drawable.syncph)
                .error(R.drawable.errorph)
                .into(ivPoster);

        Picasso.with(MovieDetailActivity.this).load(R.drawable.play)
                .into(ivPlay);

        api = ((FlicksterApplication)this.getApplication()).getApi();


        api.getTrailer(movie.getID(),new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                JSONArray youtubeTrailerArray = null;

                try {
                    String data = response.body().string();
                    JSONObject json = new JSONObject(data);
                    youtubeTrailerArray = json.getJSONArray("results");

                    if (youtubeTrailerArray.length() > 0) {
                        JSONObject trailer= (JSONObject) youtubeTrailerArray.get(0);
                        movie.setYoutubeKey(trailer.getString("key"));
                    }

                    MovieDetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (movie.getYoutubeKey() == "" || movie.getYoutubeKey() == null){
                                ivPlay.setVisibility(View.GONE);
                            }
                        }
                    });
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

    @OnClick({ R.id.ivplay})

    public void playYoutube(){
        Intent i = new Intent(MovieDetailActivity.this, TrailerActivity.class);
        i.putExtra("movie", Parcels.wrap(movie));
        startActivity(i);
    }

}
