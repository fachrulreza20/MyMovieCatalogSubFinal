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
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mymoviecatalogsubfinal.R;
import com.example.mymoviecatalogsubfinal.activity.MainActivity;
import com.example.mymoviecatalogsubfinal.adapter.ListMovieAdapter;
import com.example.mymoviecatalogsubfinal.callback.MyCallBack;
import com.example.mymoviecatalogsubfinal.myclass.Movie;
import com.example.mymoviecatalogsubfinal.viewmodel.MovieViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.CONTENT_URI;
import static com.example.mymoviecatalogsubfinal.helper.MappingHelper.mapCursorToArrayList;

public class MovieFragment extends Fragment implements MyCallBack {

    private RecyclerView rvCategory;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    ListMovieAdapter listMovieAdapter;
    private ProgressBar progressBar;
    private MovieViewModel mainViewModel;

    private static HandlerThread handlerThread;
    private FavMovieFragment.DataObserver myObserver;
    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_movie, container, false);

        mainViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mainViewModel.getMovies().observe(this, getMovieObserver);

        if(((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.appbartitle_movielist);
        }

        rvCategory = rootview.findViewById(R.id.rv_category_movie);
        rvCategory.setHasFixedSize(true);
        progressBar = rootview.findViewById(R.id.progressBar);

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new FavMovieFragment.DataObserver(handler, getActivity());
        getContext().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        showRecyclerList();

        mainViewModel.setMovie();

        if (savedInstanceState == null) {
            new LoadNoteAsync(getActivity(), this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                listMovieAdapter.setListMovie(list);
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

    private Observer<ArrayList<Movie>> getMovieObserver = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movieItems) {
            if (movieItems != null) {
                listMovieAdapter.setListMovie(movieItems);
                showLoading(false);
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading(true);
    }


    private void showRecyclerList(){
        ArrayList<Movie> movies;
        movies = new ArrayList<>();
        rvCategory.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        listMovieAdapter = new ListMovieAdapter();
        listMovieAdapter.notifyDataSetChanged();
        listMovieAdapter.setContext(this.getActivity());
        listMovieAdapter.setListMovie(movies); // pakai variabel movies di baris 19 pas deklarasi ArrayList<Movie>
        rvCategory.setAdapter(listMovieAdapter);
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
    public void postExecute(ArrayList<Movie> movies) {

    }

    public void postExecute(Cursor cursor) {
        progressBar.setVisibility(View.INVISIBLE);

        ArrayList<Movie> listNotes = mapCursorToArrayList(cursor);
        if (listNotes.size() > 0) {
            listMovieAdapter.setListMovie(listNotes);
        } else {
            listMovieAdapter.setListMovie(new ArrayList<Movie>());
            showSnackbarMessage("Tidak ada data saat ini");
        }

    }



    private static class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<MyCallBack> weakCallback;

        private LoadNoteAsync(Context context, MyCallBack callback) {
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
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
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
            new LoadNoteAsync(context, (MyCallBack) context).execute();
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvCategory, message, Snackbar.LENGTH_SHORT).show();
    }



}

