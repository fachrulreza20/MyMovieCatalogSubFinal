package com.example.mymoviecatalogsubfinal.database;

import android.database.Cursor;
import android.net.Uri;

public class DatabaseContract {

    public static final String AUTHORITY = "com.example.mymoviecatalogsubfinal";
    private static final String SCHEME = "content";

    private DatabaseContract(){}

    public static String TABLE_FAVORITE = "tb_favorite";
    public static final class FavoritColumns{
        public static String IDFAV = "idfav";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String RELEASE_DATE = "releasedate";
        public static String PHOTO_PATH = "photopath";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();
    }
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}