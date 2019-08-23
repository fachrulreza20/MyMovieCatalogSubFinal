package com.example.mymoviecatalogsubfinal.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mymoviecatalogsubfinal.database.MovieHelper;
import com.example.mymoviecatalogsubfinal.fragment.FavMovieFragment;

import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.AUTHORITY;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.CONTENT_URI;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.TABLE_FAVORITE;

public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper moviehelper;

    @Override
    public boolean onCreate() {
        moviehelper = MovieHelper.getInstance(getContext());
        return true;
    }
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        moviehelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = moviehelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = moviehelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        moviehelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                added = moviehelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavMovieFragment.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI + "/" + added);
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        moviehelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = moviehelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavMovieFragment.DataObserver(new Handler(), getContext()));
        return deleted;
    }











    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        moviehelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated = moviehelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavMovieFragment.DataObserver(new Handler(), getContext()));
        return updated;
    }

    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE, MOVIE);
        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE + "/#", MOVIE_ID);
    }



}