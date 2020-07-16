package com.alif.submission.movielist.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.alif.submission.movielist.databinding.FragmentSearchBinding;
import com.alif.submission.movielist.viewmodel.MovieMainViewModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements OnActionListener {

    public static String QUERY = "QUERY";
    public static String TYPE = "TYPE";
    private FragmentSearchBinding binding;
    private MovieAdapter adapter;

    public SearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSearchBinding.bind(view);
        binding.idProgress.setVisibility(View.VISIBLE);

        adapter = new MovieAdapter();

        binding.rvSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvSearch.addItemDecoration(new DividerItemDecoration(binding.rvSearch.getContext(), DividerItemDecoration.VERTICAL));

        String query = "";
        String type = "movie";
        if (getArguments() != null) {
            query = getArguments().getString(QUERY);
            type = getArguments().getString(TYPE);
        }
        binding.rvSearch.setAdapter(adapter);
        MovieMainViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieMainViewModel.class);
        assert type != null;
        viewModel.search(query, type);
        viewModel.getMovie().observe(getViewLifecycleOwner(), new Observer<ArrayList<MovieItem>>() {
            @Override
            public void onChanged(ArrayList<MovieItem> movieItems) {
                binding.idProgress.setVisibility(View.INVISIBLE);
                if (movieItems == null || movieItems.size() == 0) {
                    Toast.makeText(getContext(), "No Result for Query", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.setData(getContext(), movieItems, SearchFragment.this);
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public void startActivity(int position) {

    }

    @Override
    public void onDeleteFromFavorite(int position) {

    }
}