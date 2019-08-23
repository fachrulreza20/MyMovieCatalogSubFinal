package com.example.mymoviecatalogsubfinal.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mymoviecatalogsubfinal.callback.MyCallBack;
import com.example.mymoviecatalogsubfinal.callback.TvsCallBack;
import com.example.mymoviecatalogsubfinal.viewmodel.TVShowViewModel;
import com.example.mymoviecatalogsubfinal.adapter.ListTVShowAdapter;
import com.example.mymoviecatalogsubfinal.myclass.TVShow;
import com.example.mymoviecatalogsubfinal.activity.MainActivity;
import com.example.mymoviecatalogsubfinal.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.CONTENT_URI;
import static com.example.mymoviecatalogsubfinal.helper.MappingHelperTvs.mapCursorToArrayListTvs;


public class TVShowFragment extends Fragment implements TvsCallBack {

    private ArrayList<TVShow> tvshows;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    private RecyclerView rvCategory;

    public ListTVShowAdapter listTVShowAdapter;
    private ProgressBar progressBar;
    private TVShowViewModel tvshowViewModel;

    private static HandlerThread handlerThread;
    private FavTVFragment.DataObserver myObserver;

    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_tvshow, container, false);

        tvshowViewModel = ViewModelProviders.of(this).get(TVShowViewModel.class);
        tvshowViewModel.getTVShow().observe(this, getTVShowObserver);


        if(((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.appbartitle_tvshowlist);
        }

        rvCategory = rootview.findViewById(R.id.rv_category_tvshow);
        rvCategory.setHasFixedSize(true);
        progressBar = rootview.findViewById(R.id.progressBarTVShow);

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new FavTVFragment.DataObserver(handler, getActivity());
        getContext().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);



        showRecyclerList();

        tvshowViewModel.setTVShow();

        if (savedInstanceState == null) {
            new LoadNoteAsync(getActivity(), this).execute();
        } else {
            ArrayList<TVShow> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                listTVShowAdapter.setListTVShow(list);
            }
        }

        return rootview;
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private Observer<ArrayList<TVShow>> getTVShowObserver = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(ArrayList<TVShow> tvshowItems) {
            if (tvshowItems != null) {
                listTVShowAdapter.setListTVShow(tvshowItems);
                showLoading(false);
            }
        }
    };



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading(true);
    }

    public void showRecyclerList(){
        tvshows = new ArrayList<>();
        rvCategory.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        listTVShowAdapter = new ListTVShowAdapter();
        listTVShowAdapter.notifyDataSetChanged();
        listTVShowAdapter.setContext(this.getActivity());
        listTVShowAdapter.setListTVShow(tvshows);
        rvCategory.setAdapter(listTVShowAdapter);
    }


    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<TVShow> tvshows) {

    }

    public void postExecute(Cursor notes) {
        progressBar.setVisibility(View.INVISIBLE);

        ArrayList<TVShow> listNotes = mapCursorToArrayListTvs(notes);
        if (listNotes.size() > 0) {
            listTVShowAdapter.setListTVShow(listNotes);
        } else {
            listTVShowAdapter.setListTVShow(new ArrayList<TVShow>());
        }
    }

    private static class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<TvsCallBack> weakCallback;

        private LoadNoteAsync(Context context, TvsCallBack callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
//            weakCallback.get().postExecute(notes);
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
            new LoadNoteAsync(context, (TvsCallBack) context).execute();
        }
    }
}
