package com.dicoding.suwantosanjaya.cataloguemovies.providers.databases;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Suwanto Sanjaya on 17/12/2018.
 */
public class DatabaseContract {
    public static final String AUTHORITY = "com.dicoding.suwantosanjaya.cataloguemovies";
    public static String TABLE_FAVOURITE = "favourite";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVOURITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static float getColumnFloat(Cursor cursor, String columnName) {
        return cursor.getFloat(cursor.getColumnIndex(columnName));
    }

    public static final class FavouriteColumns implements BaseColumns {
        public static String ID = "id";
        public static String TITLE = "title";
        public static String POSTER = "poster";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date";
        public static String VOTE_AVERAGE = "vote_average";
    }
}
