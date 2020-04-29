package com.alif.submission.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MovieDetail extends AppCompatActivity {

    static final String EXTRA_MOVIE = "extra_movie";

    public MovieDetail(){

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);


        ImageView ivMoviePhoto = findViewById(R.id.movie_img);
        ImageView ivDirectPhoto = findViewById(R.id.movie_img_director);
        ImageView ivMovieBg = findViewById(R.id.movie_poster_bg);
        TextView tvMovieName = findViewById(R.id.movie_name);
        TextView tvMovieOverview = findViewById(R.id.movie_synops);
        TextView tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        TextView tvDirectName = findViewById(R.id.tv_director_name);

        RatingBar rb = findViewById(R.id.movie_rating);
        rb.setRating(5);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        tvMovieName.setText(movie.getName());
        tvMovieOverview.setText(movie.getOverview());
        tvMovieReleaseDate.setText(movie.getReleaseDate());
        tvDirectName.setText(movie.getDirectorName());

        ivMoviePhoto.setImageResource(movie.getPhoto());
        ivDirectPhoto.setImageResource(movie.getDirectorPhoto());
        ivMovieBg.setImageResource(movie.getPhoto());

        getSupportActionBar().setTitle(movie.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(MovieDetail.this,MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MovieDetail.this.overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
    }


}
