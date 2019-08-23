package com.example.mymoviecatalogsubfinal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mymoviecatalogsubfinal.myclass.Movie;

import java.util.ArrayList;

import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.DESCRIPTION;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.IDFAV;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.PHOTO_PATH;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.RELEASE_DATE;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.FavoritColumns.TITLE;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContract.TABLE_FAVORITE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_FAVORITE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                "idfav" + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(IDFAV)));
                movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setReleasedate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO_PATH)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFavMovie(Movie movie) {
        long execute = 0;

        ContentValues args = new ContentValues();
        args.put(IDFAV, movie.getId());
        args.put(TITLE, movie.getName());
        args.put(DESCRIPTION, movie.getDescription());
        args.put(RELEASE_DATE, movie.getReleasedate());
        args.put(PHOTO_PATH, movie.getPhoto());
        execute = database.insert(DATABASE_TABLE, null, args);

        return execute;

    }




    public int deleteFavMovie(int id) {
        return database.delete(TABLE_FAVORITE, "idfav" + " = '" + id + "'", null);
    }

    public int checkIsFavorited(int id){

        Cursor cursor = null;
        String sql ="SELECT idfav FROM "+ TABLE_FAVORITE +" WHERE idfav =" + id;
        cursor= database.rawQuery(sql,null);

        if(cursor.getCount()>0){
            //idfavFound
            id = 1;
        }else{
            //idfav Not Found
            id = 0;
        }
        cursor.close();

        return id;
    }


    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , "idfav" + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , "idfav" + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, "idfav" + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, "idfav" + " = ?", new String[]{id});
    }

}