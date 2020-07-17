package com.alif.submission.movielist.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alif.submission.movielist.OnActionListener;
import com.alif.submission.movielist.R;
import com.alif.submission.movielist.adapter.MovieAdapter;
import com.alif.submission.movielist.data.MovieItem;
import com.alif.submission.movielist.database.MovieDatabase;
import com.alif.submission.movielist.databinding.FragmentMovieFavoriteBinding;
import com.alif.submission.movielist.detail.MovieDetail;

import java.util.ArrayList;
import java.util.List;


public class MovieFavoriteFragment extends Fragment implements OnActionListener {

    private FragmentMovieFavoriteBinding binding;
    private final List<MovieItem> listOfMovie = new ArrayList<>();
    private MovieAdapter adapter;
    private TextView textView;

    public MovieFavoriteFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMovieFavoriteBinding.bind(view);
        adapter = new MovieAdapter();


        binding.rvMovieFav.setAdapter(adapter);
        binding.rvMovieFav.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvMovieFav.addItemDecoration(new DividerItemDecoration(binding.rvMovieFav.getContext(), DividerItemDecoration.VERTICAL));

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler();

                listOfMovie.addAll(MovieDatabase.getInstance(getContext())
                        .getMovieDao()
                        .getMovieByType("movie"));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        noData(false);
                        adapter.setData(getContext(), listOfMovie, MovieFavoriteFragment.this);
                    }
                });

                if (listOfMovie.size() <= 0){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            noData(true);
                        }
                    });
                }

                Looper.loop();
            }
        }).start();
    }

    private void noData(Boolean state) {
        if (state) {
            binding.noData.setVisibility(View.VISIBLE);
        } else {
            binding.noData.setVisibility(View.GONE);
        }
    }



    @Override
    public void startActivity(int position) {
        MovieItem movie = listOfMovie.get(position);
        Intent intent = new Intent(getActivity(), MovieDetail.class);
        intent.putExtra(MovieDetail.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void onDeleteFromFavorite(int position) {
        adapter.deleteFavoriteItem(position);
        listOfMovie.remove(position);
        if (listOfMovie.size() == 0){
            noData(true);
        }
    }
}
