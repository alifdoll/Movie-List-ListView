package com.alif.submission.movielist.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private int photo;
    private int directorPhoto;
    private String name;
    private String releaseDate;
    private String directorName;
    private String overview;



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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getDirectorPhoto() {
        return directorPhoto;
    }

    public void setDirectorPhoto(int directorPhoto) {
        this.directorPhoto = directorPhoto;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    protected Movie(Parcel in) {
        photo = in.readInt();
        directorPhoto = in.readInt();
        name = in.readString();
        directorName = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(photo);
        dest.writeInt(directorPhoto);
        dest.writeString(name);
        dest.writeString(directorName);
        dest.writeString(releaseDate);
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
