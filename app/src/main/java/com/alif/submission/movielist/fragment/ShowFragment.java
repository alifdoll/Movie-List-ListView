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
import androidx.recyclerview.widget.RecyclerView;

import com.alif.submission.movielist.MovieDetail;
import com.alif.submission.movielist.OnActionListener;
import com.alif.submission.movielist.R;
import com.alif.submission.movielist.adapter.MovieAdapter;
import com.alif.submission.movielist.data.MovieItem;
import com.alif.submission.movielist.viewmodel.MovieMainViewModel;

import java.util.ArrayList;


public class ShowFragment extends Fragment implements OnActionListener {

    private MovieAdapter adapter;
    private MovieMainViewModel mainViewModel;

    public ShowFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.rv_show);

        adapter = new MovieAdapter();
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieMainViewModel.class);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));

        mainViewModel.setShow();
        mainViewModel.getMovie().observe(getViewLifecycleOwner(), new Observer<ArrayList<MovieItem>>() {
            @Override
            public void onChanged(ArrayList<MovieItem> movieItems) {
                if (movieItems != null) {
                    adapter.setData(movieItems, ShowFragment.this);
                }
            }
        });
    }


    @Override
    public void startActivity(int position) {
        MovieItem movie = mainViewModel.getListMovie().get(position);
        Intent intent = new Intent(getActivity(), MovieDetail.class);
        intent.putExtra(MovieDetail.EXTRA_MOVIE, movie);
//        intent.putExtra("IS SHOW", true);
        startActivity(intent);
    }

    @Override
    public void onDeleteFromFavorite(int position) {

    }
}
