package com.alif.submission.movielist.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alif.submission.movielist.OnActionListener;
import com.alif.submission.movielist.R;
import com.alif.submission.movielist.data.MovieItem;
import com.alif.submission.movielist.database.MovieDAO;
import com.alif.submission.movielist.database.MovieDatabase;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ListViewHolder> {

    private ArrayList<MovieItem> listOfMovie = new ArrayList<>();
    private OnActionListener listener;
    private Context mContext;
    private MovieDAO database;
    private Handler handler = new Handler();
//    private ItemMovieBinding binding;

    public void setData(Context context, List<MovieItem> movies, OnActionListener onActionListener) {
        listOfMovie.clear();
        listOfMovie.addAll(movies);
        listener = onActionListener;
        mContext = context;
        database = MovieDatabase.getInstance(context).getMovieDao();
        notifyDataSetChanged();
    }

    public void deleteFavoriteItem(int position) {
        listOfMovie.remove(position);
        notifyDataSetChanged();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listOfMovie.size());
    }



    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        binding = ItemMovieBinding.inflate(inflater);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, final int position) {
        final MovieItem movie = listOfMovie.get(position);
        new Thread(new Runnable() {
            @Override
            public void run() {

                int a = MovieDatabase.getInstance(mContext)
                        .getMovieDao()
                        .getMovieId(movie.getId());
                Log.d("MDB", "Thread Name : " + Thread.currentThread().getName());
                Log.d("MDB", "Favorite Movie : " + movie.getTitle() + " " + a);
                movie.setFavorite(movie.getId() == a);
                Log.d("MDB", "MOVIE IS FAV : " + movie.getTitle() + " " + movie.isFavorite());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.BindData(movie, holder);
                        holder.btnAddFav.setText(movie.isFavorite() ? R.string.remove_from_fav : R.string.add_to_fav);
                        holder.btnAddFav.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(movie.isFavorite() ? "#e83847" : "#3857E8")));
                    }
                });
            }
        }).start();

        Log.d("MDB", "BUTTON MOVIE IS FAV : " + movie.getTitle() + " " + movie.isFavorite());

        holder.btnAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie.isFavorite()) {
                    Toast.makeText(holder.itemView.getContext(), "Removed To Favorite " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MovieItem movieItem = database.findMovieById(movie.getId());
                            database.deleteMovie(movieItem);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onDeleteFromFavorite(position);
                                }
                            });
                        }
                    }).start();
//                    listener.onDeleteFromFavorite(position);
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Added From Favorite", Toast.LENGTH_SHORT).show();
                    InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
                    insertAsyncTask.execute(movie);
                }
                movie.setFavorite(!movie.isFavorite());
                holder.btnAddFav.setText(movie.isFavorite() ? R.string.remove_from_fav : R.string.add_to_fav);
                holder.btnAddFav.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(movie.isFavorite() ? "#e83847" : "#3857E8")));
            }
        });


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

    static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView movieName,movieOverview;
        ImageView moviePhoto;
        Button btnAddFav;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.movie_title);
            movieOverview = itemView.findViewById(R.id.movie_overview);
            moviePhoto = itemView.findViewById(R.id.movie_photo);
            btnAddFav = itemView.findViewById(R.id.movie_button_add_favorite);
        }

        void BindData(MovieItem movie, ListViewHolder holder){
            Glide.with(holder.itemView.getContext())
                    .load(movie.getPoster_path())
                    .apply(new RequestOptions())
                    .into(holder.moviePhoto);

            holder.movieName.setText(movie.getTitle());
            holder.movieOverview.setText(movie.getOverview());
        }
    }

    class InsertAsyncTask extends AsyncTask<MovieItem, Void, Void> {

        @Override
        protected Void doInBackground(MovieItem... movieItems) {

            MovieDatabase.getInstance(mContext)
                    .getMovieDao()
                    .insert(movieItems[0]);

            return null;
        }
    }

    class DeleteAsyncTask extends AsyncTask<MovieItem, Void, Void> {

        @Override
        protected Void doInBackground(MovieItem... movieItems) {

            MovieDatabase.getInstance(mContext)
                    .getMovieDao()
                    .deleteById(movieItems[0].getId());
            return null;
        }
    }

    class GetMovieById extends AsyncTask<MovieItem, Void, Void> {

        MovieItem check = null;

        @Override
        protected Void doInBackground(MovieItem... movieItems) {

            check = MovieDatabase.getInstance(mContext)
                    .getMovieDao()
                    .findMovieById(movieItems[0].getId());
            return null;
        }

//        MovieItem getMovie(MovieItem movieItem){
//            GetMovieById getMovieById = new GetMovieById();
//            getMovieById.execute(movieItem);
//        }
    }


}
