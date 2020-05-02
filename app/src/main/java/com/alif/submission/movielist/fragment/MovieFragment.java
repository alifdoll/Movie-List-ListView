package com.alif.submission.movielist.fragment;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alif.submission.movielist.MovieDetail;
import com.alif.submission.movielist.R;
import com.alif.submission.movielist.adapter.MovieAdapter;
import com.alif.submission.movielist.data.Movie;

import java.util.ArrayList;

public class MovieFragment extends Fragment implements MovieAdapter.OnActionListener {


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

        RecyclerView recyclerView = view.findViewById(R.id.rv_movie);

        MovieAdapter adapter = new MovieAdapter(getListMovie(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }

    private ArrayList<Movie> getListMovie() {
        String[] movieName = getResources().getStringArray(R.array.movie_name);
        String[] movieOverview = getResources().getStringArray(R.array.movie_overview);
        TypedArray moviePhoto = getResources().obtainTypedArray(R.array.movie_photo);

        ArrayList<Movie> listMovie = new ArrayList<Movie>() {
        };
        for (int idx = 0; idx < movieName.length; idx++) {
            Movie movies = new Movie();
            movies.setName(movieName[idx]);
            movies.setOverview(movieOverview[idx]);
            movies.setPhoto(moviePhoto.getResourceId(idx, -1));
            movies.isMovie(true);
            listMovie.add(movies);
        }
        return listMovie;
    }

    @Override
    public void startActivity(int position) {
        Movie movie = getListMovie().get(position);
        Intent intent = new Intent(getActivity(), MovieDetail.class);
        intent.putExtra(MovieDetail.EXTRA_MOVIE, movie);
        intent.putExtra(MovieDetail.EXTRA_MOVIE, true);
        startActivity(intent);
    }
}
