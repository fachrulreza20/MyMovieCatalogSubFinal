package com.example.mymoviecatalogsubfinal.myclass;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.getColumnInt;
import static com.example.mymoviecatalogsubfinal.database.DatabaseContractTvs.getColumnString;

public class TVShow implements Parcelable {


    private int id;
    private String name_tvshow;
    private String description_tvshow;
    private String photo_tvshow;
    private String popularity_tvshow;
    private String releasedate_tvshow;
    final String urlPhoto = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";

    public String getName_tvshow() {
        return name_tvshow;
    }

    public void setName_tvshow(String name_tvshow) {
        this.name_tvshow = name_tvshow;
    }

    public String getDescription_tvshow() {
        return description_tvshow;
    }

    public void setDescription_tvshow(String description_tvshow) {
        this.description_tvshow = description_tvshow;
    }


    public int getId() {
        return id;
    }
    public String getPhoto_tvshow() {
        return photo_tvshow;
    }

    public void setPhoto_tvshow(String photo_tvshow) {
        this.photo_tvshow = photo_tvshow;
    }

    public String getPopularity_tvshow() {
        return popularity_tvshow;
    }

    public void setPopularity_tvshow(String popularity_tvshow) {
        this.popularity_tvshow = popularity_tvshow;
    }

    public String getReleasedate_tvshow() {
        return releasedate_tvshow;
    }

    public void setReleasedate_tvshow(String releasedate_tvshow) {
        this.releasedate_tvshow = releasedate_tvshow;
    }

    public void setId(int id) {
        this.id = id;
    }


    public TVShow(Parcel in) {
        this.id = in.readInt();
        this.name_tvshow = in.readString();
        this.description_tvshow = in.readString();
        this.photo_tvshow = in.readString();
        this.popularity_tvshow = in.readString();
        this.releasedate_tvshow = in.readString();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel source) {
            return new TVShow(source);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name_tvshow);
        dest.writeString(this.description_tvshow);
        dest.writeString(this.photo_tvshow);
        dest.writeString(this.popularity_tvshow);
        dest.writeString(this.releasedate_tvshow);
    }


    public TVShow() {
    }


    public TVShow(JSONObject object) {
        try {

            int id = object.getInt("id");
            String name = object.getString("original_name");
            String description = object.getString("overview");
            String popularity = object.getString("popularity");
            String releasedate = object.getString("first_air_date");
            String photo = object.getString("poster_path");

            this.id = id;
            this.name_tvshow = name;
            this.description_tvshow = description;
            this.popularity_tvshow = popularity;
            this.releasedate_tvshow = releasedate;
            this.photo_tvshow = urlPhoto + photo;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public TVShow(int id, String title, String description, String date) {
        this.id = id;
        this.name_tvshow = title;
        this.description_tvshow = description;
        this.releasedate_tvshow= date;
    }
    public TVShow(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.name_tvshow = getColumnString(cursor, DatabaseContractTvs.FavoritColumns.TITLE);
        this.description_tvshow = getColumnString(cursor, DatabaseContractTvs.FavoritColumns.DESCRIPTION);
        this.releasedate_tvshow = getColumnString(cursor, DatabaseContractTvs.FavoritColumns.RELEASE_DATE);
    }
}
