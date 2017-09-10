package com.m3libea.flickster.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.m3libea.flickster.R;
import com.m3libea.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by m3libea on 3/8/17.
 */

public class MovieDetailActivity extends Activity {

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvSynopsis) TextView tvSynopsis;
    @BindView(R.id.tvRDate) TextView tvRelease;
    @BindView(R.id.ivMovie) ImageView ivPoster;
    @BindView(R.id.rbStars) RatingBar rbStars;
    @BindView(R.id.ivplay) ImageView ivPlay;


    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);

        ButterKnife.bind(this);

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        //Change stars color
        rbStars.setRating(movie.getStars()/2);
        LayerDrawable stars = (LayerDrawable) rbStars.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        tvTitle.setText(movie.getOriginalTitle());
        tvSynopsis.setText(movie.getOverview());
        tvRelease.setText(movie.getReleaseDate());

        Picasso.with(this).load(movie.getBackdropPath())
                .placeholder(R.drawable.syncph)
                .error(R.drawable.errorph)
                .into(ivPoster);

        if (movie.getYoutubeKey() != null){
            Picasso.with(this).load(R.drawable.play)
                    .into(ivPlay);
        }else{
            ivPlay.setVisibility(View.GONE);
        }


    }

    @OnClick({ R.id.ivplay})

    public void playYoutube(){
        Intent i = new Intent(MovieDetailActivity.this, TrailerActivity.class);
        i.putExtra("movie", Parcels.wrap(movie));
        startActivity(i);
    }
}
