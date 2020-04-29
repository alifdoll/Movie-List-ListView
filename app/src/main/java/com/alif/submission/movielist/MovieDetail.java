package com.alif.submission.movielist;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alif.submission.movielist.data.Movie;

public class MovieDetail extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_SHOW = "extra_show";

    private boolean mov = true;

    public MovieDetail(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);


        ImageView ivMoviePhoto = findViewById(R.id.movie_img);
        ImageView ivMovieBg = findViewById(R.id.movie_poster_bg);
        TextView tvMovieName = findViewById(R.id.movie_name);
        TextView tvMovieOverview = findViewById(R.id.movie_synops);

        RatingBar rb = findViewById(R.id.movie_rating);
        rb.setRating(5);


        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);


        if (movie.getIsMovie()) {
            this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        } else {
            this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            mov = movie.getIsMovie();
        }

        tvMovieName.setText(movie.getName());
        tvMovieOverview.setText(movie.getOverview());

        ivMoviePhoto.setImageResource(movie.getPhoto());
        ivMovieBg.setImageResource(movie.getPhoto());

        getSupportActionBar().setTitle(movie.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mov) {
            MovieDetail.this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        } else {
            MovieDetail.this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        }

    }


}
