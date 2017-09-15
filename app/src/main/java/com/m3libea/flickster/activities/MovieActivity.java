package com.m3libea.flickster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.m3libea.flickster.FlicksterApplication;
import com.m3libea.flickster.R;
import com.m3libea.flickster.adapters.MovieArrayAdapter;
import com.m3libea.flickster.api.MovieDBClient;
import com.m3libea.flickster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {

    private MovieDBClient api;

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    @BindView(R.id.lvmovies)ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        api = ((FlicksterApplication)this.getApplication()).getApi();

        setElements();
        apiRequest();
        Log.d("Debug", movies.toString());

    }

    private void setElements() {
        ButterKnife.bind(this);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie m = movies.get(position);

                Intent i = null;
                if (m.getStars() > 5) {
                    i = new Intent(MovieActivity.this, TrailerActivity.class);
                    i.putExtra("play", true);
                }else {
                    i = new Intent(MovieActivity.this, MovieDetailActivity.class);
                }
                i.putExtra("movie", Parcels.wrap(m));
                startActivity(i);
            }
        });
    }

    private void apiRequest() {
        api.getNowPlaying(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    Log.d("MovieActivity", responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray movieJsonResults = jsonObject.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));

                    MovieActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            movieAdapter.notifyDataSetChanged();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        });
    }
}
