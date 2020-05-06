package com.alif.submission.movielist.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alif.submission.movielist.BuildConfig;
import com.alif.submission.movielist.data.MovieItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieMainViewModel extends ViewModel {

    private static final String API_KEY = BuildConfig.API_KEY;
    private MutableLiveData<ArrayList<MovieItem>> listMovie = new MutableLiveData<>();
    private ArrayList<MovieItem> listItems = new ArrayList<>();

    public void setMovie() {

        final String URL = "https://api.themoviedb.org/3/discover/movie?api_key="
                + API_KEY
                + "&language=en-US";

        final String POSTER_PATH = "https://image.tmdb.org/t/p/", POSTER_SIZE = "original";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        MovieItem movie = new MovieItem();

                        movie.setId(item.getInt("id"));
                        movie.setTitle(item.getString("title"));
                        movie.setOverview(item.getString("overview"));
                        movie.setFavorite(false);
                        movie.setPoster_path(POSTER_PATH + POSTER_SIZE + item.getString("poster_path"));
                        movie.setType("movie");
                        listItems.add(movie);
                    }
                    listMovie.postValue(listItems);

                } catch (Exception e) {
                    Log.d("MDB", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("MDB", error.getMessage());
            }
        });
    }

    public void setShow() {
        final String URL = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        final String POSTER_PATH = "https://image.tmdb.org/t/p/", POSTER_SIZE = "original";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {

                    Log.d("MDB","FETCH SUCCESS");
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {

                        JSONObject item = list.getJSONObject(i);
                        MovieItem show = new MovieItem();

//                        show.setId(item.getInt("id"));
                        show.setId(item.getInt("id"));
                        show.setTitle(item.getString("name"));
                        show.setOverview(item.getString("overview"));
                        show.setFavorite(false);
                        show.setPoster_path(POSTER_PATH + POSTER_SIZE + item.getString("poster_path"));
                        show.setType("show");
                        listItems.add(show);
                    }
                    listMovie.postValue(listItems);
                } catch (Exception e) {
                    Log.e("ViewModel Show Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("ViewModel Show Error", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MovieItem>> getMovie() {
        return listMovie;
    }

    public ArrayList<MovieItem> getListMovie() {
        return listItems;
    }

}
