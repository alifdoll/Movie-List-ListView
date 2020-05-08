package com.alif.submission.movielist.detail;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alif.submission.movielist.R;
import com.alif.submission.movielist.data.MovieItem;
import com.alif.submission.movielist.databinding.MovieDetailBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MovieDetail extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    //private boolean mov = getIntent().getBooleanExtra("IS SHOW", false);
    private MovieDetailBinding binding;
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
        binding = MovieDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




        MovieItem movie = getIntent().getParcelableExtra(EXTRA_MOVIE);


        binding.movieName.setText(movie.getTitle());
        binding.movieSynops.setText(movie.getOverview());
        binding.movieRating.setRating(5);
        Glide.with(this)
                .load(movie.getPoster_path())
                .apply(new RequestOptions())
                .into(binding.movieImg);

        Glide.with(this)
                .load(movie.getPoster_path())
                .apply(new RequestOptions())
                .into(binding.moviePosterBg);

        getSupportActionBar().setTitle(movie.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
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
    }


}
