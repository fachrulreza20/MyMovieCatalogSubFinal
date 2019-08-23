package com.example.mymoviecatalogsubfinal.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mymoviecatalogsubfinal.R;
import com.example.mymoviecatalogsubfinal.adapter.ListTVShowAdapter;
import com.example.mymoviecatalogsubfinal.callback.TvsCallBack;
import com.example.mymoviecatalogsubfinal.database.TvsHelper;
import com.example.mymoviecatalogsubfinal.myclass.TVShow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTVFragment extends Fragment implements View.OnClickListener, TvsCallBack {

    private RecyclerView rvFavoriteTvshow;
    private ProgressBar progressBar;
    ListTVShowAdapter listTvshowAdapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private TvsHelper tvsHelper;

    public FavTVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_fav_tv, container, false);

        rvFavoriteTvshow = rootview.findViewById(R.id.rv_category_tvshow_fav);
        progressBar = rootview.findViewById(R.id.progressBarFavm);

        rvFavoriteTvshow.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvFavoriteTvshow.setHasFixedSize(true);

        tvsHelper = TvsHelper.getInstance(getActivity().getApplicationContext());
        tvsHelper.open();

        listTvshowAdapter = new ListTVShowAdapter();
        listTvshowAdapter.setContext(this.getActivity());
        rvFavoriteTvshow.setAdapter(listTvshowAdapter);

        if (savedInstanceState == null) {
            new LoadTvshowAsync(tvsHelper, this).execute();
        } else {
            ArrayList<TVShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                listTvshowAdapter.setListTVShow(list);
            }
        }

        return rootview;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void postExecute(ArrayList<TVShow> tvshows) {
        progressBar.setVisibility(View.INVISIBLE);
        listTvshowAdapter.setListTVShow(tvshows);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, listTvshowAdapter.getListTvshow());
    }

    private static class LoadTvshowAsync extends AsyncTask<Void, Void, ArrayList<TVShow>> {

        private final WeakReference<TvsHelper> weakNoteHelper;
        private final WeakReference<TvsCallBack> weakCallback;

        private LoadTvshowAsync(TvsHelper tvsHelper, TvsCallBack callback) {
            weakNoteHelper = new WeakReference<>(tvsHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }
        @Override
        protected ArrayList<TVShow> doInBackground(Void... voids) {
            return weakNoteHelper.get().getAllTvshows();
        }
        @Override
        protected void onPostExecute(ArrayList<TVShow> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExecute(notes);
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadTvshowAsync(TvsHelper.getInstance(context), (TvsCallBack) context).execute();

        }
    }




}













