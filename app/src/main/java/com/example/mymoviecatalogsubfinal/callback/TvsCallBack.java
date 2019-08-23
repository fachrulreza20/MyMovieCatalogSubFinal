package com.example.mymoviecatalogsubfinal.callback;

import com.example.mymoviecatalogsubfinal.myclass.TVShow;

import java.util.ArrayList;

public interface TvsCallBack {

    void preExecute();
    void postExecute(ArrayList<TVShow> tvshows);

}
