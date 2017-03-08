package com.m3libea.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
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

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by m3libea on 3/7/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return isPopular(getItem(position));
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

        //find the image view
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivMovie);

        //clear out image from convertView
        ivImage.setImageResource(0);

        int orientation = getContext().getResources().getConfiguration().orientation;

        if(getItemViewType(position) == 1 || orientation == Configuration.ORIENTATION_LANDSCAPE){
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            //Populate the data
            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(movie.getOverview());
        }

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (getItemViewType(position) == 0){
                Picasso.with(getContext()).load(movie.getBackdropPath())
                        .transform(new RoundedCornersTransformation(10, 10))
                        .placeholder(R.drawable.syncph)
                        .error(R.drawable.errorph)
                        .into(ivImage);
            }else{
                Picasso.with(getContext()).load(movie.getPosterPath())
                        .transform(new RoundedCornersTransformation(10, 10))
                        .placeholder(R.drawable.syncph)
                        .error(R.drawable.errorph)
                        .into(ivImage);
            }
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext()).load(movie.getBackdropPath())
                    .placeholder(R.drawable.syncph)
                    .error(R.drawable.errorph)
                    .into(ivImage);
        }

        return convertView;

    }

    private int isPopular(Movie movie) {
        if(movie.getStars() > 5){
            return 0; //popular
        }else{
            return 1; //no popular
        }
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
