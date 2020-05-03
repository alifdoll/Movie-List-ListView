package com.alif.submission.movielist.detail;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alif.submission.movielist.R;
import com.alif.submission.movielist.data.MovieItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MovieDetail extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    //private boolean mov = getIntent().getBooleanExtra("IS SHOW", false);

    public MovieDetail(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
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


        MovieItem movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
//        if (!mov) {
//            this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
//        } else {
//            this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
//        }

        tvMovieName.setText(movie.getTitle());
        tvMovieOverview.setText(movie.getOverview());

        Glide.with(this)
                .load(movie.getPoster_path())
                .apply(new RequestOptions())
                .into(ivMoviePhoto);

        Glide.with(this)
                .load(movie.getPoster_path())
                .apply(new RequestOptions())
                .into(ivMovieBg);
//        ivMoviePhoto.setImageResource(movie.getPhoto());
//        ivMovieBg.setImageResource(movie.getPhoto());

        getSupportActionBar().setTitle(movie.getTitle());
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
        MovieDetail.this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
//        if (!mov) {
//            MovieDetail.this.overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
//        } else {
//            MovieDetail.this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
//        }

    }


}
