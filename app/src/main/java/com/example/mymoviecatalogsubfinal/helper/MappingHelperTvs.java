package com.example.mymoviecatalogsubfinal.helper;

import android.database.Cursor;

import com.example.mymoviecatalogsubfinal.myclass.Movie;
import com.example.mymoviecatalogsubfinal.myclass.TVShow;

import java.util.ArrayList;

import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.DESCRIPTION;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.IDFAV;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.PHOTO_PATH;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.RELEASE_DATE;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.TITLE;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.TABLE_FAVORITE;

public class MappingHelperTvs {

    public static ArrayList<TVShow> mapCursorToArrayListTvs(Cursor notesCursor) {
        ArrayList<TVShow> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(IDFAV));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DESCRIPTION));
            String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RELEASE_DATE));
            String photo = notesCursor.getString(notesCursor.getColumnIndexOrThrow(PHOTO_PATH));
            notesList.add(new TVShow(id, title, description, date));
        }

        return notesList;
    }
}
