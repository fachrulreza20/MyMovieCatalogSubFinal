package com.example.mymoviecatalogsubfinal.callback;

import android.database.Cursor;

import com.example.mymoviecatalogsubfinal.myclass.Movie;

import java.util.ArrayList;

public interface MyCallBack {

    void preExecute();
    void postExecute(ArrayList<Movie> movies);
    void postExecute(Cursor movies);

}
