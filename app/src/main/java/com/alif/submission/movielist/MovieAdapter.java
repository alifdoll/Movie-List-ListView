package com.alif.submission.movielist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> movies = new ArrayList<>();

    MovieAdapter(Context context){
        this.context = context;
    }

    void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if(itemView == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup,false);
        }

        ViewHolder viewHolder = new ViewHolder(itemView);

        Movie movie = (Movie)getItem(i);
        viewHolder.bind(movie);
        return itemView;
    }

    private class ViewHolder {
        private TextView txtName;
        private TextView txtOverview;
        private ImageView imgPhoto;

        ViewHolder(View view){
            txtName = view.findViewById(R.id.movie_name);
            txtOverview = view.findViewById(R.id.movie_overview);
            imgPhoto = view.findViewById(R.id.movie_photo);
        }

        void bind(Movie movie){
            txtName.setText(movie.getName());
            txtOverview.setText(movie.getOverview());
            imgPhoto.setImageResource(movie.getPhoto());
        }
    }
}
