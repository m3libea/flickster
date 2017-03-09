package com.m3libea.flickster.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.m3libea.flickster.R;
import com.squareup.picasso.Picasso;

/**
 * Created by m3libea on 3/8/17.
 */

public class MovieDetailActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_movie);

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        TextView tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        TextView tvRelease = (TextView) findViewById(R.id.tvRDate);
        ImageView ivPoster = (ImageView) findViewById(R.id.ivMovie);
        RatingBar rbStarts = (RatingBar) findViewById(R.id.rbStars);
        LayerDrawable stars = (LayerDrawable) rbStarts.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        tvTitle.setText(getIntent().getExtras().getString("title"));
        tvSynopsis.setText(getIntent().getExtras().getString("overview"));
        tvRelease.setText(getIntent().getExtras().getString("release"));
        rbStarts.setRating(getIntent().getExtras().getInt("stars")/2);

        Picasso.with(this).load(getIntent().getExtras().getString("image"))
                .placeholder(R.drawable.syncph)
                .error(R.drawable.errorph)
                .into(ivPoster);
    }
}
