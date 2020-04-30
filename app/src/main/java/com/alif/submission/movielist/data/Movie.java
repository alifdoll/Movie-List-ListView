package com.alif.submission.movielist.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int photo;
    private String name;
    private String overview;
    private boolean isMovie;



    public Movie() {

    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void isMovie(boolean isMovie) {
        this.isMovie = isMovie;
    }

    public boolean getIsMovie() {
        return isMovie;
    }

    protected Movie(Parcel in) {
        photo = in.readInt();
        name = in.readString();
        overview = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(photo);
        dest.writeString(name);
        dest.writeString(overview);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
