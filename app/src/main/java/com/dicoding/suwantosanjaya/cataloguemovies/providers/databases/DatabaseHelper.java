package com.dicoding.suwantosanjaya.cataloguemovies.providers.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * Created by Suwanto Sanjaya on 17/12/2018.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final SQLiteDatabase.CursorFactory CURSOR_FACTORY = null;
    private static final int DATABASE_VERSION = 1;
    public static String CREATE_TABLE_FAVOURITE = "create table " + DatabaseContract.TABLE_FAVOURITE + " (" +
            DatabaseContract.FavouriteColumns.ID + " integer primary key, " +
            DatabaseContract.FavouriteColumns.TITLE + " text not null, " +
            DatabaseContract.FavouriteColumns.POSTER + " text not null, " +
            DatabaseContract.FavouriteColumns.OVERVIEW + " text not null, " +
            DatabaseContract.FavouriteColumns.RELEASE_DATE + " text not null, " +
            DatabaseContract.FavouriteColumns.VOTE_AVERAGE + " real);";
    private static String DATABASE_NAME = "dbmovie";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, CURSOR_FACTORY, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVOURITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVOURITE);
        onCreate(db);
    }
}
