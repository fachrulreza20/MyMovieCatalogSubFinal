package com.example.mymoviecatalogsubfinal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperTvs extends SQLiteOpenHelper {

    public DatabaseHelperTvs(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String DATABASE_NAME = "dbFavoriteTvshow";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContractTvs.TABLE_FAVORITE_TVS,
            DatabaseContractTvs.FavoritColumns.IDFAV,
            DatabaseContractTvs.FavoritColumns.TITLE,
            DatabaseContractTvs.FavoritColumns.DESCRIPTION,
            DatabaseContractTvs.FavoritColumns.PHOTO_PATH,
            DatabaseContractTvs.FavoritColumns.RELEASE_DATE
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContractTvs.TABLE_FAVORITE_TVS);
        onCreate(db);
    }
}
