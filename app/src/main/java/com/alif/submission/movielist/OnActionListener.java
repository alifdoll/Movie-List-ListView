package com.alif.submission.movielist;

public interface OnActionListener {
    void startActivity(int position);

    void onDeleteFromFavorite(int position);
}
