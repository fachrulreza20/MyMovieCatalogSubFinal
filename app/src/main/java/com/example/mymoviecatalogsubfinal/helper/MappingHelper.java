package com.example.mymoviecatalogsubfinal.helper;

import android.database.Cursor;

import com.example.mymoviecatalogsubfinal.myclass.Movie;

import java.util.ArrayList;

import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.DESCRIPTION;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.IDFAV;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.PHOTO_PATH;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.RELEASE_DATE;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.TITLE;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<Movie> notesList = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(IDFAV));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DESCRIPTION));
            String date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RELEASE_DATE));
            String photo = notesCursor.getString(notesCursor.getColumnIndexOrThrow(PHOTO_PATH));
            notesList.add(new Movie(id, title, description, date));
        }
        return notesList;
    }

}
