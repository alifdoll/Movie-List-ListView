package com.alif.submission.movielist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alif.submission.movielist.OnActionListener;
import com.alif.submission.movielist.R;
import com.alif.submission.movielist.data.MovieItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder> {

    private ArrayList<MovieItem> listOfMovie = new ArrayList<>();
    private OnActionListener listener;
    private Context context;

    public void setData(ArrayList<MovieItem> movies, OnActionListener onActionListener) {
        listOfMovie.clear();
        listOfMovie.addAll(movies);
        listener = onActionListener;
    }



    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        final MovieItem movie = listOfMovie.get(position);

        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster_path())
                .apply(new RequestOptions())
                .into(holder.moviePhoto);

        holder.movieName.setText(movie.getTitle());
        holder.movieOverview.setText(movie.getOverview());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return listOfMovie.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView movieName,movieOverview;
        ImageView moviePhoto;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_title);
            movieOverview = itemView.findViewById(R.id.movie_overview);
            moviePhoto = itemView.findViewById(R.id.movie_photo);

        }
    }


}
