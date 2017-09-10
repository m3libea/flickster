package com.m3libea.flickster.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.m3libea.flickster.R;
import com.squareup.picasso.Picasso;

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


    int movieID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);

        ButterKnife.bind(this);

        //Change stars color
        rbStars.setRating(getIntent().getExtras().getInt("stars")/2);
        LayerDrawable stars = (LayerDrawable) rbStars.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        tvTitle.setText(getIntent().getExtras().getString("title"));
        tvSynopsis.setText(getIntent().getExtras().getString("overview"));
        tvRelease.setText(getIntent().getExtras().getString("release"));

        movieID = getIntent().getExtras().getInt("id");


        Picasso.with(this).load(getIntent().getExtras().getString("image"))
                .placeholder(R.drawable.syncph)
                .error(R.drawable.errorph)
                .into(ivPoster);
        Picasso.with(this).load(R.drawable.play)
                .into(ivPlay);

    }

    @OnClick({ R.id.ivplay, R.id.flayout })

    public void playYoutube(){
        Intent i = new Intent(MovieDetailActivity.this, TrailerActivity.class);
        i.putExtra("id", movieID);
        startActivity(i);
    }
}
