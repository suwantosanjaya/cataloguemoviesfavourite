package com.dicoding.suwantosanjaya.cataloguemovies.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dicoding.suwantosanjaya.cataloguemovies.providers.databases.DatabaseContract;
import com.dicoding.suwantosanjaya.cataloguemovies.providers.databases.FavouriteHelper;

/**
 * Created by Suwanto Sanjaya on 17/12/2018.
 */
public class MovieProvider extends ContentProvider {
    private static final int FAVOURITE = 1;
    private static final int FAFOURITE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        // content://com.dicoding.suwantosanjaya.cataloguemovies/favourite
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_FAVOURITE, FAVOURITE);

        // content://com.dicoding.suwantosanjaya.cataloguemovies/favourite/id
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_FAVOURITE + "/#", FAFOURITE_ID);
    }

    private FavouriteHelper favouriteHelper;

    @Override
    public boolean onCreate() {
        favouriteHelper = new FavouriteHelper(getContext());
        favouriteHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITE:
                cursor = favouriteHelper.queryProvider();
                break;
            case FAFOURITE_ID:
                cursor = favouriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added;

        switch (sUriMatcher.match(uri)) {
            case FAVOURITE:
                added = favouriteHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(DatabaseContract.CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAFOURITE_ID:
                deleted = favouriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case FAFOURITE_ID:
                updated = favouriteHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }
}
