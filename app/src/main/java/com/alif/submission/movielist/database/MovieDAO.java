package com.alif.submission.movielist.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alif.submission.movielist.data.MovieItem;

import java.util.List;

@Dao
public interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieItem movieItems);

    @Query("SELECT * FROM favorite")
    Cursor selectAll();

    @Query("SELECT * FROM favorite")
    List<MovieItem> getAllFavMovie();

    @Query("SELECT * FROM favorite WHERE type = :type")
    List<MovieItem> getMovieByType(String type);

    @Query("SELECT id FROM favorite WHERE id = :id")
    int getMovieId(int id);

    @Query("SELECT * FROM favorite WHERE id = :id")
    MovieItem findMovieById(int id);

    @Delete
    void deleteMovie(MovieItem movieItem);

    @Query("DELETE FROM favorite WHERE id = :id")
    void deleteById(int id);


}
