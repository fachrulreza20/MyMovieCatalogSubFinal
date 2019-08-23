package com.example.mymoviecatalogsubfinal.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mymoviecatalogsubfinal.database.TvsHelper;
import com.example.mymoviecatalogsubfinal.fragment.FavTVFragment;

import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.AUTHORITY;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.CONTENT_URI;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.TABLE_FAVORITE_TVS;

public class TVSProvider extends ContentProvider {

    private static final int TVS = 1;
    private static final int TVS_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private TvsHelper tvsHelper;


    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_TVS, TVS);
        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY, TABLE_FAVORITE_TVS + "/#", TVS_ID);
    }

    @Override
    public boolean onCreate() {
        tvsHelper = TvsHelper.getInstance(getContext());
        return true;    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        tvsHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case TVS:
                cursor = tvsHelper.queryProvider();
                break;
            case TVS_ID:
                cursor = tvsHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        tvsHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case TVS:
                added = tvsHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavTVFragment.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        tvsHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case TVS_ID:
                deleted = tvsHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavTVFragment.DataObserver(new Handler(), getContext()));
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        tvsHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case TVS_ID:
                updated = tvsHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavTVFragment.DataObserver(new Handler(), getContext()));
        return updated;    }
}
