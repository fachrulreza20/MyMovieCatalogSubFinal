package com.example.mymoviecatalogsubfinal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mymoviecatalogsubfinal.myclass.TVShow;

import java.util.ArrayList;

import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.DESCRIPTION;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.IDFAV;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.PHOTO_PATH;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.RELEASE_DATE;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.FavoritColumns.TITLE;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.TABLE_FAVORITE_TVS;

public class TvsHelper {
    private static final String DATABASE_TABLE = TABLE_FAVORITE_TVS;
    private static DatabaseHelperTvs DatabaseHelperTvs;
    private static TvsHelper INSTANCE;

    private static SQLiteDatabase database;

    private TvsHelper(Context context) {
        DatabaseHelperTvs = new DatabaseHelperTvs(context);
    }

    public static TvsHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvsHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = DatabaseHelperTvs.getWritableDatabase();
    }

    public void close() {
        DatabaseHelperTvs.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<TVShow> getAllTvshows() {
        ArrayList<TVShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                "idfav" + " ASC",
                null);
        cursor.moveToFirst();
        TVShow tvshow;
        if (cursor.getCount() > 0) {
            do {
                tvshow = new TVShow();
                tvshow.setName_tvshow(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvshow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(IDFAV)));
                tvshow.setDescription_tvshow(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                tvshow.setReleasedate_tvshow(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                tvshow.setPhoto_tvshow(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO_PATH)));

                arrayList.add(tvshow);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFavTvshow(TVShow tvshow) {
        long execute = 0;

        ContentValues args = new ContentValues();
        args.put(IDFAV, tvshow.getId());
        args.put(TITLE, tvshow.getName_tvshow());
        args.put(DESCRIPTION, tvshow.getDescription_tvshow());
        args.put(RELEASE_DATE, tvshow.getReleasedate_tvshow());
        args.put(PHOTO_PATH, tvshow.getPhoto_tvshow());
        execute = database.insert(DATABASE_TABLE, null, args);

        return execute;

    }




    public int deleteFavTvshow(int id) {
        return database.delete(TABLE_FAVORITE_TVS, "idfav" + " = '" + id + "'", null);
    }

    public int checkIsFavorited(int id){

        Cursor cursor = null;
        String sql ="SELECT idfav FROM "+ TABLE_FAVORITE_TVS +" WHERE idfav =" + id;
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