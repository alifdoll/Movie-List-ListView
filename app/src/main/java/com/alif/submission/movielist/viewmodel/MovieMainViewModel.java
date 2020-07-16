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
    private static final String POSTER_PATH = BuildConfig.POSTER_PATH;
    private final String POSTER_SIZE = "w780";
    private MutableLiveData<ArrayList<MovieItem>> listMovie = new MutableLiveData<>();
    private ArrayList<MovieItem> listItems = new ArrayList<>();

    public void setMovie() {

        final String URL = "https://api.themoviedb.org/3/discover/movie?api_key="
                + API_KEY
                + "&language=en-US";

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

    public void search(String query, final String type) {
        final String URL_INFO = API_KEY + "&language=en-US&query=" + query;
        String URL = "";
        final String name;
        if (type.equalsIgnoreCase("movie")) {
            URL = "https://api.themoviedb.org/3/search/movie?api_key=" + URL_INFO;
            name = "title";
        } else {
            URL = "https://api.themoviedb.org/3/search/tv?api_key=" + URL_INFO;
            name = "original_name";
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(URL, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    ArrayList<MovieItem> movieItems = new ArrayList<>();
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItem item = new MovieItem();
                        item.setId(movie.getInt("id"));
                        item.setTitle(movie.getString(name));
                        item.setOverview(movie.getString("overview"));
                        item.setFavorite(false);
                        item.setPoster_path(POSTER_PATH + POSTER_SIZE + movie.getString("poster_path"));
                        item.setType(type);
                        movieItems.add(item);
                    }
                    listMovie.postValue(movieItems);
                } catch (Exception e) {
                    Log.e("ViewModel Movie Error", e.getMessage() == null ? "" : e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("ViewModel Movie Error", error.getMessage() == null ? "" : error.getMessage());
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
