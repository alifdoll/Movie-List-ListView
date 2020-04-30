package com.alif.submission.movielist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alif.submission.movielist.R;
import com.alif.submission.movielist.data.Movie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder> {

    private ArrayList<Movie> listOfMovie;
    private Movie movie;
    private OnActionListener listener;

    public MovieAdapter(ArrayList<Movie> movies, OnActionListener listener){
        listOfMovie = movies;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {
        final  Movie movie = listOfMovie.get(position);

        Glide.with(holder.itemView.getContext())
                .load(movie.getPhoto())
                .apply(new RequestOptions())
                .into(holder.moviePhoto);

        holder.movieName.setText(movie.getName());
        holder.movieOverview.setText(movie.getOverview());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.startActivity(position);
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

    public interface OnActionListener{
        void startActivity(int position);
    }
}
