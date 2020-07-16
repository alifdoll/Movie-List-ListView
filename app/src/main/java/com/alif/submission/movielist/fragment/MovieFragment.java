package com.alif.submission.movielist.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alif.submission.movielist.OnActionListener;
import com.alif.submission.movielist.R;
import com.alif.submission.movielist.adapter.MovieAdapter;
import com.alif.submission.movielist.data.MovieItem;
import com.alif.submission.movielist.databinding.FragmentMovieBinding;
import com.alif.submission.movielist.detail.MovieDetail;
import com.alif.submission.movielist.viewmodel.MovieMainViewModel;

import java.util.ArrayList;

public class MovieFragment extends Fragment implements OnActionListener {

    private MovieAdapter adapter;
    private MovieMainViewModel mainViewModel;
    private FragmentMovieBinding binding;


    public MovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMovieBinding.bind(view);

        adapter = new MovieAdapter();
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieMainViewModel.class);
        binding.rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvMovie.setAdapter(adapter);
        binding.rvMovie.addItemDecoration(new DividerItemDecoration(binding.rvMovie.getContext(), DividerItemDecoration.VERTICAL));

        mainViewModel.setMovie();
        showLoading(true);
        mainViewModel.getMovie().observe(getViewLifecycleOwner(), new Observer<ArrayList<MovieItem>>() {
            @Override
            public void onChanged(ArrayList<MovieItem> movieItems) {
                if (movieItems != null) {
                    adapter.setData(getContext(), movieItems, MovieFragment.this);
                    showLoading(false);
                }
            }
        });
    }


    private void showLoading(Boolean state) {
        if (state) {
            binding.idProgress.setVisibility(View.VISIBLE);
        } else {
            binding.idProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void startActivity(int position) {
        MovieItem movie = mainViewModel.getListMovie().get(position);
        Intent intent = new Intent(getActivity(), MovieDetail.class);
        intent.putExtra(MovieDetail.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void onDeleteFromFavorite(int position) {

    }
}
