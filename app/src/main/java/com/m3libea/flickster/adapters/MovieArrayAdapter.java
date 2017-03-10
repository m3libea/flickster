package com.m3libea.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m3libea.flickster.R;
import com.m3libea.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by m3libea on 3/7/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    @BindView(R.id.ivMovie) ImageView ivImage;
    @BindView(R.id.ivplay) ImageView ivPlay;
    @Nullable @BindView(R.id.tvTitle) TextView tvTitle;
    @Nullable @BindView(R.id.tvOverview) TextView tvOverview;

    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return isPopular(getItem(position))? 0: 1;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for position

        Movie movie = getItem(position);

        //check the existing view being reused
        if (convertView == null){
            int type = getItemViewType(position);
            convertView = getInflatedLayoutForType(type);
        }

        ButterKnife.bind(this, convertView);

        //clear out image from convertView
        ivImage.setImageResource(0);

        int orientation = getContext().getResources().getConfiguration().orientation;

        if(getItemViewType(position) == 1 || orientation == Configuration.ORIENTATION_LANDSCAPE){
            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(movie.getOverview());
        }

        if (isPopular(movie)){
            //Insert image
            Picasso.with(getContext()).load(movie.getBackdropPath())
                    .transform(new RoundedCornersTransformation(10, 10))
                    .placeholder(R.drawable.syncph)
                    .error(R.drawable.errorph)
                    .into(ivImage);
            //Insert play button
            Picasso.with(getContext()).load(R.drawable.play)
                    .into(ivPlay);

        }else{
            Picasso.with(getContext()).load(movie.getPosterPath())
                    .transform(new RoundedCornersTransformation(10, 10))
                    .placeholder(R.drawable.syncph)
                    .error(R.drawable.errorph)
                    .into(ivImage);
        }


        return convertView;

    }

    private boolean isPopular(Movie movie) {
        return (movie.getStars() > 5);
    }

    private View getInflatedLayoutForType(int type) {
        int orientation = getContext().getResources().getConfiguration().orientation;

        if (type == 0 && orientation != Configuration.ORIENTATION_LANDSCAPE) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_popular_movie, null);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
        }
    }
}
