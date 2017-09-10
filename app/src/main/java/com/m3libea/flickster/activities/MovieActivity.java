package com.m3libea.flickster.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.m3libea.flickster.R;
import com.m3libea.flickster.adapters.MovieArrayAdapter;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends Activity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    @BindView(R.id.lvmovies)ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        setElements();
        apiRequest();
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
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);
                    JSONArray movieJsonResults = jsonObject.getJSONArray("results");
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));

                    MovieActivity.this.runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             movieAdapter.notifyDataSetChanged();
                         }
                     });
                    Log.d("Debug", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        });
    }
}
