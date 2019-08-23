package com.example.mymoviecatalogsubfinal.database;

import android.database.Cursor;
import android.net.Uri;

public class DatabaseContractTvs {

    public static final String AUTHORITY = "com.example.mymoviecatalogsubfinaltvs";
    private static final String SCHEME = "content";

    private DatabaseContractTvs(){}

    public static String TABLE_FAVORITE_TVS = "tb_favoritetvs";
    public static final class FavoritColumns{
        public static String IDFAV = "idfav";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String RELEASE_DATE = "releasedate";
        public static String PHOTO_PATH = "photopath";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE_TVS)
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