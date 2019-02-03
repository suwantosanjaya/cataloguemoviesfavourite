package com.dicoding.suwantosanjaya.cataloguemovies.providers.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.dicoding.suwantosanjaya.cataloguemovies.providers.databases.DatabaseContract;

/**
 * Created by Suwanto Sanjaya on 17/12/2018.
 */
public class FavouriteModel implements Parcelable {
    public static final Creator<FavouriteModel> CREATOR = new Creator<FavouriteModel>() {
        @Override
        public FavouriteModel createFromParcel(Parcel in) {
            return new FavouriteModel(in);
        }

        @Override
        public FavouriteModel[] newArray(int size) {
            return new FavouriteModel[size];
        }
    };
    private int id;
    private String title;
    private String poster;
    private String overview;
    private String releaseDate;
    private float voteAverage;

    public FavouriteModel() {
    }

    public FavouriteModel(int id, String title, String poster, String overview, String releaseDate, float voteAverage) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public FavouriteModel(Cursor cursor) {
        this.id = DatabaseContract.getColumnInt(cursor, DatabaseContract.FavouriteColumns.ID);
        this.title = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.TITLE);
        this.poster = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.POSTER);
        this.overview = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.OVERVIEW);
        this.releaseDate = DatabaseContract.getColumnString(cursor, DatabaseContract.FavouriteColumns.RELEASE_DATE);
        this.voteAverage = DatabaseContract.getColumnFloat(cursor, DatabaseContract.FavouriteColumns.VOTE_AVERAGE);
    }

    protected FavouriteModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.poster = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readFloat();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.poster);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeFloat(this.voteAverage);
    }


}
