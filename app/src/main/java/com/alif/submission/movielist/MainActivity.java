package com.alif.submission.movielist;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.alif.submission.movielist.adapter.MovieAdapter;
import com.alif.submission.movielist.data.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;
    private String[] movieName;
    private String[] movieOverview;
    private String[] directorName;
    private String[] movieRelease;
    private TypedArray moviePhoto;
    private TypedArray directorPhoto;
    private ArrayList<Movie> movies;
    private Movie movie;
    private Movie sMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.lv_list);
        movieAdapter = new MovieAdapter(this);
        listView.setAdapter(movieAdapter);

        Prepare();
        AddItem();

        OnStartActivity();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sMovie = movies.get(i);
                Intent intent = new Intent(MainActivity.this, MovieDetail.class);
                intent.putExtra(MovieDetail.EXTRA_MOVIE, sMovie);
                startActivity(intent);
            }
        });
    }


    @Override
    public void finish() {
        super.finish();
        OnLeaveActivity();
    }

    protected void OnLeaveActivity() {
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    protected void OnStartActivity() {
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    private void Prepare() {
        movieName = getResources().getStringArray(R.array.movie_name);
        directorName = getResources().getStringArray(R.array.movie_director_name);
        movieOverview = getResources().getStringArray(R.array.movie_overview);
        movieRelease = getResources().getStringArray(R.array.movie_release_date);
        moviePhoto = getResources().obtainTypedArray(R.array.movie_photo);
        directorPhoto = getResources().obtainTypedArray(R.array.movie_director_photo);
    }


    private void AddItem() {
        movies = new ArrayList<>();
        for (int idx = 0; idx < movieName.length; idx++) {
            movie = new Movie();
            movie.setName(movieName[idx]);
            movie.setOverview(movieOverview[idx]);
            movie.setReleaseDate(movieRelease[idx]);
            movie.setPhoto(moviePhoto.getResourceId(idx, -1));
            movie.setDirectorName(directorName[idx]);
            movie.setDirectorPhoto(directorPhoto.getResourceId(idx, -1));
            movies.add(movie);
        }
        movieAdapter.setMovies(movies);
    }
}
